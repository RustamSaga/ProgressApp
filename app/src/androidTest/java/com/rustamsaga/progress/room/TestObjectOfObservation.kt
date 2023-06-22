package com.rustamsaga.progress.room

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rustamsaga.progress.core.data.local.ProgressDatabase
import com.rustamsaga.progress.core.data.local.dao.NoteOfObjectDao
import com.rustamsaga.progress.core.data.local.dao.ObjectOfObservationDao
import com.rustamsaga.progress.core.data.local.dao.ProgressTargetDao
import com.rustamsaga.progress.core.data.local.entity.ObjectOfObservationData
import com.rustamsaga.progress.core.data.local.entity.ObjectOfObservationEntity
import com.rustamsaga.progress.core.data.mapper.NoteMapper
import com.rustamsaga.progress.core.data.mapper.ObjectOfObservationMapper
import com.rustamsaga.progress.core.data.mapper.ProgressMapper
import com.rustamsaga.progress.core.data.mapper.TargetMapper
import com.rustamsaga.progress.core.utils.TimeConverters
import com.rustamsaga.progress.room.elements.TestObjectOfObservationElements
import com.rustamsaga.progress.room.elements.TestObjectOfObservationElements.getListOfChildrenTarget
import com.rustamsaga.progress.room.elements.TestObjectOfObservationElements.log
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.time.OffsetDateTime

/**
 * TODO Использовать такой подход
 *  We use pattern: state under test - expected behaviour - [when]:
 *  @Test fun `success runs - returns number of passed test runs`() {}
 *  @Test fun `read instrumentation output - completes stream - with failed test`() {}
 *  @Test fun `create summary - marks test as matched - all tests passed`() {}
 */


@RunWith(AndroidJUnit4::class)
class TestObjectOfObservation {

    private lateinit var db: ProgressDatabase
    private lateinit var userDao: ObjectOfObservationDao
    private lateinit var noteDao: NoteOfObjectDao
    private lateinit var targetDao: ProgressTargetDao

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, ProgressDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        userDao = db.objectOfObservationDao()
        noteDao = db.noteObjectOfObservationDao()
        targetDao = db.progressTargetDao()
    }

    @After
    @Throws(IOException::class)
    fun clear() {
        db.close()
    }


    @Test
    fun createObjectOfObservation_marksTestAsMatched_allTestsPassed() = runBlocking {
        val user = TestObjectOfObservationElements.obj
        assertEquals(
            false,
            userDao.contains(
                user.firstName,
                TimeConverters().fromOffsetDateTime(user.checkInTime)
            )
        )

        // insert
        userDao.insertObject(user)
        assertEquals(
            true,
            userDao.contains(
                firstName = user.firstName,
                checkInTime = TimeConverters().fromOffsetDateTime(user.checkInTime)
            )
        )
        assertEquals(user, userDao.getObjectById(user.id!!))

        // delete
        userDao.deleteObject(user)
        assertEquals(
            false,
            userDao.contains(
                firstName = user.firstName,
                checkInTime = TimeConverters().fromOffsetDateTime(user.checkInTime)
            )
        )
        assertNotEquals(user, userDao.getObjectById(user.id!!))

        // objects are active and observed
        val users = TestObjectOfObservationElements.getListOfObject()
        assertEquals(emptyList<ObjectOfObservationEntity>(), userDao.getAllObjects())

        users.forEach {
            log(it.id!!, it.isActive, it.observed)
            userDao.insertObject(it)
        }
        assertEquals(users, userDao.getAllObjects())
        assertNotEquals(users, userDao.getAllObserved(true))
    }

    @Test
    fun checkedObjectByTargets_allTestsPassed() = runBlocking {
//        val user = TestObjectOfObservationElements.obj
        val user = ObjectOfObservationData(
            id = 1,
            firstName = "TESTIROVSHIK",
            lastName = "TOTSAMI",
            description = "nothing",
            observed = false,
            checkInTime = OffsetDateTime.now(),
            isActive = true,
            targets = getListOfChildrenTarget(1),
            notes = TestObjectOfObservationElements.createNotesOfUser(1)
        )

        userDao.insertObject(user.map(ObjectOfObservationMapper.ObjectToEntity()))
        user.targets.forEach {
            targetDao.insertTarget(
                it.map(
                    TargetMapper.EntityConverter(),
                    ProgressMapper.Current(),
                    ProgressMapper.Standard(),
                    NoteMapper.NoteOfTargetToDomain()
                )
            )
        }
        user.notes.forEach {
            noteDao.insertNote(it)
        }
        assertEquals(
            true,
            userDao.contains(user.firstName, TimeConverters().fromOffsetDateTime(user.checkInTime))
        )

        assertEquals(
            user.targets.size,
            targetDao.getTargetsByUser(user.id).size
        )


    }

    @Test
    fun containsTest() = runBlocking {
        val user = TestObjectOfObservationElements.obj
        assertEquals(
            false,
            userDao.contains(
                user.firstName,
                checkInTime = TimeConverters().fromOffsetDateTime(user.checkInTime)
            )
        )

        userDao.insertObject(user)

        assertEquals(
            true,
            userDao.contains(
                firstName = user.firstName,
                checkInTime = TimeConverters().fromOffsetDateTime(user.checkInTime)
            )
        )

        userDao.deleteObject(user)

        assertEquals(
            false,
            userDao.contains(
                firstName = user.firstName,
                checkInTime = TimeConverters().fromOffsetDateTime(user.checkInTime)
            )
        )
    }

    @Test
    fun test_add_and_check_and_delete_note() = runBlocking {
        val user = TestObjectOfObservationElements.obj
        val notes = TestObjectOfObservationElements.createNotesOfUser(user.id!!)

        userDao.insertObject(user)
        notes.forEach {
            noteDao.insertNote(it)
        }

        notes.forEach {
            assertEquals(
                true,
                noteDao.contains(
                    userId = it.userId,
                    checkInTime = TimeConverters().fromOffsetDateTime(it.checkInTime)
                )
            )
        }

        val actual = noteDao.getNoteByDate(userId = user.id!!, date = "2023%").isEmpty()

        assertEquals(false, actual)

        noteDao.deleteNote(notes[0])
        assertEquals(
            true,
            noteDao.getNoteByDate(
                userId = notes[0].userId,
                date = TimeConverters().fromOffsetDateTime(notes[0].checkInTime)
            ).isEmpty()
        )

        assertEquals(
            false,
            noteDao.contains(
                userId = notes[0].userId,
                checkInTime = TimeConverters().fromOffsetDateTime(notes[0].checkInTime)
            )
        )
    }

    @Test
    fun dependency_test() = runBlocking {
        val user = TestObjectOfObservationElements.obj
        val notes = TestObjectOfObservationElements.createNotesOfUser(user.id!!)

        userDao.insertObject(user)
        notes.forEach {
            noteDao.insertNote(it)
        }

        userDao.deleteObject(user)

        val actualList = noteDao.getNoteByDate(user.id!!, "2023%").isEmpty()
        assertEquals(true, actualList)

        val actualNote = noteDao.contains(
            user.id!!,
            TimeConverters().fromOffsetDateTime(notes[0].checkInTime)
        )
        assertEquals(false, actualNote)
    }

}