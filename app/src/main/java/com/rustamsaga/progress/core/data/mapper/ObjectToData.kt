package com.rustamsaga.progress.core.data.mapper

import com.rustamsaga.progress.core.data.local.ObjectOfObservationData
import com.rustamsaga.progress.core.domain.mapper.NoteBoxMapper
import com.rustamsaga.progress.core.domain.mapper.ObjectOfObservationMapper
import com.rustamsaga.progress.core.domain.mapper.TargetBoxMapper
import java.time.OffsetDateTime

class ObjectToData : ObjectOfObservationMapper<ObjectOfObservationData> {
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
    ) = ObjectOfObservationData(
        id = id,
        firstName = firstName,
        lastName = lastName,
        description = description,
        observed = observed,
        checkInTime = checkInTime,
        isActive = isActive,
        notes = notes.getNoteEntity()
    )
}