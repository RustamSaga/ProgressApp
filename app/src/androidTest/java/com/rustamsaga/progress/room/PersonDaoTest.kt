package com.rustamsaga.progress.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rustamsaga.progress.core.data.room.ProgressDatabase
import com.rustamsaga.progress.core.data.room.TestComponents
import com.rustamsaga.progress.core.data.room.dao.PersonDao
import com.rustamsaga.progress.core.data.room.entity.PersonEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.time.OffsetDateTime

@RunWith(AndroidJUnit4::class)
class PersonDaoTest {

    private lateinit var personDao: PersonDao
    private lateinit var db: ProgressDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ProgressDatabase::class.java).build()
        personDao = db.personDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun test_insert_and_check() = runBlocking {

        val expected = TestComponents.person
        personDao.insertPerson(expected)

        val actual = personDao.getPersonById(expected.id!!)
        Assert.assertEquals(expected, actual)
    }

    @Test
    @Throws(Exception::class)
    fun test_delete_person() = runBlocking {
        val unexpected = TestComponents.person
        personDao.insertPerson(unexpected)
        personDao.deletePerson(unexpected)

        val actual = personDao.getPersonById(unexpected.id!!)
        Assert.assertNotEquals(unexpected, actual)
    }

    @Test
    @Throws(Exception::class)
    fun test_update() = runBlocking {
        val expected = TestComponents.person
        personDao.insertPerson(expected)

        val actual = personDao.getPersonById(expected.id!!)
        Assert.assertEquals(expected, actual)

        val changed = expected.copy(id = 1, name = "test changed", observed = false, checkInTime = OffsetDateTime.now())
        personDao.insertPerson(changed)

        val actualChanged = personDao.getPersonById(expected.id!!)
        Assert.assertEquals(changed, actualChanged)

    }

}