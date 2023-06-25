package com.rustamsaga.progress.core.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rustamsaga.progress.core.data.local.ProgressDatabase
import com.rustamsaga.progress.core.data.local.dao.NoteOfObjectDao
import com.rustamsaga.progress.core.data.local.dao.ObjectOfObservationDao
import com.rustamsaga.progress.core.data.local.dao.ProgressTargetDao
import com.rustamsaga.progress.core.utils.TimeConverters
import com.rustamsaga.progress.core.room.elements.Notes
import com.rustamsaga.progress.core.room.elements.Objects
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class ObjectNoteTesting {

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
    fun createObjectNotes_marksTestAsMatched_allTestsPassed() = runBlocking {
        val user = Objects.obj
        val notes = Notes.createNotesOfUser(user.id!!)

        userDao.insertObject(user)
        notes.forEach {
            noteDao.insertNote(it)
        }

        notes.forEach {
            assertTrue(
                noteDao.contains(
                    userId = it.userId,
                    checkInTime = TimeConverters().fromOffsetDateTime(it.checkInTime)
                )
            )
        }

        val actual = noteDao.getNotesByDate(userId = user.id!!, date = "2023%").isNotEmpty()

        assertTrue(actual)

        noteDao.deleteNote(notes[0])
        assertTrue(
            noteDao.getNotesByDate(
                userId = notes[0].userId,
                date = TimeConverters().fromOffsetDateTime(notes[0].checkInTime)
            ).isEmpty()
        )

        assertFalse(
            noteDao.contains(
                userId = notes[0].userId,
                checkInTime = TimeConverters().fromOffsetDateTime(notes[0].checkInTime)
            )
        )
    }

    @Test
    fun noteDependency_deletingNotesAfterDeletingAnObject_allTestsPassed() = runBlocking {
        val user = Objects.obj
        val notes = Notes.createNotesOfUser(user.id!!)

        userDao.insertObject(user)
        notes.forEach {
            noteDao.insertNote(it)
            assertTrue(
                noteDao.contains(
                    it.userId,
                    TimeConverters().fromOffsetDateTime(it.checkInTime)
                )
            )
        }

        userDao.deleteObject(user)

        val actualList = noteDao.getNotesByUser(user.id!!).isEmpty()
        assertTrue(actualList)

        val actualNote = noteDao.contains(
            user.id!!,
            TimeConverters().fromOffsetDateTime(notes[0].checkInTime)
        )
        assertFalse(actualNote)
    }

}