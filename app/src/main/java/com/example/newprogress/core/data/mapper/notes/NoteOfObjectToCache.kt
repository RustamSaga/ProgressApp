package com.example.newprogress.core.data.mapper.notes

import com.example.newprogress.core.data.local.entity.NoteOfObjectEntity
import com.example.newprogress.core.domain.mapper.NoteMapper
import java.time.OffsetDateTime
import javax.inject.Inject

class NoteOfObjectToCache @Inject constructor(): NoteMapper<NoteOfObjectEntity> {
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