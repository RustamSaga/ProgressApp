package com.rustamsaga.progress.core.data.mapper.progresses

import com.rustamsaga.progress.core.domain.mapper.ProgressMapper
import com.rustamsaga.progress.core.domain.models.StandardProgressModel
import java.time.OffsetDateTime

class StandardToModel : ProgressMapper<StandardProgressModel> {
    override fun map(
        idUnit: Long,
        targetId: Long,
        title: String,
        description: String,
        checkInTime: OffsetDateTime,
        level: String
    ) = StandardProgressModel(
        idUnit, targetId, title, description, checkInTime, level
    )
}

