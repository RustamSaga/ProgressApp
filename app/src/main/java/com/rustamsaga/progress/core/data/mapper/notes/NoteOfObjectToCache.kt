package com.rustamsaga.progress.core.data.mapper.notes

import com.rustamsaga.progress.core.data.local.entity.NoteOfObjectEntity
import com.rustamsaga.progress.core.domain.mapper.NoteMapper
import java.time.OffsetDateTime

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