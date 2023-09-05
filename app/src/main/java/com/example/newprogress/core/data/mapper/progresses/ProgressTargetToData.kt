package com.example.newprogress.core.data.mapper.progresses

import com.example.newprogress.core.data.local.ProgressTargetData
import com.example.newprogress.core.data.mapper.notes.NoteOfTargetToCache
import com.example.newprogress.core.domain.mapper.TargetMapper
import com.example.newprogress.core.domain.models.CurrentProgressModel
import com.example.newprogress.core.domain.models.NoteOfProgressTargetModel
import com.example.newprogress.core.domain.models.ProgressTargetModel
import com.example.newprogress.core.domain.models.StandardProgressModel
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