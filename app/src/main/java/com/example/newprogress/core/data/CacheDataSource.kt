package com.example.newprogress.core.data

import android.content.Context
import androidx.room.Room
import com.example.newprogress.core.data.local.dao.CurrentProgressDao
import com.example.newprogress.core.data.local.dao.NoteOfObjectDao
import com.example.newprogress.core.data.local.dao.NoteOfTargetDao
import com.example.newprogress.core.data.local.dao.ObjectOfObservationDao
import com.example.newprogress.core.data.local.dao.ProgressTargetDao
import com.example.newprogress.core.data.local.dao.StandardProgressDao
import com.example.newprogress.core.data.local.dao.TargetDao
import com.example.newprogress.core.data.local.Headings
import com.example.newprogress.core.data.local.ProgressDatabase
import javax.inject.Inject


interface CacheDataSource :
    ObjectOfObservationCacheDataSource,
    ProgressTargetCacheDataSource,
    CurrentProgressDataSource,
    StandardProgressDataSource,
    TargetDataSource,
    NoteOfObjectDataSource,
    NoteOfTargetDataSource {

    class Base @Inject constructor(context: Context) : CacheDataSource {

        private val progressDB: ProgressDatabase = Room.databaseBuilder(
            context,
            ProgressDatabase::class.java,
            Headings.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()

        override suspend fun objectOfObservationDao(): ObjectOfObservationDao {
            return progressDB.objectOfObservationDao()
        }

        override suspend fun progressTargetDao(): ProgressTargetDao {
            return progressDB.progressTargetDao()
        }

        override suspend fun currentProgressDao(): CurrentProgressDao {
            return progressDB.currentProgressDao()
        }

        override suspend fun standardProgressDao(): StandardProgressDao {
            return progressDB.standardProgressDao()
        }

        override suspend fun targetDao(): TargetDao {
            return progressDB.targetDao()
        }

        override suspend fun noteOfObjectDao(): NoteOfObjectDao {
            return progressDB.noteObjectOfObservationDao()
        }

        override suspend fun noteOfTargetDao(): NoteOfTargetDao {
            return progressDB.noteOfTargetDao()
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