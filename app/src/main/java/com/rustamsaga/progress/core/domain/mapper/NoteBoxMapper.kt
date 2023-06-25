package com.rustamsaga.progress.core.domain.mapper

import com.rustamsaga.progress.core.data.local.entity.NoteOfObjectEntity
import com.rustamsaga.progress.core.domain.models.NoteOfObjectModel

interface NoteBoxMapper {
    suspend fun getNoteModel(): List<NoteOfObjectModel>
    suspend fun getNoteEntity(): List<NoteOfObjectEntity>
}