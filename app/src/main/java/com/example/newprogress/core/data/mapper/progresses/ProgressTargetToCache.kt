package com.example.newprogress.core.data.mapper.progresses

import com.example.newprogress.core.data.local.entity.ProgressTargetEntity
import com.example.newprogress.core.domain.mapper.TargetMapper
import com.example.newprogress.core.domain.models.CurrentProgressModel
import com.example.newprogress.core.domain.models.NoteOfProgressTargetModel
import com.example.newprogress.core.domain.models.ProgressTargetModel
import com.example.newprogress.core.domain.models.StandardProgressModel
import java.time.OffsetDateTime
import javax.inject.Inject

class ProgressTargetToCache @Inject constructor() : TargetMapper<ProgressTargetEntity> {
    override fun mapProgressTarget(
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
    ): ProgressTargetEntity {
        return ProgressTargetEntity(
            id = id,
            name = name,
            description = description,
            userId = userId,
            parentTarget = parentTarget,
            isParent = isParent,
            checkInTime = checkInTime,
            isActive = isActive,
        )
    }
}