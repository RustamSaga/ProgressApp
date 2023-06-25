package com.rustamsaga.progress.core.data.mapper.progresses

import com.rustamsaga.progress.core.data.local.entity.StandardProgressEntity
import com.rustamsaga.progress.core.domain.mapper.ProgressMapper
import java.time.OffsetDateTime

class StandardToEntity : ProgressMapper<StandardProgressEntity> {
    override fun map(
        idUnit: Long,
        targetId: Long,
        title: String,
        description: String,
        checkInTime: OffsetDateTime,
        level: String
    ) = StandardProgressEntity(
        idUnit, targetId, title, description, checkInTime, level
    )

}