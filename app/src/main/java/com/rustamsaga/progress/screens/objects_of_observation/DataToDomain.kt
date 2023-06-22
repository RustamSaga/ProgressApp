package com.rustamsaga.progress.screens.objects_of_observation

import com.rustamsaga.progress.core.data.local.Headings
import com.rustamsaga.progress.core.data.local.dao.*
import com.rustamsaga.progress.core.data.local.entity.NoteOfObjectEntity
import com.rustamsaga.progress.core.data.local.entity.NoteOfProgressTargetEntity
import com.rustamsaga.progress.core.data.local.entity.ProgressTargetData
import com.rustamsaga.progress.core.data.mapper.NoteMapper
import com.rustamsaga.progress.core.data.mapper.ObjectOfObservationMapper
import com.rustamsaga.progress.core.data.mapper.TargetMapper
import com.rustamsaga.progress.core.domain.models.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.OffsetDateTime

class DataToDomain(
    private val progressTargetDao: ProgressTargetDao,
    private val currentProgress: CurrentProgressDao,
    private val standardProgressDao: StandardProgressDao,
    private val noteOfObjectDao: NoteOfObjectDao,
    private val noteOfTargetDao: NoteOfTargetDao,
    private val noteOfObjectMapper: NoteMapper.NoteOfObjectToDomain,
    private val noteOfTargetMapper: NoteMapper.NoteOfTargetToDomain
) :
    ObjectOfObservationMapper<ObjectOfObservationModel>,
    TargetMapper<ProgressTargetModel> {
    override suspend fun <P, N> mapObject(
        id: Long,
        firstName: String,
        lastName: String,
        description: String,
        observed: Boolean,
        checkInTime: OffsetDateTime,
        isActive: Boolean,
        targets: List<P>,
        notes: List<N>
    ): ObjectOfObservationModel {

//        val targets = collect(progressTargetDao.getActiveTargetsByUser(id, true).map {
//            it.map(this)
//        })
        TODO("Not yet implemented")

    }

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
    ): ProgressTargetModel {
        return ProgressTargetModel(
            id,
            name,
            description,
            userId,
            parentTarget,
            isGroup,
            checkInTime,
            isActive,
            targets,
            notes,
            currentProgress,
            standardProgress
        )
    }

    private suspend fun collect(item: List<ProgressTargetModel>): List<ProgressTargetModel> {
        if (item.isEmpty()) return emptyList()

        val parent = mutableListOf<ProgressTargetModel>()
        val children = mutableListOf<ProgressTargetModel>()

        item.forEach {
            if (it.parentTarget == Headings.NO_PARENTS) {
                parent.add(it)
            } else {
                children.add(it)
            }
        }

//        return parent.map { target ->
//            target.copy(
//                targets = getChildren(children, target.id),
//                notes = noteOfTargetDao.getNoteByTargetId(target.id).map { note ->
//                    note.map(noteOfTargetMapper)
//                },
//                currentProgress = ,
//                standardProgress = ,
//            )
//        }
        TODO("Not yet implemented")


    }

    private suspend fun getChildren(
        childrenList: MutableList<ProgressTargetModel>,
        parentId: Long
    ): List<ProgressTargetModel> {
        if (childrenList.isEmpty()) return emptyList()

        val result = mutableListOf<ProgressTargetModel>()
        result.addAll(childrenList.filter { it.parentTarget == parentId })
        childrenList.removeAll(result)
//        return result.map { target ->
//            target.copy(
//                targets = getChildren(childrenList, target.id),
//                notes = noteOfTargetDao.getNoteByTargetId(target.id).map { note ->
//                    note.map(noteOfTargetMapper)
//                },
//                currentProgress = ,
//                standardProgress = ,
//            )
//        }
        TODO("Not yet implemented")

    }



}
