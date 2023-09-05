package com.example.newprogress.core.domain.mapper

import com.example.newprogress.core.domain.models.CurrentProgressModel
import com.example.newprogress.core.domain.models.NoteOfProgressTargetModel
import com.example.newprogress.core.domain.models.ProgressTargetModel
import com.example.newprogress.core.domain.models.StandardProgressModel
import java.time.OffsetDateTime

interface TargetMapper<T> {
    fun mapProgressTarget(
        id: Long,
        name: String,
        description: String,
        userId: Long,
        parentTarget: Long,
        isParent: Boolean,
        checkInTime: OffsetDateTime,
        isActive: Boolean,
        targets: List<ProgressTargetModel>,
        notes: List<NoteOfProgressTargetModel>,
        currentProgress: List<CurrentProgressModel>,
        standardProgress: List<StandardProgressModel>
    ): T
}

