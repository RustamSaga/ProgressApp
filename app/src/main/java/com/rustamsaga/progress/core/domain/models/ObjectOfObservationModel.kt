package com.rustamsaga.progress.core.domain.models

import com.rustamsaga.progress.core.domain.mapper.NoteBoxMapper
import com.rustamsaga.progress.core.domain.mapper.NoteMapper
import com.rustamsaga.progress.core.domain.mapper.ObjectOfObservationMapper
import com.rustamsaga.progress.core.domain.mapper.TargetBoxMapper
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
) {

    suspend fun <T> map(
        mapper: ObjectOfObservationMapper<T>,
        noteBox: NoteBoxMapper,
        targetBox: TargetBoxMapper
    ): T = mapper.mapObject(
        id = id,
        firstName = firstName,
        lastName = lastName,
        description = description,
        observed = observed,
        checkInTime = checkInTime,
        isActive = isActive,
        targets = targetBox,
        notes = noteBox
    )
}

data class NoteOfObjectModel(
    val noteId: Long,
    val title: String,
    val description: String,
    val userId: Long,
    val checkInTime: OffsetDateTime
) {
    fun <T> map(mapper: NoteMapper<T>): T = mapper.map(
        noteId = noteId,
        title = title,
        description = description,
        parentId = userId,
        checkInTime = checkInTime
    )
}
