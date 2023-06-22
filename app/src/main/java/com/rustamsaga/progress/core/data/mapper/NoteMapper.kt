package com.rustamsaga.progress.core.data.mapper

import com.rustamsaga.progress.core.data.local.entity.NoteOfObjectEntity
import com.rustamsaga.progress.core.data.local.entity.NoteOfProgressTargetEntity
import com.rustamsaga.progress.core.domain.models.NoteOfObjectModel
import com.rustamsaga.progress.core.domain.models.NoteOfTargetModel
import java.time.OffsetDateTime

interface NoteMapper<T> {
    fun map(
        noteId: Long,
        title: String,
        description: String,
        parentId: Long,
        checkInTime: OffsetDateTime,
    ): T

    class NoteOfObjectToDomain : NoteMapper<NoteOfObjectModel> {
        override fun map(
            noteId: Long,
            title: String,
            description: String,
            parentId: Long,
            checkInTime: OffsetDateTime
        ): NoteOfObjectModel {
            return NoteOfObjectModel(
                noteId = noteId,
                title = title,
                description = description,
                userId = parentId,
                checkInTime = checkInTime
            )
        }
    }

    class NoteOfObjectToCache : NoteMapper<NoteOfObjectEntity> {
        override fun map(
            noteId: Long,
            title: String,
            description: String,
            parentId: Long,
            checkInTime: OffsetDateTime
        ): NoteOfObjectEntity {
            return NoteOfObjectEntity(
                noteId = noteId,
                title = title,
                description = description,
                userId = parentId,
                checkInTime = checkInTime
            )
        }
    }
    class NoteOfTargetToDomain : NoteMapper<NoteOfTargetModel> {
        override fun map(
            noteId: Long,
            title: String,
            description: String,
            parentId: Long,
            checkInTime: OffsetDateTime
        ): NoteOfTargetModel {
            return NoteOfTargetModel(
                noteId = noteId,
                title = title,
                description = description,
                targetId = parentId,
                checkInTime = checkInTime
            )
        }
    }

    class NoteOfTargetToCache : NoteMapper<NoteOfProgressTargetEntity> {
        override fun map(
            noteId: Long,
            title: String,
            description: String,
            parentId: Long,
            checkInTime: OffsetDateTime
        ): NoteOfProgressTargetEntity {
            return NoteOfProgressTargetEntity(
                noteId = noteId,
                title = title,
                description = description,
                targetId = parentId,
                checkInTime = checkInTime
            )
        }
    }
}