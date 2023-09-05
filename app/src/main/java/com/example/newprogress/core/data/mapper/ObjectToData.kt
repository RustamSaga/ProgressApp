package com.example.newprogress.core.data.mapper

import com.example.newprogress.core.data.local.ObjectOfObservationData
import com.example.newprogress.core.domain.mapper.NoteBoxMapper
import com.example.newprogress.core.domain.mapper.ObjectOfObservationMapper
import com.example.newprogress.core.domain.mapper.TargetBoxMapper
import java.time.OffsetDateTime
import javax.inject.Inject

class ObjectToData @Inject constructor(): ObjectOfObservationMapper<ObjectOfObservationData> {
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