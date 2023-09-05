package com.example.newprogress.core.data.mapper.boxes

import com.example.newprogress.core.data.local.ProgressTargetData
import com.example.newprogress.core.data.local.entity.ProgressTargetEntity
import com.example.newprogress.core.domain.mapper.TargetBoxMapper
import com.example.newprogress.core.domain.models.ProgressTargetModel


class TargetBoxMapperImpl(
    private val targetsEntity: List<ProgressTargetEntity> = emptyList(),
    private val targetsModel: List<ProgressTargetModel> = emptyList(),
    private val targetsData: List<ProgressTargetData> = emptyList()
) : TargetBoxMapper {
    override suspend fun getTargetEntity() = targetsEntity

    override suspend fun getTargetModel() = targetsModel

    override suspend fun getTargetData() = targetsData

}

