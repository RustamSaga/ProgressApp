package com.rustamsaga.progress.core.data.mapper

import com.rustamsaga.progress.core.domain.mapper.NoteBoxMapper
import com.rustamsaga.progress.core.domain.mapper.ObjectOfObservationMapper
import com.rustamsaga.progress.core.domain.mapper.TargetBoxMapper
import com.rustamsaga.progress.core.domain.models.ObjectOfObservationModel
import java.time.OffsetDateTime

class ObjectToDomain : ObjectOfObservationMapper<ObjectOfObservationModel> {
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