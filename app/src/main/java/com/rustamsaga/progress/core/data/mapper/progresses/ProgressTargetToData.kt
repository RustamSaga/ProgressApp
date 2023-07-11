package com.rustamsaga.progress.core.data.mapper.progresses

import com.rustamsaga.progress.core.data.local.ProgressTargetData
import com.rustamsaga.progress.core.data.mapper.notes.NoteOfTargetToCache
import com.rustamsaga.progress.core.domain.mapper.TargetMapper
import com.rustamsaga.progress.core.domain.models.CurrentProgressModel
import com.rustamsaga.progress.core.domain.models.NoteOfProgressTargetModel
import com.rustamsaga.progress.core.domain.models.ProgressTargetModel
import com.rustamsaga.progress.core.domain.models.StandardProgressModel
import java.time.OffsetDateTime

class ProgressTargetToData : TargetMapper<ProgressTargetData> {
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
    ): ProgressTargetData {
        return ProgressTargetData(
            id = id,
            name = name,
            description = description,
            userId = userId,
            parentTarget = parentTarget,
            isParent = isParent,
            checkInTime = checkInTime,
            isActive = isActive,
            notes = notes.map { it.map(NoteOfTargetToCache()) },
            currentProgress = currentProgress.map { it.map(CurrentToEntity()) },
            standardProgress = standardProgress.map { it.map(StandardToEntity()) }
        )
    }
}