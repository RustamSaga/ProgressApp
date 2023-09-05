package com.example.newprogress.core.domain.mapper

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
}