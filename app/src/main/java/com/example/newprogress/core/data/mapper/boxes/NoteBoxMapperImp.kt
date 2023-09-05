package com.example.newprogress.core.data.mapper.boxes

import com.example.newprogress.core.data.local.entity.NoteOfObjectEntity
import com.example.newprogress.core.domain.mapper.NoteBoxMapper
import com.example.newprogress.core.domain.models.NoteOfObjectModel

class NoteBoxMapperImp(
    private val noteModel: List<NoteOfObjectModel> = emptyList(),
    private val noteEntity: List<NoteOfObjectEntity> = emptyList()
) : NoteBoxMapper {
    override suspend fun getNoteModel() = noteModel

    override suspend fun getNoteEntity() = noteEntity

}