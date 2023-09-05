package com.rustamsaga.progress.core.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.newprogress.core.data.local.dao.NoteOfTargetDao
import com.example.newprogress.core.data.local.dao.ObjectOfObservationDao
import com.example.newprogress.core.data.local.dao.ProgressTargetDao
import com.example.newprogress.core.data.local.ProgressDatabase
import com.example.newprogress.core.utils.TimeConverters
import com.rustamsaga.progress.core.room.elements.NotesEntity
import com.rustamsaga.progress.core.room.elements.ObjectsEntity
import com.rustamsaga.progress.core.room.elements.Targets
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException

class NoteOfTargetTest {

    private lateinit var db: ProgressDatabase
    private lateinit var userDao: ObjectOfObservationDao
    private lateinit var targetDao: ProgressTargetDao
    private lateinit var note: NoteOfTargetDao

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

    }

    @After
    @Throws(IOException::class)
    fun clear() {
        db.close()
    }

    @Test
    fun createNote_marksTestAsMatched_allTestsPassed() = runBlocking {
        val user = ObjectsEntity.obj
        val target = Targets.progressTargetEntity
        val noteOfTarget = NotesEntity.createNoteOfProgressTarget(target.id!!)

        userDao.insertObject(user)
        targetDao.insertTarget(target)
        noteOfTarget.forEach {
            note.insertNote(it)

            Assert.assertTrue(
                note.contains(
                    it.title, TimeConverters().fromOffsetDateTime(it.checkInTime)
                )
            )
        }

        note.deleteNote(noteOfTarget[0])
        Assert.assertFalse(
            note.contains(
                target.name,
                TimeConverters().fromOffsetDateTime(noteOfTarget[0].checkInTime)
            )
        )
    }

    @Test
    fun linkageTest_byDeleteTarget_allTestsPassed() = runBlocking {
        val user = ObjectsEntity.obj
        val target = Targets.progressTargetEntity
        val noteOfTarget = NotesEntity.createNoteOfProgressTarget(target.id!!)

        userDao.insertObject(user)
        targetDao.insertTarget(target)
        noteOfTarget.forEach {
            note.insertNote(it)

            Assert.assertTrue(
                note.contains(
                    it.title, TimeConverters().fromOffsetDateTime(it.checkInTime)
                )
            )
        }

        targetDao.deleteTarget(target)
        val condition = note.getNotesByTargetId(target.id!!).firstOrNull()
        Assert.assertNull(condition)

    }

    @Test
    fun linkageTest_byDeleteUser_allTestsPassed() = runBlocking {
        val user = ObjectsEntity.obj
        val target = Targets.progressTargetEntity
        val noteOfTarget = NotesEntity.createNoteOfProgressTarget(target.id!!)

        userDao.insertObject(user)
        targetDao.insertTarget(target)
        noteOfTarget.forEach {
            note.insertNote(it)

            Assert.assertTrue(
                note.contains(
                    it.title, TimeConverters().fromOffsetDateTime(it.checkInTime)
                )
            )
        }

        userDao.deleteObject(user)
        val condition = note.getNotesByTargetId(target.id!!).firstOrNull()
        Assert.assertNull(condition)

    }
}