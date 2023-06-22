package com.rustamsaga.progress.screens.user.domain

import com.rustamsaga.progress.core.domain.models.CurrentProgressModel
import com.rustamsaga.progress.core.domain.models.ObjectOfObservationModel
import com.rustamsaga.progress.core.domain.models.ProgressTargetModel
import com.rustamsaga.progress.core.domain.models.StandardProgressModel
import kotlinx.coroutines.flow.Flow
// TODO нужно ли?
interface UserRepository {
    suspend fun getObjectOfObservation(id: Int): ObjectOfObservationModel
    suspend fun addObjectOfObservation(obj: ObjectOfObservationModel): Boolean
//    fun getAllObjectsOfObservation(): Flow<List<ObjectOfObservationModel>>

    suspend fun addProgressTarget(target: ProgressTargetModel): Boolean
    suspend fun getProgressTarget(id: ProgressTargetModel): ProgressTargetModel
    suspend fun deleteProgressTarget(target: ProgressTargetModel): Boolean

    fun getCurrentProgress(targetId: Int): Flow<CurrentProgressModel>
    fun addCurrentProgress(progress: CurrentProgressModel): Boolean
    fun updateCurrentProgress(progress: CurrentProgressModel): Boolean
    fun deleteCurrentProgress(progress: CurrentProgressModel): Boolean

    fun getStandardProgress(targetId: Int): Flow<StandardProgressModel>
    fun addStandardProgress(progress: StandardProgressModel): Boolean
    fun updateStandardProgress(progress: StandardProgressModel): Boolean
    fun deleteStandardProgress(progress: StandardProgressModel): Boolean
}