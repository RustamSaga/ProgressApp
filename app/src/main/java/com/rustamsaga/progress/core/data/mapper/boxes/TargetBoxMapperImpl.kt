package com.rustamsaga.progress.core.data.mapper.boxes

import com.rustamsaga.progress.core.data.local.entity.ProgressTargetData
import com.rustamsaga.progress.core.data.local.entity.ProgressTargetEntity
import com.rustamsaga.progress.core.domain.mapper.TargetBoxMapper
import com.rustamsaga.progress.core.domain.models.ProgressTargetModel


class TargetBoxMapperImpl(
    private val targetsEntity: List<ProgressTargetEntity> = emptyList(),
    private val targetsModel: List<ProgressTargetModel> = emptyList(),
    private val targetsData: List<ProgressTargetData> = emptyList()
) : TargetBoxMapper {
    override suspend fun getTargetEntity() = targetsEntity

    override suspend fun getTargetModel() = targetsModel

    override suspend fun getTargetData() = targetsData

}

