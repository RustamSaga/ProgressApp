package com.rustamsaga.progress.core.room

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.newprogress.core.data.local.dao.CurrentProgressDao
import com.example.newprogress.core.data.local.dao.NoteOfTargetDao
import com.example.newprogress.core.data.local.dao.ObjectOfObservationDao
import com.example.newprogress.core.data.local.dao.ProgressTargetDao
import com.example.newprogress.core.data.local.dao.StandardProgressDao
import com.example.newprogress.core.data.local.ObjectOfObservationData
import com.example.newprogress.core.data.local.ProgressDatabase
import com.example.newprogress.core.data.local.ProgressTargetData
import com.example.newprogress.core.data.local.entity.CurrentProgressEntity
import com.example.newprogress.core.data.local.entity.NoteOfProgressTargetEntity
import com.example.newprogress.core.data.local.entity.ProgressTargetEntity
import com.example.newprogress.core.data.local.entity.StandardProgressEntity
import com.example.newprogress.core.data.mapper.notes.NoteOfTargetToDomain
import com.example.newprogress.core.data.mapper.progresses.CurrentToModel
import com.example.newprogress.core.data.mapper.progresses.ProgressTargetToCache
import com.example.newprogress.core.data.mapper.progresses.ProgressTargetToData
import com.example.newprogress.core.data.mapper.progresses.StandardToModel
import com.example.newprogress.core.utils.TimeConverters
import com.rustamsaga.progress.core.room.elements.ObjectsEntity.obj
import com.rustamsaga.progress.core.room.elements.Targets
import com.rustamsaga.progress.core.room.elements.Targets.getListOfChildrenTarget
import com.rustamsaga.progress.core.room.elements.Targets.progressTargetData
import com.rustamsaga.progress.core.room.elements.Targets.progressTargetEntity
import com.example.newprogress.screens.user.data.utils.mapToEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.time.OffsetDateTime


class TestProgressTarget {

