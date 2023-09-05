package com.example.newprogress.core.data.mapper.notes

import com.example.newprogress.core.domain.mapper.NoteMapper
import com.example.newprogress.core.domain.models.NoteOfObjectModel
import java.time.OffsetDateTime

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