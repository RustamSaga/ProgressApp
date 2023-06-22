package com.rustamsaga.progress.core.data.local.entity

import androidx.room.Relation
import com.rustamsaga.progress.core.data.mapper.ObjectOfObservationMapper
import com.rustamsaga.progress.core.domain.models.ObjectsInterface
import java.time.OffsetDateTime

data class ObjectOfObservationData(
    val id: Long,
    val firstName: String,
    val lastName: String = "",
    val description: String,
    val observed: Boolean = true,
    val checkInTime: OffsetDateTime,
    val isActive: Boolean,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val notes: List<NoteOfObjectEntity>
) {
    suspend fun <O, T, N> map(mapper: ObjectOfObservationMapper.<O, T, N>): T = mapper.mapObject(
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
