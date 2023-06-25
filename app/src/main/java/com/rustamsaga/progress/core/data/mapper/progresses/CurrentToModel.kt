package com.rustamsaga.progress.core.data.mapper.progresses

import com.rustamsaga.progress.core.domain.mapper.ProgressMapper
import com.rustamsaga.progress.core.domain.models.CurrentProgressModel
import java.time.OffsetDateTime

class CurrentToModel : ProgressMapper<CurrentProgressModel> {
    override fun map(
        idUnit: Long,
        targetId: Long,
        title: String,
        description: String,
        checkInTime: OffsetDateTime,
        level: String
    ) = CurrentProgressModel(
        idUnit, targetId, title, description, checkInTime, level
    )

}