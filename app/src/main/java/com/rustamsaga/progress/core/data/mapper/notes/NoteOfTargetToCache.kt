package com.rustamsaga.progress.core.data.mapper.notes

import com.rustamsaga.progress.core.data.local.entity.NoteOfProgressTargetEntity
import com.rustamsaga.progress.core.domain.mapper.NoteMapper
import java.time.OffsetDateTime

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