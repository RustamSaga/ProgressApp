package com.example.newprogress.core.data.mapper.notes

import com.example.newprogress.core.domain.mapper.NoteMapper
import com.example.newprogress.core.domain.models.NoteOfProgressTargetModel
import java.time.OffsetDateTime
import javax.inject.Inject

class NoteOfTargetToDomain @Inject constructor(): NoteMapper<NoteOfProgressTargetModel> {
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

