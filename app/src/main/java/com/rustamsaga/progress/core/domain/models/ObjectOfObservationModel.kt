package com.rustamsaga.progress.core.domain.models

import com.rustamsaga.progress.core.data.mapper.NoteMapper
import com.rustamsaga.progress.core.data.mapper.ObjectOfObservationMapper
import java.time.OffsetDateTime

data class ObjectOfObservationModel(
    val id: Long,
    val firstName: String,
    val lastName: String = "",
    val description: String,
    val observed: Boolean = true,
    val checkInTime: OffsetDateTime,
    val isActive: Boolean,
    val targets: List<ProgressTargetModel>,
    val notes: List<NoteOfObjectModel>
) : ObjectsInterface {

    suspend fun <T> map(mapper: ObjectOfObservationMapper<T>): T = mapper.mapObject(
        id = id,
        firstName = firstName,
        lastName = lastName,
        description = description,
        observed = observed,
        checkInTime = checkInTime,
        isActive = isActive,
        targets = targets,
        notes = notes
    )
}

data class NoteOfObjectModel(
    val noteId: Long,
    val title: String,
    val description: String,
    val userId: Long,
    val checkInTime: OffsetDateTime
) : NoteOfObjectInterface {
    fun <T> map(mapper: NoteMapper<T>): T = mapper.map(
        noteId = noteId,
        title = title,
        description = description,
        parentId = userId,
        checkInTime = checkInTime
    )
}
