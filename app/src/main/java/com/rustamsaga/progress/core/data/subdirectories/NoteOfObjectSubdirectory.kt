package com.rustamsaga.progress.core.data.subdirectories

import com.rustamsaga.progress.core.data.NoteOfObjectDataSource
import com.rustamsaga.progress.core.data.local.entity.NoteOfObjectEntity
import com.rustamsaga.progress.core.utils.TimeConverters
import kotlinx.coroutines.flow.Flow

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
            return cache.noteOfObjectDao().getNoteByDate(userId, date)
        }

        override suspend fun contains(userId: Long, checkInTime: String): Boolean {
            return cache.noteOfObjectDao().contains(userId, checkInTime)
        }
    }


    class Immutable(
        private val cache: NoteOfObjectDataSource
    ): Read{
        override fun getNoteOfObjectByDate(userId: Long, date: String): Flow<List<NoteOfObjectEntity>> {
            return cache.noteOfObjectDao().getNoteByDate(userId, date)
        }

        override suspend fun contains(userId: Long, checkInTime: String): Boolean {
            return cache.noteOfObjectDao().contains(userId, checkInTime)
        }

    }
}