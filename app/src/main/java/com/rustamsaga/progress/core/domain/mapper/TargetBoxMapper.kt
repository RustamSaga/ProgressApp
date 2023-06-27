package com.rustamsaga.progress.core.domain.mapper

import com.rustamsaga.progress.core.data.local.ProgressTargetData
import com.rustamsaga.progress.core.data.local.entity.ProgressTargetEntity
import com.rustamsaga.progress.core.domain.models.ProgressTargetModel

interface TargetBoxMapper {
    suspend fun getTargetEntity(): List<ProgressTargetEntity>
    suspend fun getTargetModel(): List<ProgressTargetModel>
    suspend fun getTargetData(): List<ProgressTargetData>
}