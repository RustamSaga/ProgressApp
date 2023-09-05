package com.example.newprogress.core.data.mapper

import com.example.newprogress.core.data.CacheDataSource
import com.example.newprogress.core.data.local.entity.CurrentProgressEntity
import com.example.newprogress.core.data.local.entity.NoteOfProgressTargetEntity
import com.example.newprogress.core.data.local.entity.ProgressTargetEntity
import com.example.newprogress.core.data.local.entity.StandardProgressEntity
import com.example.newprogress.core.domain.mapper.NoteMapper
import com.example.newprogress.core.domain.mapper.ProgressMapper
import com.example.newprogress.core.domain.mapper.Spreader
import com.example.newprogress.core.domain.mapper.TargetMapper
import com.example.newprogress.core.domain.models.CurrentProgressModel
import com.example.newprogress.core.domain.models.NoteOfProgressTargetModel
import com.example.newprogress.core.domain.models.ProgressTargetModel
import com.example.newprogress.core.domain.models.StandardProgressModel
import com.example.newprogress.screens.user.data.utils.mapToDomain

class TargetPacker (
    private val cache: CacheDataSource,
//    private val progressTargetDao: ProgressTargetDao,
//    private val currentProgressDao: CurrentProgressDao,
//    private val standardProgressDao: StandardProgressDao,
//    private val noteOfTargetDao: NoteOfTargetDao,
    private val targetToDomain: TargetMapper<ProgressTargetModel>,
    private val targetToCache: TargetMapper<ProgressTargetEntity>,
    private val currentToDomain: ProgressMapper<CurrentProgressModel>,
    private val currentToCache: ProgressMapper<CurrentProgressEntity>,
    private val standardToDomain: ProgressMapper<StandardProgressModel>,
    private val standardToCache: ProgressMapper<StandardProgressEntity>,
    private val noteToDomain: NoteMapper<NoteOfProgressTargetModel>,
    private val noteToCache: NoteMapper<NoteOfProgressTargetEntity>
) : Spreader<ProgressTargetModel> {
    override suspend fun get(item: List<ProgressTargetModel>): List<ProgressTargetModel> {

        val result = item.map { parent ->
            parent.copy(
                targets = if (parent.isParent) {
                    cache.progressTargetDao().getChildrenTarget(parent.id).mapToDomain { list ->
                        list.map { targetData ->
                            targetData.map(
                                targetMapper = targetToDomain,
                                currentProgressMapper = currentToDomain,
                                standardProgressMapper = standardToDomain,
                                noteMapper = noteToDomain
                            )
                        }
                    }.map { child ->
                        child.copy(
                            targets = if (child.isParent) {
                                get(cache.progressTargetDao().getChildrenTarget(child.id).mapToDomain { listOfChildren ->
                                    listOfChildren.map {
                                        it.map(
                                            targetMapper = targetToDomain,
                                            currentProgressMapper = currentToDomain,
                                            standardProgressMapper = standardToDomain,
                                            noteMapper = noteToDomain
                                        )
                                    }
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
        cache.progressTargetDao().insertTarget(
            item.map(targetToCache)
        )
        cache.currentProgressDao().insertCurrentProgresses(
            item.currentProgress.map { it.map(currentToCache) })

        cache.standardProgressDao().insertStandardProgresses(
            item.standardProgress.map { it.map(standardToCache) })

        cache.noteOfTargetDao().insertNotes(
            item.notes.map { it.map(noteToCache) }
        )

        if (item.targets.isNotEmpty()) {
            item.targets.forEach { target ->
                decompose(target)
            }
        }
    }


}