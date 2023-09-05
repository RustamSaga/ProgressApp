package com.example.newprogress.core.data.mapper.progresses

import com.example.newprogress.core.data.local.entity.CurrentProgressEntity
import com.example.newprogress.core.domain.mapper.ProgressMapper
import java.time.OffsetDateTime
import javax.inject.Inject

class CurrentToEntity @Inject constructor(): ProgressMapper<CurrentProgressEntity> {
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