package com.example.newprogress.core.data.mapper.progresses

import com.example.newprogress.core.domain.mapper.ProgressMapper
import com.example.newprogress.core.domain.models.StandardProgressModel
import java.time.OffsetDateTime
import javax.inject.Inject

class StandardToModel @Inject constructor(): ProgressMapper<StandardProgressModel> {
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

