package com.rustamsaga.progress.core.data.mapper

import com.rustamsaga.progress.core.data.local.dao.*
import com.rustamsaga.progress.core.data.local.entity.CurrentProgressEntity
import com.rustamsaga.progress.core.data.local.entity.NoteOfProgressTargetEntity
import com.rustamsaga.progress.core.data.local.entity.ProgressTargetEntity
import com.rustamsaga.progress.core.data.local.entity.StandardProgressEntity
import com.rustamsaga.progress.core.domain.mapper.*
import com.rustamsaga.progress.core.domain.models.CurrentProgressModel
import com.rustamsaga.progress.core.domain.models.NoteOfProgressTargetModel
import com.rustamsaga.progress.core.domain.models.ProgressTargetModel
import com.rustamsaga.progress.core.domain.models.StandardProgressModel

class TargetPackerImpl(
    private val progressTargetDao: ProgressTargetDao,
    private val currentProgressDao: CurrentProgressDao,
    private val standardProgressDao: StandardProgressDao,
    private val noteOfTargetDao: NoteOfTargetDao,
    private val targetToDomain: TargetMapper<ProgressTargetModel>,
    private val targetToCache: TargetMapper<ProgressTargetEntity>,
    private val currentToDomain: ProgressMapper<CurrentProgressModel>,
    private val currentToCache: ProgressMapper<CurrentProgressEntity>,
    private val standardToDomain: ProgressMapper<StandardProgressModel>,
    private val standardToCache: ProgressMapper<StandardProgressEntity>,
    private val noteToDomain: NoteMapper<NoteOfProgressTargetModel>,
    private val noteToCache: NoteMapper<NoteOfProgressTargetEntity>
) : Spreader<ProgressTargetModel, ProgressTargetModel> {
    override suspend fun get(item: List<ProgressTargetModel>): List<ProgressTargetModel> {

        val result = item.map { parent ->
            parent.copy(
                targets = if (parent.isParent) {
                    progressTargetDao.getChildrenTarget(parent.id).map {
                        it.map(
                            targetMapper = targetToDomain,
                            currentProgressMapper = currentToDomain,
                            standardProgressMapper = standardToDomain,
                            noteMapper = noteToDomain
                        )
                    }.map { child ->
                        child.copy(
                            targets = if (child.isParent) {
                                get(progressTargetDao.getChildrenTarget(child.id).map {
                                    it.map(
                                        targetMapper = targetToDomain,
                                        currentProgressMapper = currentToDomain,
                                        standardProgressMapper = standardToDomain,
                                        noteMapper = noteToDomain
                                    )
                                })
                            } else emptyList()
                        )
                    }
                } else emptyList()
            )
        }
        return result
    }

    override suspend fun decompose(item: ProgressTargetModel) {
        progressTargetDao.insertTarget(
            item.map(targetToCache)
        )
        currentProgressDao.insertCurrentProgresses(
            item.currentProgress.map { it.map(currentToCache) })

        standardProgressDao.insertStandardProgresses(
            item.standardProgress.map { it.map(standardToCache) })

        noteOfTargetDao.insertNotes(
            item.notes.map { it.map(noteToCache) }
        )

        if (item.targets.isNotEmpty()) {
            item.targets.forEach { target ->
                decompose(target)
            }
        }
    }


}