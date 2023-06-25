package com.rustamsaga.progress.core.data.mapper

import com.rustamsaga.progress.core.data.local.dao.*
import com.rustamsaga.progress.core.data.local.entity.ProgressTargetEntity
import com.rustamsaga.progress.core.domain.mapper.*
import com.rustamsaga.progress.core.domain.models.CurrentProgressModel
import com.rustamsaga.progress.core.domain.models.NoteOfProgressTargetModel
import com.rustamsaga.progress.core.domain.models.ProgressTargetModel
import com.rustamsaga.progress.core.domain.models.StandardProgressModel

class TargetPacker(
    private val progressTargetDao: ProgressTargetDao,
    private val targetMapper: TargetMapper<ProgressTargetModel>,
    private val currentProgressMapper: ProgressMapper<CurrentProgressModel>,
    private val standardProgressMapper: ProgressMapper<StandardProgressModel>,
    private val noteMapper: NoteMapper<NoteOfProgressTargetModel>
) : Spreader<ProgressTargetModel, ProgressTargetEntity> {
    override suspend fun get(item: List<ProgressTargetModel>): List<ProgressTargetModel> {
        if (item.isEmpty()) return emptyList()

        val result = item.map { target ->
            target.copy(
                targets = if (target.isParent) {
                    progressTargetDao.getChildrenTarget(target.id).map {
                        it.map(
                            targetMapper = targetMapper,
                            currentProgressMapper = currentProgressMapper,
                            standardProgressMapper = standardProgressMapper,
                            noteMapper = noteMapper
                        )
                    }.map {
                        target.copy(
                            targets = if (it.isParent) {
                                get(it.targets)
                            } else emptyList()
                        )
                    }
                } else emptyList()
            )
        }
        return result
    }

    override suspend fun decompose(item: ProgressTargetEntity) {

    }


}