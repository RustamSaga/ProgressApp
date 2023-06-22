package com.rustamsaga.progress.core.domain.models

import com.rustamsaga.progress.core.data.mapper.ProgressMapper
import java.time.OffsetDateTime

data class StandardProgressModel(
    val idUnit: Long,
    val targetId: Long,
    val title: String,
    val description: String,
    val checkInTime: OffsetDateTime,
    val level: String
) : ProgressInterface {

    fun <T> map(mapper: ProgressMapper<T>): T = mapper.map(
        idUnit, targetId, title, description, checkInTime, level
    )
}
