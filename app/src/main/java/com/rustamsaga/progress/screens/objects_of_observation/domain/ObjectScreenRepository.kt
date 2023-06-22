package com.rustamsaga.progress.screens.objects_of_observation.domain

import com.rustamsaga.progress.core.data.subdirectories.*
import com.rustamsaga.progress.core.domain.models.ObjectOfObservationModel
import kotlinx.coroutines.flow.Flow

interface ObjectScreenRepository {

    suspend fun createObject(obj: ObjectOfObservationModel): Boolean
    suspend fun getObjectById(id: Long): Result<ObjectOfObservationModel>
    suspend fun update(obj: ObjectOfObservationModel): Boolean
    fun getAllObjects(): Flow<List<ObjectOfObservationModel>>
    suspend fun deleteObject(obj: ObjectOfObservationModel): Boolean
}