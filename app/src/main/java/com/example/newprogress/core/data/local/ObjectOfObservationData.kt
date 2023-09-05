package com.example.newprogress.core.data.local

import androidx.room.Relation
import com.example.newprogress.core.data.local.entity.NoteOfObjectEntity
import com.example.newprogress.core.data.mapper.boxes.NoteBoxMapperImp
import com.example.newprogress.core.data.mapper.notes.NoteOfObjectToDomain
import com.example.newprogress.core.domain.mapper.NoteBoxMapper
import com.example.newprogress.core.domain.mapper.ObjectOfObservationMapper
import com.example.newprogress.core.domain.mapper.TargetBoxMapper
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
