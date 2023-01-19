package com.rustamsaga.progress.core.data

import android.content.Context
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.rustamsaga.progress.core.data.room.Headings
import com.rustamsaga.progress.core.data.room.ProgressDatabase
import com.rustamsaga.progress.core.data.room.dao.CurrentProgressDao
import com.rustamsaga.progress.core.data.room.dao.PersonDao
import com.rustamsaga.progress.core.data.room.dao.StandardProgressDao
import com.rustamsaga.progress.core.data.room.dao.TargetDao

interface CacheDataSource :
    PersonCacheDataSource,
    TargetCacheDataSource,
    CurrentProgressDataSource,
    StandardProgressDataSource {

    class Base(context: Context) : CacheDataSource {

        private val ProgressDB: ProgressDatabase = Room.databaseBuilder(
            context,
            ProgressDatabase::class.java,
            Headings.TEST_DATABASE
        ).build()

        override fun personDao(): PersonDao {
            return ProgressDB.personDao()
        }

        override fun targetDao(): TargetDao {
            return ProgressDB.targetDao()
        }

        override fun currentProgressDao(): CurrentProgressDao {
            return ProgressDB.currentProgressDao()
        }

        override fun standardProgressDao(): StandardProgressDao {
            return ProgressDB.standardProgressDao()
        }

    }

}

interface PersonCacheDataSource {
    fun personDao(): PersonDao
}

interface TargetCacheDataSource {
    fun targetDao(): TargetDao
}

interface CurrentProgressDataSource {
    fun currentProgressDao(): CurrentProgressDao
}

interface StandardProgressDataSource {
    fun standardProgressDao(): StandardProgressDao
}