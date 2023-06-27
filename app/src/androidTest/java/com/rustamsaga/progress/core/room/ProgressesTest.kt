package com.rustamsaga.progress.core.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.rustamsaga.progress.core.data.local.ProgressDatabase
import com.rustamsaga.progress.core.data.local.dao.*
import com.rustamsaga.progress.core.data.local.entity.CurrentProgressEntity
import com.rustamsaga.progress.core.data.local.entity.StandardProgressEntity
import com.rustamsaga.progress.core.data.mapper.notes.NoteOfTargetToDomain
import com.rustamsaga.progress.core.data.mapper.progresses.CurrentToModel
import com.rustamsaga.progress.core.data.mapper.progresses.ProgressTargetToCache
import com.rustamsaga.progress.core.data.mapper.progresses.StandardToModel
import com.rustamsaga.progress.core.utils.TimeConverters
import com.rustamsaga.progress.core.room.elements.Objects
import com.rustamsaga.progress.core.room.elements.Targets
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException

class ProgressesTest {

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
    fun gettingProgresses_marksTestAsMatched_allTestsPassed() = runBlocking {
        val user = Objects.obj
        val target = Targets.progressTargetData
        val currentProgressTarget = target.currentProgress
        val standardProgressTarget = target.standardProgress

        Assert.assertEquals(
            emptyList<CurrentProgressEntity>(),
            currentProgressDao.getCurrentProgressesByTargetId(target.id)
        )
        Assert.assertEquals(
            emptyList<StandardProgressEntity>(),
            standardProgressDao.getStandardProgressByTargetId(target.id)
        )

        userDao.insertObject(user)
        targetDao.insertTarget(target.map(
            ProgressTargetToCache(),
            CurrentToModel(),
            StandardToModel(),
            NoteOfTargetToDomain()
        ))

        currentProgressTarget.forEach {
            currentProgressDao.insertCurrentProgress(it)
        }
        standardProgressTarget.forEach {
            standardProgressDao.insertStandardProgress(it)
        }

        Assert.assertEquals(
            currentProgressTarget,
            currentProgressDao.getCurrentProgressesByTargetId(target.id)
        )
        Assert.assertEquals(
            standardProgressTarget,
            standardProgressDao.getStandardProgressByTargetId(target.id)
        )


        currentProgressDao.deleteCurrentProgress(currentProgressTarget[0])
        standardProgressDao.deleteStandardProgress(standardProgressTarget[0])

        Assert.assertFalse(
            currentProgressDao.contains(
                currentProgressTarget[0].title,
                TimeConverters().fromOffsetDateTime(currentProgressTarget[0].checkInTime)
            )
        )

        Assert.assertFalse(
            standardProgressDao.contains(
                standardProgressTarget[0].title,
                TimeConverters().fromOffsetDateTime(standardProgressTarget[0].checkInTime)
            )
        )
    }
}