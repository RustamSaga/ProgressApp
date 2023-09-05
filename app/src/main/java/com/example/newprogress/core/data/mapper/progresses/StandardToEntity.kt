package com.example.newprogress.core.data.mapper.progresses

import com.example.newprogress.core.data.local.entity.StandardProgressEntity
import com.example.newprogress.core.domain.mapper.ProgressMapper
import java.time.OffsetDateTime
import javax.inject.Inject

class StandardToEntity @Inject constructor(): ProgressMapper<StandardProgressEntity> {
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