    private lateinit var db: ProgressDatabase
    private lateinit var userDao: ObjectOfObservationDao
    private lateinit var targetDao: ProgressTargetDao
    private lateinit var note: NoteOfTargetDao
    private lateinit var currentProgressDao: CurrentProgressDao
    private lateinit var standardProgressDao: StandardProgressDao


    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, ProgressDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        userDao = db.objectOfObservationDao()
        targetDao = db.progressTargetDao()
        note = db.noteOfTargetDao()
        currentProgressDao = db.currentProgressDao()
        standardProgressDao = db.standardProgressDao()

    }

    @After
    @Throws(IOException::class)
    fun clear() {
        db.close()
    }

    @Test
    fun createTarget_marksTestAsMatched_allTestsPassed() = runBlocking {
        val user = obj
        val target = progressTargetEntity

        assertNull(targetDao.getTargetEntityById(target.id!!))

        userDao.insertObject(user)
        targetDao.insertTarget(target)

        var obj: ObjectOfObservationData? = null
        userDao.getObjectWholly(user.id!!).collect {
            obj = it
        }
        assertEquals(user.checkInTime, obj!!.checkInTime)
        assertTrue(
            targetDao.contains(
                target.name,
                TimeConverters().fromOffsetDateTime(target.checkInTime)
            )
        )
        var targetResult: ProgressTargetEntity? = null

        targetDao.getTargetEntityById(target.id!!).collect {
            targetResult = it
        }
        assertEquals(target, targetResult)

        targetDao.deleteTarget(target)

        assertNull(targetDao.getTargetEntityById(target.id!!))
        assertFalse(
            targetDao.contains(
                target.name,
                TimeConverters().fromOffsetDateTime(target.checkInTime)
            )
        )

    }

    @Test
    fun gettingTargetData_marksTestAsMatched_allTestsPassed() = runBlocking {
        val user = obj
        val target = progressTargetData
        val notesTarget = target.notes
        val currentProgressTarget = target.currentProgress
        val standardProgressTarget = target.standardProgress

        assertNull(targetDao.getTargetDataById(target.id))
        assertEquals(emptyList<NoteOfProgressTargetEntity>(), note.getNotesByTargetId(target.id))
        assertEquals(
            emptyList<NoteOfProgressTargetEntity>(),
            currentProgressDao.getCurrentProgressesByTargetId(target.id)
        )
        assertEquals(
            emptyList<NoteOfProgressTargetEntity>(),
            standardProgressDao.getStandardProgressByTargetId(target.id)
        )

        userDao.insertObject(user)
        targetDao.insertTarget(
            target.map(
                ProgressTargetToCache(),
                CurrentToModel(),
                StandardToModel(),
                NoteOfTargetToDomain()
            )
        )
        notesTarget.forEach {
            note.insertNote(it)
        }
        currentProgressTarget.forEach {
            currentProgressDao.insertCurrentProgress(it)
        }
        standardProgressTarget.forEach {
            standardProgressDao.insertStandardProgress(it)
        }

        var objRes: ObjectOfObservationData? = null
        userDao.getObjectWholly(user.id!!).collect {
            objRes = it
        }

        assertEquals(user.checkInTime, objRes!!.checkInTime)
        assertTrue(
            targetDao.contains(
                target.name,
                TimeConverters().fromOffsetDateTime(target.checkInTime)
            )
        )

        var targetResult: ProgressTargetData? = null

        targetDao.getTargetEntityById(target.id).collect {
            targetResult = it!!.map(ProgressTargetToData())
        }
        assertEquals(target, targetResult)

        targetDao.deleteTarget(
            target.map(
                ProgressTargetToCache(),
                CurrentToModel(),
                StandardToModel(),
                NoteOfTargetToDomain()
            )
        )

        assertNull(targetDao.getTargetDataById(target.id))
        assertFalse(
            targetDao.contains(
                target.name,
                TimeConverters().fromOffsetDateTime(target.checkInTime)
            )
        )
    }

    @Test
    fun linkageTest_byDeleteUser_allTestsPassed() = runBlocking {
        val user = obj
        val target = progressTargetData
        val childrenTarget = getListOfChildrenTarget(target.id)

        userDao.insertObject(user)
        targetDao.insertTarget(
            target.map(
                ProgressTargetToCache(),
                CurrentToModel(),
                StandardToModel(),
                NoteOfTargetToDomain()
            )
        )
        childrenTarget.forEach {
            targetDao.insertTarget(
                it.map(
                    ProgressTargetToCache(),
                    CurrentToModel(),
                    StandardToModel(),
                    NoteOfTargetToDomain()
                )
            )
        }

        var checkInTime = OffsetDateTime.now()
        targetDao.getTargetDataById(target.id).collect {
            checkInTime = it!!.checkInTime
        }
        assertEquals(target.checkInTime, checkInTime)

        var size = 0
        targetDao.getChildrenTarget(target.id).collect {
            size = it.size
        }
        assertEquals(childrenTarget.size, size)

        userDao.deleteObject(user)

        var nullTarget: ProgressTargetData? = null
        targetDao.getTargetDataById(target.id).collect {
            nullTarget = it
        }

        assertNull(nullTarget)

        var isEmpty = false
        targetDao.getChildrenTarget(target.id).collect {
            isEmpty = it.isEmpty()
        }
        assertTrue(isEmpty)
    }

    @Test
    fun insertAllTargets() = runBlocking {
        val user = obj
        val targets = Targets.getListTargets()

        userDao.insertObject(user)
        targetDao.insertTargets(targets)
        val actual = targetDao.getAllTargets().mapToEntity()
        assertEquals(targets, actual)
    }

    @Test
    fun linkage_test_for_progresses() = runBlocking {
        val user = obj
        val target = progressTargetData
        val currentProgress = target.currentProgress
        val standardProgress = target.standardProgress

        userDao.insertObject(user)
        targetDao.insertTarget(
            target.map(
                ProgressTargetToCache(),
                CurrentToModel(),
                StandardToModel(),
                NoteOfTargetToDomain()
            )
        )

        for (i in 0..currentProgress.lastIndex) {
            currentProgressDao.insertCurrentProgress(currentProgress[i])
            standardProgressDao.insertStandardProgress(standardProgress[i])
        }

        Log.d("fromDomain - ", target.currentProgress.toString())
        Log.d("fromDao - ", targetDao.getTargetDataById(target.id).first()!!.currentProgress.toString())
        Log.d("fromDomain - ", target.standardProgress.toString())
        Log.d("fromDao - ", targetDao.getTargetDataById(target.id).first()!!.standardProgress.toString())

        var currentProgresses: List<CurrentProgressEntity> = emptyList()
        targetDao.getTargetDataById(target.id).collect {
            currentProgresses = it!!.currentProgress
        }

        assertEquals(
            target.currentProgress,
            currentProgress
        )

        var standardProgresses: List<StandardProgressEntity> = emptyList()
        targetDao.getTargetDataById(target.id).collect {
            standardProgresses = it!!.standardProgress
        }
        assertEquals(
            target.standardProgress,
            standardProgresses
        )

        currentProgressDao.deleteCurrentProgress(currentProgress[0])
        standardProgressDao.deleteStandardProgress(standardProgress[0])


        var currentProgressesAfterRemove: List<CurrentProgressEntity> = emptyList()
        targetDao.getTargetDataById(target.id).collect {
            currentProgressesAfterRemove = it!!.currentProgress
        }
        Log.d(
            "fromDao - after single remove",
            currentProgressesAfterRemove.toString()
        )

        var standardProgressesAfterRemove: List<StandardProgressEntity> = emptyList()
        targetDao.getTargetDataById(target.id).collect {
            standardProgressesAfterRemove = it!!.standardProgress
        }
        Log.d(
            "fromDao - after single remove",
            standardProgressesAfterRemove.toString()
        )


        assertNotEquals(
            currentProgress[0],
            currentProgressDao.getCurrentProgressesWithDate(
                currentProgress[0].targetId,
                TimeConverters().fromOffsetDateTime(currentProgress[0].checkInTime)
            )
        )
        assertNotEquals(
            standardProgress[0],
            standardProgressDao.getStandardProgressesWithDate(
                standardProgress[0].targetId,
                TimeConverters().fromOffsetDateTime(standardProgress[0].checkInTime)
            )
        )

        targetDao.deleteTarget(
            target.map(
                ProgressTargetToCache(),
                CurrentToModel(),
                StandardToModel(),
                NoteOfTargetToDomain()
            )
        )

        Log.d("fromDao - after delete target", targetDao.getTargetDataById(target.id).toString())
        Log.d("fromDao - after delete target", targetDao.getTargetDataById(target.id).toString())


        assertEquals(
            emptyList<CurrentProgressEntity>(),
            currentProgressDao.getCurrentProgressesByTargetId(currentProgress[0].targetId)
        )
        assertEquals(
            emptyList<StandardProgressEntity>(),
            standardProgressDao.getStandardProgressByTargetId(standardProgress[0].targetId)
        )
    }
}
