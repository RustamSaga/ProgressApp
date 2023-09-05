package com.example.newprogress.core.domain.mapper

import com.example.newprogress.core.data.local.ProgressTargetData
import com.example.newprogress.core.data.local.entity.ProgressTargetEntity
import com.example.newprogress.core.domain.models.ProgressTargetModel

interface TargetBoxMapper {
    suspend fun getTargetEntity(): List<ProgressTargetEntity>
    suspend fun getTargetModel(): List<ProgressTargetModel>
    suspend fun getTargetData(): List<ProgressTargetData>
}