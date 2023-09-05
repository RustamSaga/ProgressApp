package com.example.newprogress.core.data.mapper

import com.example.newprogress.core.data.local.entity.ObjectOfObservationEntity
import com.example.newprogress.core.domain.mapper.NoteBoxMapper
import com.example.newprogress.core.domain.mapper.ObjectOfObservationMapper
import com.example.newprogress.core.domain.mapper.TargetBoxMapper
import java.time.OffsetDateTime
import javax.inject.Inject


class ObjectToCache @Inject constructor() : ObjectOfObservationMapper<ObjectOfObservationEntity> {
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
    ) = ObjectOfObservationEntity(
        id = id,
        firstName = firstName,
        lastName = lastName,
        description = description,
        observed = observed,
        checkInTime = checkInTime,
        isActive = isActive,
    )
}