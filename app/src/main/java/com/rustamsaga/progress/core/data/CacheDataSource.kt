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

    class Base(context: Context) : CacheDataSource {

        private val ProgressDB: ProgressDatabase = Room.databaseBuilder(
            context,
            ProgressDatabase::class.java,
            Headings.DATABASE_NAME
        ).build()

        override suspend fun objectOfObservationDao(): ObjectOfObservationDao {
            return ProgressDB.objectOfObservationDao()
        }

        override suspend fun progressTargetDao(): ProgressTargetDao {
            return ProgressDB.progressTargetDao()
        }

        override suspend fun currentProgressDao(): CurrentProgressDao {
            return ProgressDB.currentProgressDao()
        }

        override suspend fun standardProgressDao(): StandardProgressDao {
            return ProgressDB.standardProgressDao()
        }

        override suspend fun targetDao(): TargetDao {
            return ProgressDB.targetDao()
        }

        override suspend fun noteOfObjectDao(): NoteOfObjectDao {
            return ProgressDB.noteObjectOfObservationDao()
        }

        override suspend fun noteOfTargetDao(): NoteOfTargetDao {
            return ProgressDB.noteOfTargetDao()
        }

    }

}

interface ObjectOfObservationCacheDataSource {
    suspend fun objectOfObservationDao(): ObjectOfObservationDao
}

interface ProgressTargetCacheDataSource {
    suspend fun progressTargetDao(): ProgressTargetDao
}

interface CurrentProgressDataSource {
    suspend fun currentProgressDao(): CurrentProgressDao
}

interface StandardProgressDataSource {
    suspend fun standardProgressDao(): StandardProgressDao
}

interface TargetDataSource {
    suspend fun targetDao(): TargetDao
}

interface NoteOfObjectDataSource {
    suspend fun noteOfObjectDao(): NoteOfObjectDao
}

interface NoteOfTargetDataSource {
    suspend fun noteOfTargetDao(): NoteOfTargetDao
}