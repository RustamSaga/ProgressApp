package com.example.newprogress.screens.user.data.utils

import com.example.newprogress.core.data.local.ObjectOfObservationData
import com.example.newprogress.core.data.local.ProgressTargetData
import com.example.newprogress.core.data.local.entity.ProgressTargetEntity
import com.example.newprogress.core.data.mapper.notes.NoteOfTargetToDomain
import com.example.newprogress.core.data.mapper.progresses.CurrentToModel
import com.example.newprogress.core.data.mapper.progresses.ProgressTargetToCache
import com.example.newprogress.core.data.mapper.progresses.StandardToModel
import com.example.newprogress.core.domain.models.ObjectOfObservationModel
import com.example.newprogress.core.domain.models.ProgressTargetModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

suspend fun Flow<ObjectOfObservationData?>.modelMap(
    block: suspend (ObjectOfObservationData?) -> Unit
): Flow<ObjectOfObservationModel> = flow {
    collect { observation ->
        block(observation)
    }
}

suspend fun Flow<List<ProgressTargetData>>.mapToDomain(
    block: suspend (List<ProgressTargetData>) -> List<ProgressTargetModel>
): List<ProgressTargetModel> {
    var targetList: List<ProgressTargetData> = emptyList()
    collect {
        targetList = it
    }
    return block(targetList)
}

suspend fun Flow<ObjectOfObservationModel>.get(): ObjectOfObservationModel {
    var obj: ObjectOfObservationModel? = null
    collect {
        obj = it
    }

    return if (obj != null) obj!! else throw NullPointerException()

}


suspend fun Flow<List<ProgressTargetData>>.mapToEntity(): List<ProgressTargetEntity> {
    var targetList: List<ProgressTargetEntity> = emptyList()
    collect { listTargetData ->
        targetList = listTargetData.map {
            it.map(
                ProgressTargetToCache(), CurrentToModel(), StandardToModel(), NoteOfTargetToDomain()
            )
        }
    }
    return targetList
}
