package com.rustamsaga.progress.core.data.mapper.notes

import com.rustamsaga.progress.core.domain.mapper.NoteMapper
import com.rustamsaga.progress.core.domain.models.NoteOfProgressTargetModel
import java.time.OffsetDateTime

class NoteOfTargetToDomain : NoteMapper<NoteOfProgressTargetModel> {
    override fun map(
        noteId: Long,
        title: String,
        description: String,
        parentId: Long,
        checkInTime: OffsetDateTime
    ): NoteOfProgressTargetModel {
        return NoteOfProgressTargetModel(
            noteId = noteId,
            title = title,
            description = description,
            targetId = parentId,
            checkInTime = checkInTime
        )
    }
}

