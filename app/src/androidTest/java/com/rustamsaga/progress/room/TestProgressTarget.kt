package com.rustamsaga.progress.room

import android.content.Context
import android.util.Log
import androidx.compose.compiler.plugins.kotlin.lower.forEachWith
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.rustamsaga.progress.core.data.local.ProgressDatabase
import com.rustamsaga.progress.core.data.local.dao.*
import com.rustamsaga.progress.core.data.local.entity.CurrentProgressEntity
import com.rustamsaga.progress.core.data.local.entity.StandardProgressEntity
import com.rustamsaga.progress.core.data.mapper.NoteMapper
import com.rustamsaga.progress.core.data.mapper.ProgressMapper
import com.rustamsaga.progress.core.data.mapper.TargetMapper
import com.rustamsaga.progress.core.domain.models.NoteOfTargetModel
import com.rustamsaga.progress.core.utils.TimeConverters
import com.rustamsaga.progress.room.elements.TestObjectOfObservationElements
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException


/**
 * TODO Использовать такой подход
 *  We use pattern: state under test - expected behaviour - [when]:
 *  @Test fun `success runs - returns number of passed test runs`() {}
 *  @Test fun `read instrumentation output - completes stream - with failed test`() {}
 *  @Test fun `create summary - marks test as matched - all tests passed`() {}
 */
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
    fun test_add_and_check() = runBlocking {
        val user = TestObjectOfObservationElements.obj
        val target = TestObjectOfObservationElements.progressTarget
        assertNotEquals(target, targetDao.getTargetById(target.id))

        userDao.insertObject(user)
        targetDao.insertTarget(
            target.map(
                targetMapper = TargetMapper.EntityConverter(),
                currentProgressMapper = ProgressMapper.Current(),
                standardProgressMapper = ProgressMapper.Standard(),
                noteMapper = NoteMapper.NoteOfTargetToDomain()
            )
        )

        assertEquals(user, userDao.getObjectById(user.id!!))
        assertEquals(
            true,
            targetDao.contains(
                target.name,
                TimeConverters().fromOffsetDateTime(target.checkInTime)
            )
        )
        assertEquals(
            target.checkInTime,
            targetDao.getTargetById(target.id)!!.checkInTime
        )
    }

    @Test
    fun linkage_test_by_delete_user() = runBlocking {
        val user = TestObjectOfObservationElements.obj
        val target = TestObjectOfObservationElements.progressTarget
        val childrenTarget =
            TestObjectOfObservationElements.getListOfChildrenTarget(target.id)

        userDao.insertObject(user)
        targetDao.insertTarget(
            target.map(
                targetMapper = TargetMapper.EntityConverter(),
                currentProgressMapper = ProgressMapper.Current(),
                standardProgressMapper = ProgressMapper.Standard(),
                noteMapper = NoteMapper.NoteOfTargetToDomain()
            )
        )
        childrenTarget.forEach {
            targetDao.insertTarget(
                it.map(
                    targetMapper = TargetMapper.EntityConverter(),
                    currentProgressMapper = ProgressMapper.Current(),
                    standardProgressMapper = ProgressMapper.Standard(),
                    noteMapper = NoteMapper.NoteOfTargetToDomain()
                )
            )
        }

        assertEquals(target.checkInTime, targetDao.getTargetById(target.id)!!.checkInTime)
        assertEquals(childrenTarget.size, targetDao.getChildrenTarget(target.id).size)

        userDao.deleteObject(user)

        assertNotEquals(target.checkInTime, targetDao.getTargetById(target.id)!!.checkInTime)
        assertEquals(true, targetDao.getChildrenTarget(target.id).isEmpty())
    }

    @Test
    fun linkage_test_by_delete_target() = runBlocking {
        val user = TestObjectOfObservationElements.obj
        val target = TestObjectOfObservationElements.progressTarget
        val childrenTarget =
            TestObjectOfObservationElements.getListOfChildrenTarget(target.id)

        userDao.insertObject(user)
        targetDao.insertTarget(
            target.map(
                targetMapper = TargetMapper.EntityConverter(),
                currentProgressMapper = ProgressMapper.Current(),
                standardProgressMapper = ProgressMapper.Standard(),
                noteMapper = NoteMapper.NoteOfTargetToDomain()
            )
        )
        childrenTarget.forEach {
            targetDao.insertTarget(
                it.map(
                    targetMapper = TargetMapper.EntityConverter(),
                    currentProgressMapper = ProgressMapper.Current(),
                    standardProgressMapper = ProgressMapper.Standard(),
                    noteMapper = NoteMapper.NoteOfTargetToDomain()
                )
            )
        }

        targetDao.deleteTarget(
            target.map(
                targetMapper = TargetMapper.EntityConverter(),
                currentProgressMapper = ProgressMapper.Current(),
                standardProgressMapper = ProgressMapper.Standard(),
                noteMapper = NoteMapper.NoteOfTargetToDomain()
            )
        )
        assertNotEquals(target, targetDao.getTargetById(target.id))
        assertNotEquals(true, targetDao.getChildrenTarget(target.id).isEmpty())

    }

    @Test
    fun test_note() = runBlocking {
        val user = TestObjectOfObservationElements.obj
        val target = TestObjectOfObservationElements.progressTarget
        val noteOfTarget = target.notes

        userDao.insertObject(user)
        targetDao.insertTarget(
            target.map(
                targetMapper = TargetMapper.EntityConverter(),
                currentProgressMapper = ProgressMapper.Current(),
                standardProgressMapper = ProgressMapper.Standard(),
                noteMapper = NoteMapper.NoteOfTargetToDomain()
            )
        )
        noteOfTarget.forEach {
            note.insertNote(it)

            assertEquals(
                true,
                note.contains(
                    1, TimeConverters().fromOffsetDateTime(it.checkInTime)
                )
            )
        }

        Log.d("fromDomain - ", target.toString())
        Log.d("fromDao - ", targetDao.getTargetById(target.id).toString())
        note.deleteNote(noteOfTarget[0])
        assertEquals(
            false, note.contains(
                1, TimeConverters().fromOffsetDateTime(noteOfTarget[0].checkInTime)
            )
        )

        targetDao.deleteTarget(
            target.map(
                targetMapper = TargetMapper.EntityConverter(),
                currentProgressMapper = ProgressMapper.Current(),
                standardProgressMapper = ProgressMapper.Standard(),
                noteMapper = NoteMapper.NoteOfTargetToDomain()
            )
        )
        val actual = note.getNoteByTargetId(target.id).isEmpty()
        assertEquals(true, actual)
    }

    @Test
    fun linkage_test_for_progresses() = runBlocking {
        val user = TestObjectOfObservationElements.obj
        val target = TestObjectOfObservationElements.progressTarget
        val currentProgress = target.currentProgress
        val standardProgress = target.standardProgress

        userDao.insertObject(user)
        targetDao.insertTarget(
            target.map(
                targetMapper = TargetMapper.EntityConverter(),
                currentProgressMapper = ProgressMapper.Current(),
                standardProgressMapper = ProgressMapper.Standard(),
                noteMapper = NoteMapper.NoteOfTargetToDomain()
            )
        )

        for (i in 0..currentProgress.lastIndex) {
            currentProgressDao.insertCurrentProgress(currentProgress[i])
            standardProgressDao.insertCurrentProgress(standardProgress[i])
        }

        Log.d("fromDomain - ", target.currentProgress.toString())
        Log.d("fromDao - ", targetDao.getTargetById(target.id)!!.currentProgress.toString())
        Log.d("fromDomain - ", target.standardProgress.toString())
        Log.d("fromDao - ", targetDao.getTargetById(target.id)!!.standardProgress.toString())

        assertEquals(target.currentProgress, targetDao.getTargetById(target.id)!!.currentProgress)
        assertEquals(target.standardProgress, targetDao.getTargetById(target.id)!!.standardProgress)

        currentProgressDao.deleteCurrentProgress(currentProgress[0])
        standardProgressDao.deleteStandardProgress(standardProgress[0])

        Log.d(
            "fromDao - after single remove",
            targetDao.getTargetById(target.id)!!.currentProgress.toString()
        )
        Log.d(
            "fromDao - after single remove",
            targetDao.getTargetById(target.id)!!.standardProgress.toString()
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
                targetMapper = TargetMapper.EntityConverter(),
                currentProgressMapper = ProgressMapper.Current(),
                standardProgressMapper = ProgressMapper.Standard(),
                noteMapper = NoteMapper.NoteOfTargetToDomain()
            )
        )

        Log.d("fromDao - after delete target", targetDao.getTargetById(target.id).toString())
        Log.d("fromDao - after delete target", targetDao.getTargetById(target.id).toString())


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
