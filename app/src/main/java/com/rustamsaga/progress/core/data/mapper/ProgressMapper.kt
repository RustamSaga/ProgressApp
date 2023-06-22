package com.rustamsaga.progress.core.data.mapper

import com.rustamsaga.progress.core.domain.models.CurrentProgressModel
import com.rustamsaga.progress.core.domain.models.StandardProgressModel
import java.time.OffsetDateTime

interface ProgressMapper<T> {
    fun map(
        idUnit: Long,
        targetId: Long,
        title: String,
        description: String,
        checkInTime: OffsetDateTime,
        level: String
    ): T

    class Current : ProgressMapper<CurrentProgressModel> {
        override fun map(
            idUnit: Long,
            targetId: Long,
            title: String,
            description: String,
            checkInTime: OffsetDateTime,
            level: String
        ): CurrentProgressModel = CurrentProgressModel(
            idUnit, targetId, title, description, checkInTime, level
        )

    }

    class Standard : ProgressMapper<StandardProgressModel> {
        override fun map(
            idUnit: Long,
            targetId: Long,
            title: String,
            description: String,
            checkInTime: OffsetDateTime,
            level: String
        ): StandardProgressModel = StandardProgressModel(
            idUnit, targetId, title, description, checkInTime, level
        )

    }
}