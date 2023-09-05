package com.rustamsaga.progress.core.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.newprogress.core.data.local.ObjectOfObservationData
import com.example.newprogress.core.data.local.ProgressDatabase
import com.example.newprogress.core.data.local.dao.NoteOfObjectDao
import com.example.newprogress.core.data.local.dao.ObjectOfObservationDao
import com.example.newprogress.core.data.local.dao.ProgressTargetDao
import com.example.newprogress.core.data.local.entity.ObjectOfObservationEntity
import com.example.newprogress.core.utils.TimeConverters
import com.rustamsaga.progress.core.room.elements.ObjectsEntity.getListOfObject
import com.rustamsaga.progress.core.room.elements.ObjectsEntity.log
import com.rustamsaga.progress.core.room.elements.ObjectsEntity.obj
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


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
        val user = obj
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
        var obj: ObjectOfObservationData? = null
        userDao.getObjectWholly(user.id!!).collect() {
            obj = it
        }
        assertEquals(user.checkInTime, obj!!.checkInTime)


        // delete
        userDao.deleteObject(user)
        assertEquals(
            false,
            userDao.contains(
                firstName = user.firstName,
                checkInTime = TimeConverters().fromOffsetDateTime(user.checkInTime)
            )
        )
        assertEquals(null, userDao.getObjectWholly(user.id!!))

    }

    @Test
    fun createActiveAndObservedObjects_marksTestAsMatched_allTestsPassed() = runBlocking {

        val users = getListOfObject()
        assertEquals(emptyList<ObjectOfObservationEntity>(), userDao.getAllObjects())

        users.forEach {
            log(it.id!!, it.isActive, it.observed)
            userDao.insertObject(it)
        }


        val activeList = users.filter { it.isActive }
        val inactiveList = users.filter { !it.isActive }
        val observedList = users.filter { it.observed }
        val unobservableList = users.filter { !it.observed }


        assertEquals(users, userDao.getAllObjects())
        assertEquals(activeList, userDao.getAllObjects(true))
        assertEquals(inactiveList, userDao.getAllObjects(false))
        assertEquals(observedList, userDao.getAllObserved(true))
        assertEquals(unobservableList, userDao.getAllObserved(false))
    }



}