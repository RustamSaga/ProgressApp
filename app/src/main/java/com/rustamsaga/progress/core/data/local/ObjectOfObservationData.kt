package com.rustamsaga.progress.core.data.local

import androidx.room.Relation
import com.rustamsaga.progress.core.data.local.entity.NoteOfObjectEntity
import com.rustamsaga.progress.core.data.mapper.boxes.NoteBoxMapperImp
import com.rustamsaga.progress.core.data.mapper.notes.NoteOfObjectToDomain
import com.rustamsaga.progress.core.domain.mapper.*
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
    suspend fun <O> map(
        mapper: ObjectOfObservationMapper<O>,
        noteBox: NoteBoxMapper,
        targetBox: TargetBoxMapper
    ): O = mapper.mapObject(
        id = id,
        firstName = firstName,
        lastName = lastName,
        description = description,
        observed = observed,
        checkInTime = checkInTime,
        isActive = isActive,
        targets = targetBox,
        notes = NoteBoxMapperImp(
            noteModel = notes.map { it.map(NoteOfObjectToDomain()) },
            noteEntity = notes
        )
    )
}
