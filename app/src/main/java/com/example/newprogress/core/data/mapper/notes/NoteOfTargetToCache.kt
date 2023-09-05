package com.example.newprogress.core.data.mapper.notes

import com.example.newprogress.core.data.local.entity.NoteOfProgressTargetEntity
import com.example.newprogress.core.domain.mapper.NoteMapper
import java.time.OffsetDateTime
import javax.inject.Inject

class NoteOfTargetToCache @Inject constructor(): NoteMapper<NoteOfProgressTargetEntity> {
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