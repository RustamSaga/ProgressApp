package com.rustamsaga.progress.core.data.mapper.boxes

import com.rustamsaga.progress.core.data.local.entity.NoteOfObjectEntity
import com.rustamsaga.progress.core.domain.mapper.NoteBoxMapper
import com.rustamsaga.progress.core.domain.models.NoteOfObjectModel

class NoteBoxMapperImp(
    private val noteModel: List<NoteOfObjectModel> = emptyList(),
    private val noteEntity: List<NoteOfObjectEntity> = emptyList()
) : NoteBoxMapper {
    override suspend fun getNoteModel() = noteModel

    override suspend fun getNoteEntity() = noteEntity

}