package com.rustamsaga.progress.core.data.mapper.progresses

import com.rustamsaga.progress.core.domain.mapper.TargetMapper
import com.rustamsaga.progress.core.domain.models.CurrentProgressModel
import com.rustamsaga.progress.core.domain.models.NoteOfProgressTargetModel
import com.rustamsaga.progress.core.domain.models.ProgressTargetModel
import com.rustamsaga.progress.core.domain.models.StandardProgressModel
import java.time.OffsetDateTime

class ProgressTargetToDomain : TargetMapper<ProgressTargetModel> {
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
    ) = ProgressTargetModel(
        id = id,
        name = name,
        description = description,
        userId = userId,
        parentTarget = parentTarget,
        isParent = isParent,
        checkInTime = checkInTime,
        isActive = isActive,
        targets = targets,
        notes = notes,
        currentProgress = currentProgress,
        standardProgress = standardProgress
    )
}