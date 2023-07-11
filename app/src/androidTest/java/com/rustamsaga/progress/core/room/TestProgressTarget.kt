package com.rustamsaga.progress.core.room

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.rustamsaga.progress.core.data.local.ProgressDatabase
import com.rustamsaga.progress.core.data.local.dao.*
import com.rustamsaga.progress.core.data.local.entity.CurrentProgressEntity
import com.rustamsaga.progress.core.data.local.entity.NoteOfProgressTargetEntity
import com.rustamsaga.progress.core.data.local.entity.StandardProgressEntity
import com.rustamsaga.progress.core.data.mapper.notes.NoteOfTargetToDomain
import com.rustamsaga.progress.core.data.mapper.progresses.*
import com.rustamsaga.progress.core.utils.TimeConverters
import com.rustamsaga.progress.core.room.elements.ObjectsEntity.obj
import com.rustamsaga.progress.core.room.elements.Targets
import com.rustamsaga.progress.core.room.elements.Targets.getListOfChildrenTarget
import com.rustamsaga.progress.core.room.elements.Targets.progressTargetData
import com.rustamsaga.progress.core.room.elements.Targets.progressTargetEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.IOException


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

        assertEquals(user.checkInTime, userDao.getObjectWholly(user.id!!)!!.checkInTime)
        assertTrue(targetDao.contains(target.name, TimeConverters().fromOffsetDateTime(target.checkInTime)))
        assertEquals(target, targetDao.getTargetEntityById(target.id!!))

        targetDao.deleteTarget(target)

        assertNull(targetDao.getTargetEntityById(target.id!!))
        assertFalse(targetDao.contains(target.name, TimeConverters().fromOffsetDateTime(target.checkInTime)))

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
        assertEquals(emptyList<NoteOfProgressTargetEntity>(), currentProgressDao.getCurrentProgressesByTargetId(target.id))
        assertEquals(emptyList<NoteOfProgressTargetEntity>(), standardProgressDao.getStandardProgressByTargetId(target.id))

        userDao.insertObject(user)
        targetDao.insertTarget(target.map(
            ProgressTargetToCache(),
            CurrentToModel(),
            StandardToModel(),
            NoteOfTargetToDomain()
        ))
        notesTarget.forEach {
            note.insertNote(it)
        }
        currentProgressTarget.forEach {
            currentProgressDao.insertCurrentProgress(it)
        }
        standardProgressTarget.forEach {
            standardProgressDao.insertStandardProgress(it)
        }

        assertEquals(user.checkInTime, userDao.getObjectWholly(user.id!!)!!.checkInTime)
        assertTrue(targetDao.contains(target.name, TimeConverters().fromOffsetDateTime(target.checkInTime)))
        assertEquals(target, targetDao.getTargetDataById(target.id))

        targetDao.deleteTarget(target.map(
            ProgressTargetToCache(),
            CurrentToModel(),
            StandardToModel(),
            NoteOfTargetToDomain()
        ))

        assertNull(targetDao.getTargetDataById(target.id))
        assertFalse(targetDao.contains(target.name, TimeConverters().fromOffsetDateTime(target.checkInTime)))
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

        assertEquals(target.checkInTime, targetDao.getTargetDataById(target.id)!!.checkInTime)
        assertEquals(childrenTarget.size, targetDao.getChildrenTarget(target.id).size)

        userDao.deleteObject(user)

        assertNull(targetDao.getTargetDataById(target.id))
        assertTrue(targetDao.getChildrenTarget(target.id).isEmpty())
    }

    @Test
    fun insertAllTargets() = runBlocking {
        val user = obj
        val targets = Targets.getListTargets()

        userDao.insertObject(user)
        targetDao.insertTargets(targets)
        val actual = targetDao.getAllTargets().map { it.map(
            ProgressTargetToCache(), CurrentToModel(), StandardToModel(), NoteOfTargetToDomain()
        ) }
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
        Log.d("fromDao - ", targetDao.getTargetDataById(target.id)!!.currentProgress.toString())
        Log.d("fromDomain - ", target.standardProgress.toString())
        Log.d("fromDao - ", targetDao.getTargetDataById(target.id)!!.standardProgress.toString())

        assertEquals(target.currentProgress, targetDao.getTargetDataById(target.id)!!.currentProgress)
        assertEquals(target.standardProgress, targetDao.getTargetDataById(target.id)!!.standardProgress)

        currentProgressDao.deleteCurrentProgress(currentProgress[0])
        standardProgressDao.deleteStandardProgress(standardProgress[0])

        Log.d(
            "fromDao - after single remove",
            targetDao.getTargetDataById(target.id)!!.currentProgress.toString()
        )
        Log.d(
            "fromDao - after single remove",
            targetDao.getTargetDataById(target.id)!!.standardProgress.toString()
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
