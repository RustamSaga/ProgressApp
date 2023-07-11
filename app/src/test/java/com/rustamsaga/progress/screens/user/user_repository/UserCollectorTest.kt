package com.rustamsaga.progress.screens.user.user_repository

import com.rustamsaga.progress.core.data.CacheDataSource
import com.rustamsaga.progress.core.data.local.dao.*
import com.rustamsaga.progress.core.data.local.entity.*
import com.rustamsaga.progress.core.data.mapper.ObjectToCache
import com.rustamsaga.progress.core.data.mapper.ObjectToDomain
import com.rustamsaga.progress.core.data.mapper.TargetPackerImpl
import com.rustamsaga.progress.core.data.mapper.boxes.NoteBoxMapperImp
import com.rustamsaga.progress.core.data.mapper.boxes.TargetBoxMapperImpl
import com.rustamsaga.progress.core.data.mapper.notes.NoteOfObjectToCache
import com.rustamsaga.progress.core.data.mapper.notes.NoteOfTargetToCache
import com.rustamsaga.progress.core.data.mapper.notes.NoteOfTargetToDomain
import com.rustamsaga.progress.core.data.mapper.progresses.*
import com.rustamsaga.progress.core.domain.models.ProgressTargetModel
import com.rustamsaga.progress.core.utils.ManageResources
import com.rustamsaga.progress.screens.user.data.UserCollectorImpl
import com.rustamsaga.progress.screens.user.domain.UserCollector
import com.rustamsaga.progress.screens.user.user_repository.elements.ObjectsModel
import com.rustamsaga.progress.screens.user.user_repository.elements.TestCacheDataSource
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class UserCollectorTest {

    private lateinit var mockObjectDao: ObjectOfObservationDao
    private lateinit var mockTargetDao: ProgressTargetDao
    private lateinit var mockNoteOfObjectDao: NoteOfObjectDao
    private lateinit var mockCurrentProgressDao: CurrentProgressDao
    private lateinit var mockStandardProgressDao: StandardProgressDao
    private lateinit var mockNoteOfTargetDao: NoteOfTargetDao
    private lateinit var mockDataSource: CacheDataSource

    private lateinit var userCollector: UserCollector

    private val expectedUser = ObjectsModel.userByDetails
    private val expectedUserForUpdate =
        expectedUser.copy(firstName = "Updated name", lastName = "Updated last name")
    private val userDefault = UserCollectorImpl.userDefault


    @Before
    fun setup() = runBlocking {
        mockObjectDao =
            TestCacheDataSource.getMockObjectOfObservationDao(expectedUser, expectedUserForUpdate)
        mockTargetDao = TestCacheDataSource.getMockProgressTargetDao(expectedUser.targets)
        mockNoteOfObjectDao = TestCacheDataSource.getNoteOfObjectDao()
        mockCurrentProgressDao = TestCacheDataSource.getMockkCurrentProgressDao()
        mockStandardProgressDao = TestCacheDataSource.getMockkStandardProgressDao()
        mockNoteOfTargetDao = TestCacheDataSource.getNoteOfTargetDao()
        mockDataSource = mockk() {
            coEvery { this@mockk.objectOfObservationDao() } coAnswers { mockObjectDao }
            coEvery { this@mockk.progressTargetDao() } coAnswers { mockTargetDao }
            coEvery { this@mockk.noteOfObjectDao() } coAnswers { mockNoteOfObjectDao }
            coEvery { this@mockk.currentProgressDao() } coAnswers { mockCurrentProgressDao }
            coEvery { this@mockk.standardProgressDao() } coAnswers { mockStandardProgressDao }
        }

        userCollector = spyk(createUserCollector(mockDataSource))

    }

    @Test
    fun createUser_and_update_success() = runBlocking {
        val slotObj = slot<ObjectOfObservationEntity>()
        val expectedUserEntity =
            expectedUser.map(ObjectToCache(), NoteBoxMapperImp(), TargetBoxMapperImpl())
        val expectedUserForUpdateEntity = expectedUserForUpdate.map(
            ObjectToCache(), NoteBoxMapperImp(), TargetBoxMapperImpl()
        )

        mockObjectDao.captureInserting(slotObj)

        Assert.assertEquals(true, userCollector.updateUser(expectedUser))
        Assert.assertEquals(expectedUserEntity, slotObj.captured)
        println("create is success - ${slotObj.captured}")

        Assert.assertEquals(true, userCollector.updateUser(expectedUserForUpdate))
        Assert.assertEquals(expectedUserForUpdateEntity, slotObj.captured)
        println("update is success - ${slotObj.captured}")
    }

    @Test
    fun getUserByDetails_success() = runBlocking {
        val user = userCollector.getUser()
        Assert.assertEquals(expectedUser, user)
        println("expected: $expectedUser")
        println("actual  : $expectedUser")
    }

    @Test
    fun deleteUser_success() = runBlocking {

        val slotObjAfterDelete = slot<ObjectOfObservationEntity>()
        val slotDeletedTargets = mutableListOf<ProgressTargetEntity>()
        val slotDeletedNoteOfObject = mutableListOf<NoteOfObjectEntity>()

        mockObjectDao.captureInserting(slotObjAfterDelete)
        mockObjectDao.setBehaviorContains(expectedUser, false)

        mockTargetDao.captureDeleting(slotDeletedTargets)
        expectedUser.targets.forEach {
            mockTargetDao.setBehaviorContains(it, false)
        }
        mockNoteOfObjectDao.captureDeleting(slotDeletedNoteOfObject)
        mockNoteOfObjectDao.setBehaviorContains(expectedUser.notes, true)


        val successful = userCollector.deleteUser(expectedUser)

        Assert.assertEquals(userDefault, slotObjAfterDelete.captured)
        println("user after deleting - ${slotObjAfterDelete.captured}\n")
        Assert.assertEquals(true, successful)
        Assert.assertEquals(
            expectedUser.targets.map { it.map(ProgressTargetToCache()) },
            slotDeletedTargets
        )
        println("deleted targets: $slotDeletedTargets")

        Assert.assertEquals(
            expectedUser.notes.map { it.map(NoteOfObjectToCache()) },
            slotDeletedNoteOfObject
        )
        println("deleted notes of obj: $slotDeletedNoteOfObject")


    }


    @Test
    fun create_and_delete_target_success() = runBlocking {
        val target = expectedUser.targets.first()
        val expectedTargets = putTogetherTargets(target)
        val expectedCurrent = putTogetherCurrentProgresses(target)
        val expectedStandard = putTogetherStandardProgresses(target)
        val expectedNote = putTogetherNoteOfTarget(target)

        val insertedTarget = mutableListOf<ProgressTargetEntity>()
        val deletedTargets = mutableListOf<ProgressTargetEntity>()
        val insertedCurrProgresses = mutableListOf<List<CurrentProgressEntity>>()
        val insertedStanProgresses = mutableListOf<List<StandardProgressEntity>>()
        val insertedNotes = mutableListOf<List<NoteOfProgressTargetEntity>>()


        mockTargetDao.captureInserting(insertedTarget)
        mockCurrentProgressDao.captureInserting(insertedCurrProgresses)
        mockStandardProgressDao.captureInserting(insertedStanProgresses)
        mockNoteOfTargetDao.captureInserting(insertedNotes)
        mockTargetDao.captureDeleting(deletedTargets)
        mockTargetDao.setBehaviorContains(target, true)

        Assert.assertEquals(true, userCollector.createTarget(target))
        Assert.assertEquals(expectedTargets, insertedTarget)
        Assert.assertEquals(expectedCurrent, insertedCurrProgresses.flatten())
        Assert.assertEquals(expectedStandard, insertedStanProgresses.flatten())
        Assert.assertEquals(expectedNote, insertedNotes.flatten())

        mockTargetDao.setBehaviorContains(target, false)

        Assert.assertEquals(true, userCollector.deleteTarget(target))

        coVerify(exactly = 1) {
            userCollector.createTarget(target)
            userCollector.deleteTarget(target)
        }
        println("inserted:")
        println(insertedTarget)
        println(insertedCurrProgresses)
        println(insertedStanProgresses)
        println(insertedNotes)
        println("deleted:")
        println(deletedTargets)
    }

    @Test
    fun create_deleteNoteOfUser_success() = runBlocking {
        val notes = expectedUser.notes
        val insertSlot = mutableListOf<NoteOfObjectEntity>()
        val deleteSlot = mutableListOf<NoteOfObjectEntity>()

        mockNoteOfObjectDao.captureInserting(insertSlot)
        mockNoteOfObjectDao.captureDeleting(deleteSlot)
        mockNoteOfObjectDao.setBehaviorContains(notes, true)

        notes.forEach { note ->
            userCollector.createNoteUser(note)
        }
        coVerify(exactly = notes.size) {
            userCollector.createNoteUser(any())
        }
        Assert.assertEquals(notes.map { it.map(NoteOfObjectToCache()) }, insertSlot)

        mockNoteOfObjectDao.setBehaviorContains(notes, false)
        userCollector.deleteNoteUser(notes.first())
        userCollector.deleteNoteUser(notes.last())


        Assert.assertEquals(notes.first().map(NoteOfObjectToCache()), deleteSlot.first())
        Assert.assertEquals(notes.last().map(NoteOfObjectToCache()), deleteSlot.last())


        println("notes: $notes")
        println("was inserted: $insertSlot")
        println("was deleted: $deleteSlot")

    }

    @Test
    fun crud_currentProgress_success() = runBlocking {
        val progresses = expectedUser.targets.first().currentProgress
        val update = progresses.map { it.copy( title = "updated", description = "updated") }
        val insertSlot = mutableListOf<CurrentProgressEntity>()
        val updateSlot = mutableListOf<CurrentProgressEntity>()
        val deleteSlot = mutableListOf<CurrentProgressEntity>()

        mockCurrentProgressDao.captureSingleInserting(insertSlot)
        mockCurrentProgressDao.captureDeleting(deleteSlot)
        mockCurrentProgressDao.captureUpdating(updateSlot)

        // creating and checking
        mockCurrentProgressDao.setBehaviorContains(progresses, true)
        progresses.forEach { item ->
            userCollector.addCurrentProgress(item)
        }
        coVerify(exactly = progresses.size) {
            userCollector.addCurrentProgress(any())
        }
        Assert.assertEquals(progresses.map { it.map(CurrentToEntity()) }, insertSlot)

        // update and checking
        update.forEach { item ->
            userCollector.updateCurrentProgress(item)
        }
        coVerify(exactly = update.size) {
            userCollector.updateCurrentProgress(any())
        }
        Assert.assertEquals(update.map { it.map(CurrentToEntity()) }, updateSlot)

        // delete
        mockCurrentProgressDao.setBehaviorContains(update, false)

        Assert.assertEquals(true, userCollector.deleteCurrentProgress(update.first()))
        Assert.assertEquals(true, userCollector.deleteCurrentProgress(update.last()))

        Assert.assertEquals(update.first().map(CurrentToEntity()), deleteSlot.first())
        Assert.assertEquals(update.last().map(CurrentToEntity()), deleteSlot.last())

        coVerify(exactly = 2) {
            userCollector.deleteCurrentProgress(any())
        }

        println("progresses are added: $insertSlot")
        println("was updated: $updateSlot")
        println("was deleted: $deleteSlot")
    }


    @Test
    fun crud_standardProgress_success() = runBlocking {
        val progresses = expectedUser.targets.first().standardProgress
        val update = progresses.map { it.copy( title = "updated", description = "updated") }
        val insertSlot = mutableListOf<StandardProgressEntity>()
        val updateSlot = mutableListOf<StandardProgressEntity>()
        val deleteSlot = mutableListOf<StandardProgressEntity>()

        mockStandardProgressDao.captureSingleInserting(insertSlot)
        mockStandardProgressDao.captureDeleting(deleteSlot)
        mockStandardProgressDao.captureUpdating(updateSlot)

        // creating and checking
        mockStandardProgressDao.setBehaviorContains(progresses, true)
        progresses.forEach { item ->
            userCollector.addStandardProgress(item)
        }
        coVerify(exactly = progresses.size) {
            userCollector.addStandardProgress(any())
        }
        Assert.assertEquals(progresses.map { it.map(StandardToEntity()) }, insertSlot)

        // update and checking
        update.forEach { item ->
            userCollector.updateStandardProgress(item)
        }
        coVerify(exactly = update.size) {
            userCollector.updateStandardProgress(any())
        }
        Assert.assertEquals(update.map { it.map(StandardToEntity()) }, updateSlot)

        // delete
        mockStandardProgressDao.setBehaviorContains(update, false)

        Assert.assertEquals(true, userCollector.deleteStandardProgress(update.first()))
        Assert.assertEquals(true, userCollector.deleteStandardProgress(update.last()))

        Assert.assertEquals(update.first().map(StandardToEntity()), deleteSlot.first())
        Assert.assertEquals(update.last().map(StandardToEntity()), deleteSlot.last())

        coVerify(exactly = 2) {
            userCollector.deleteStandardProgress(any())
        }


        println("progresses are added: $insertSlot")
        println("was updated: $updateSlot")
        println("was deleted: $deleteSlot")
    }

    private fun manageResources(): ManageResources {
        val manager = mockk<ManageResources>()
        every { manager.string(1) } returns "Progressor"
        return manager
    }

    private suspend fun createUserCollector(dataSource: CacheDataSource): UserCollector {

        return UserCollectorImpl(
            manageResources = manageResources(),
            cacheDataSource = dataSource,
            objectToDomain = ObjectToDomain(),
            objectToCache = ObjectToCache(),
            noteBoxMapper = NoteBoxMapperImp(),
            targetBoxMapper = TargetBoxMapperImpl(),
            targetToDomain = ProgressTargetToDomain(),
            targetToCache = ProgressTargetToCache(),
            currentProgressToDomain = CurrentToModel(),
            currentProgressToCache = CurrentToEntity(),
            standardProgressToDomain = StandardToModel(),
            standardProgressToCache = StandardToEntity(),
            noteTargetToDomain = NoteOfTargetToDomain(),
            noteTargetToCache = NoteOfTargetToCache(),
            noteUserToCache = NoteOfObjectToCache(),
            targetSpreader = TargetPackerImpl(
                progressTargetDao = dataSource.progressTargetDao(),
                currentProgressDao = mockCurrentProgressDao,
                standardProgressDao = mockStandardProgressDao,
                noteOfTargetDao = mockNoteOfTargetDao,
                targetToDomain = ProgressTargetToDomain(),
                targetToCache = ProgressTargetToCache(),
                currentToDomain = CurrentToModel(),
                currentToCache = CurrentToEntity(),
                standardToDomain = StandardToModel(),
                standardToCache = StandardToEntity(),
                noteToDomain = NoteOfTargetToDomain(),
                noteToCache = NoteOfTargetToCache(),
            )
        )
    }


    private fun putTogetherCurrentProgresses(targetModel: ProgressTargetModel): List<CurrentProgressEntity> {
        val res = mutableListOf<CurrentProgressEntity>()
        if (targetModel.currentProgress.isNotEmpty()) {
            res.addAll(targetModel.currentProgress.map { it.map(CurrentToEntity()) })
        }
        if (targetModel.targets.isNotEmpty()) {
            targetModel.targets.forEach { res.addAll(putTogetherCurrentProgresses(it)) }
        }
        return res
    }

    private fun putTogetherStandardProgresses(targetModel: ProgressTargetModel): List<StandardProgressEntity> {
        val res = mutableListOf<StandardProgressEntity>()
        if (targetModel.standardProgress.isNotEmpty()) {
            res.addAll(targetModel.standardProgress.map { it.map(StandardToEntity()) })
        }
        if (targetModel.targets.isNotEmpty()) {
            targetModel.targets.forEach { res.addAll(putTogetherStandardProgresses(it)) }
        }
        return res
    }

    private fun putTogetherTargets(targetModel: ProgressTargetModel): List<ProgressTargetEntity> {
        val res = mutableListOf<ProgressTargetEntity>()
        res.add(targetModel.map(ProgressTargetToCache()))

        if (targetModel.targets.isNotEmpty()) {
            targetModel.targets.forEach { res.addAll(putTogetherTargets(it)) }
        }
        return res
    }

    private fun putTogetherNoteOfTarget(targetModel: ProgressTargetModel): List<NoteOfProgressTargetEntity> {
        val res = mutableListOf<NoteOfProgressTargetEntity>()
        if (targetModel.notes.isNotEmpty()) {
            res.addAll(targetModel.notes.map { it.map(NoteOfTargetToCache()) })
        }
        if (targetModel.targets.isNotEmpty()) {
            targetModel.targets.forEach { res.addAll(putTogetherNoteOfTarget(it)) }
        }
        return res
    }
}

