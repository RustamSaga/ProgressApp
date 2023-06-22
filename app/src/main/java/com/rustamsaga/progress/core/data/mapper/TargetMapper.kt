package com.rustamsaga.progress.core.data.mapper

import com.rustamsaga.progress.core.data.local.entity.ProgressTargetEntity
import com.rustamsaga.progress.core.domain.models.CurrentProgressModel
import com.rustamsaga.progress.core.domain.models.NoteOfTargetModel
import com.rustamsaga.progress.core.domain.models.ProgressTargetModel
import com.rustamsaga.progress.core.domain.models.StandardProgressModel
import java.time.OffsetDateTime

interface TargetMapper<T> {
    fun mapProgressTarget(
        id: Long,
        name: String,
        description: String,
        userId: Long,
        parentTarget: Long,
        isGroup: Boolean,
        checkInTime: OffsetDateTime,
        isActive: Boolean,
        targets: List<ProgressTargetModel>,
        notes: List<NoteOfTargetModel>,
        currentProgress: List<CurrentProgressModel>,
        standardProgress: List<StandardProgressModel>
    ): T

        class EntityConverter : TargetMapper<ProgressTargetEntity> {
            override fun mapProgressTarget(
                id: Long,
                name: String,
                description: String,
                userId: Long,
                parentTarget: Long,
                isGroup: Boolean,
                checkInTime: OffsetDateTime,
                isActive: Boolean,
                targets: List<ProgressTargetModel>,
                notes: List<NoteOfTargetModel>,
                currentProgress: List<CurrentProgressModel>,
                standardProgress: List<StandardProgressModel>
            ): ProgressTargetEntity {
                return ProgressTargetEntity(
                    id = id,
                    name = name,
                    description = description,
                    userId = userId,
                    parentTarget = parentTarget,
                    isGroup = isGroup,
                    checkInTime = checkInTime,
                    isActive = isActive,
                )
            }
        }
}