package com.example.newprogress.core.data.mapper.progresses

import com.example.newprogress.core.domain.mapper.ProgressMapper
import com.example.newprogress.core.domain.models.CurrentProgressModel
import java.time.OffsetDateTime
import javax.inject.Inject

class CurrentToModel @Inject constructor() : ProgressMapper<CurrentProgressModel> {
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