package com.rustamsaga.progress.screens.user.data.subdirectories

import com.rustamsaga.progress.core.data.NoteOfTargetDataSource
import com.rustamsaga.progress.core.data.local.entity.NoteOfProgressTargetEntity
import com.rustamsaga.progress.core.utils.TimeConverters
import kotlinx.coroutines.flow.Flow

interface NoteOfTargetSubdirectory {

    interface Read{

        fun getNoteOfTargetByDate(targetId: Long, date: String): Flow<List<NoteOfProgressTargetEntity>>

        suspend fun contains(targetId: Long, checkInTime: String): Boolean
    }

    interface Save{
        suspend fun insertNote(note: NoteOfProgressTargetEntity): Boolean
        suspend fun deleteNote(note: NoteOfProgressTargetEntity): Boolean
    }

    interface Mutate: Read, Save

    class Mutable(
        private val cache: NoteOfTargetDataSource
    ): Mutate {
        override fun getNoteOfTargetByDate(
            targetId: Long,
            date: String
        ): Flow<List<NoteOfProgressTargetEntity>> {
//            return cache.noteOfTargetDao().getNoteByDate(targetId, date)
            TODO("Not yet implemented")

        }

        override suspend fun contains(targetId: Long, checkInTime: String): Boolean {
            return cache.noteOfTargetDao().contains(targetId, checkInTime)
        }

        override suspend fun insertNote(note: NoteOfProgressTargetEntity): Boolean {
            cache.noteOfTargetDao().insertNote(note)
            return cache.noteOfTargetDao().contains(
                targetId = note.targetId,
                checkInTime = TimeConverters().fromOffsetDateTime(note.checkInTime)
            )
        }

        override suspend fun deleteNote(note: NoteOfProgressTargetEntity): Boolean {
            cache.noteOfTargetDao().deleteNote(note)
            return cache.noteOfTargetDao().contains(
                targetId = note.targetId,
                checkInTime = TimeConverters().fromOffsetDateTime(note.checkInTime)
            )
        }

    }


    class Immutable(
        private val cache: NoteOfTargetDataSource
    ): Read {
        override fun getNoteOfTargetByDate(
            targetId: Long,
            date: String
        ): Flow<List<NoteOfProgressTargetEntity>> {
//            return cache.noteOfTargetDao().getNoteByDate(targetId, date)
            TODO("Not yet implemented")

        }

        override suspend fun contains(targetId: Long, checkInTime: String): Boolean {
            return cache.noteOfTargetDao().contains(targetId, checkInTime)
        }

    }
}