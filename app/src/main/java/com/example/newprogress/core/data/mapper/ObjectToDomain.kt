package com.example.newprogress.core.data.mapper

import com.example.newprogress.core.domain.mapper.NoteBoxMapper
import com.example.newprogress.core.domain.mapper.ObjectOfObservationMapper
import com.example.newprogress.core.domain.mapper.TargetBoxMapper
import com.example.newprogress.core.domain.models.ObjectOfObservationModel
import java.time.OffsetDateTime
import javax.inject.Inject

class ObjectToDomain @Inject constructor(): ObjectOfObservationMapper<ObjectOfObservationModel> {
    override suspend fun mapObject(
        id: Long,
        firstName: String,
        lastName: String,
        description: String,
        observed: Boolean,
        checkInTime: OffsetDateTime,
        isActive: Boolean,
        targets: TargetBoxMapper,
        notes: NoteBoxMapper
    ) = ObjectOfObservationModel(
        id = id,
        firstName = firstName,
        lastName = lastName,
        description = description,
        observed = observed,
        checkInTime = checkInTime,
        isActive = isActive,
        targets = targets.getTargetModel(),
        notes = notes.getNoteModel()
    )
}