package com.rustamsaga.progress.core.domain.mapper

import java.time.OffsetDateTime

interface ObjectOfObservationMapper<T> {
    suspend fun mapObject(
        id: Long,
        firstName: String,
        lastName: String,
        description: String,
        observed: Boolean = true,
        checkInTime: OffsetDateTime,
        isActive: Boolean,
        targets: TargetBoxMapper,
        notes: NoteBoxMapper
    ): T
}