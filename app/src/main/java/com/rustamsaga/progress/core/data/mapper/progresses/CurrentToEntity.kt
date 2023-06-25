package com.rustamsaga.progress.core.data.mapper.progresses

import com.rustamsaga.progress.core.data.local.entity.CurrentProgressEntity
import com.rustamsaga.progress.core.domain.mapper.ProgressMapper
import java.time.OffsetDateTime

class CurrentToEntity : ProgressMapper<CurrentProgressEntity> {
    override fun map(
        idUnit: Long,
        targetId: Long,
        title: String,
        description: String,
        checkInTime: OffsetDateTime,
        level: String
    ) = CurrentProgressEntity(
        idUnit, targetId, title, description, checkInTime, level
    )

}