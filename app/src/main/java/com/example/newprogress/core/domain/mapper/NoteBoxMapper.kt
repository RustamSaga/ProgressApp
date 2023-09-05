package com.example.newprogress.core.domain.mapper

import com.example.newprogress.core.data.local.entity.NoteOfObjectEntity
import com.example.newprogress.core.domain.models.NoteOfObjectModel

interface NoteBoxMapper {
    suspend fun getNoteModel(): List<NoteOfObjectModel>
    suspend fun getNoteEntity(): List<NoteOfObjectEntity>
}