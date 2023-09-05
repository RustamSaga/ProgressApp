package com.example.newprogress.screens.user.data.subdirectories

import com.example.newprogress.core.data.NoteOfObjectDataSource
import com.example.newprogress.core.data.local.entity.NoteOfObjectEntity
import com.example.newprogress.core.utils.TimeConverters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface NoteOfObjectSubdirectory {

    interface Read {
        fun getNoteOfObjectByDate(userId: Long, date: String): Flow<List<NoteOfObjectEntity>>
        suspend fun contains(userId: Long, checkInTime: String): Boolean
    }

    interface Save {
        suspend fun insertNote(note: NoteOfObjectEntity): Boolean
        suspend fun deleteNote(note: NoteOfObjectEntity): Boolean
    }

    interface Mutate : Save, Read

    class Mutable(
        private val cache: NoteOfObjectDataSource
    ) : Mutate {
        override suspend fun insertNote(note: NoteOfObjectEntity): Boolean {
            cache.noteOfObjectDao().insertNote(note)
            return cache.noteOfObjectDao()
                .contains(note.userId, TimeConverters().fromOffsetDateTime(note.checkInTime))
        }

        override suspend fun deleteNote(note: NoteOfObjectEntity): Boolean {
            cache.noteOfObjectDao().deleteNote(note)
            return cache.noteOfObjectDao()
                .contains(note.userId, TimeConverters().fromOffsetDateTime(note.checkInTime))
        }

        override fun getNoteOfObjectByDate(userId: Long, date: String): Flow<List<NoteOfObjectEntity>> {
            return flow {
                cache.noteOfObjectDao().getNotesByDate(userId, date)
            }
        }

        override suspend fun contains(userId: Long, checkInTime: String): Boolean {
            return cache.noteOfObjectDao().contains(userId, checkInTime)
        }
    }


    class Immutable(
        private val cache: NoteOfObjectDataSource
    ): Read {
        override fun getNoteOfObjectByDate(userId: Long, date: String): Flow<List<NoteOfObjectEntity>> {
            return flow {
                cache.noteOfObjectDao().getNotesByDate(userId, date)
            }
        }

        override suspend fun contains(userId: Long, checkInTime: String): Boolean {
            return cache.noteOfObjectDao().contains(userId, checkInTime)
        }

    }
}