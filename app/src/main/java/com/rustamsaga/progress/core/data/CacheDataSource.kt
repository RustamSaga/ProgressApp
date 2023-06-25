package com.rustamsaga.progress.core.data

import android.content.Context
import androidx.room.Room
import com.rustamsaga.progress.core.data.local.Headings
import com.rustamsaga.progress.core.data.local.ProgressDatabase
import com.rustamsaga.progress.core.data.local.dao.*


interface CacheDataSource :
    ObjectOfObservationCacheDataSource,
    ProgressTargetCacheDataSource,
    CurrentProgressDataSource,
    StandardProgressDataSource,
    TargetDataSource,
    NoteOfObjectDataSource,
    NoteOfTargetDataSource {

    abstract class Base(context: Context) : CacheDataSource {

        private val ProgressDB: ProgressDatabase = Room.databaseBuilder(
            context,
            ProgressDatabase::class.java,
            Headings.DATABASE_NAME
        ).build()

        override fun objectOfObservationDao(): ObjectOfObservationDao {
            return ProgressDB.objectOfObservationDao()
        }

        override fun progressTargetDao(): ProgressTargetDao {
            return ProgressDB.progressTargetDao()
        }

        override fun currentProgressDao(): CurrentProgressDao {
            return ProgressDB.currentProgressDao()
        }

        override fun standardProgressDao(): StandardProgressDao {
            return ProgressDB.standardProgressDao()
        }

        override fun targetDao(): TargetDao {
            return ProgressDB.targetDao()
        }

        override fun noteOfObjectDao(): NoteOfObjectDao {
            return ProgressDB.noteObjectOfObservationDao()
        }

        override fun noteOfTargetDao(): NoteOfTargetDao {
            return ProgressDB.noteOfTargetDao()
        }

    }

}

interface ObjectOfObservationCacheDataSource {
    fun objectOfObservationDao(): ObjectOfObservationDao
}

interface ProgressTargetCacheDataSource {
    fun progressTargetDao(): ProgressTargetDao
}

interface CurrentProgressDataSource {
    fun currentProgressDao(): CurrentProgressDao
}

interface StandardProgressDataSource {
    fun standardProgressDao(): StandardProgressDao
}

interface TargetDataSource {
    fun targetDao(): TargetDao
}

interface NoteOfObjectDataSource {
    fun noteOfObjectDao(): NoteOfObjectDao
}

interface NoteOfTargetDataSource {
    fun noteOfTargetDao(): NoteOfTargetDao
}