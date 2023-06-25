package com.rustamsaga.progress.screens.objects_of_observation.domain

import com.rustamsaga.progress.core.data.local.dao.NoteOfTargetDao
import com.rustamsaga.progress.core.domain.models.*
import kotlinx.coroutines.flow.Flow

interface ObjectScreenRepository: ObjectScreenNoteRepository {

    suspend fun createObject(obj: ObjectOfObservationModel): Boolean
    suspend fun getObjectById(id: Long): Result<ObjectOfObservationModel>
    suspend fun update(obj: ObjectOfObservationModel): Boolean
    fun getAllObjects(): Flow<List<ObjectOfObservationModel>>
    suspend fun deleteObject(obj: ObjectOfObservationModel): Boolean

}

interface ObjectScreenNoteRepository{
    suspend fun addNoteObject(note: NoteOfObjectModel): Boolean
    suspend fun addNoteTarget(note: NoteOfProgressTargetModel): Boolean
    suspend fun deleteNoteObject(note: NoteOfObjectModel): Boolean
    suspend fun deleteNoteTarget(note: NoteOfTargetDao): Boolean
    suspend fun getNoteObjectByDate(checkInTime: String): NoteOfObjectModel
    suspend fun getNoteTargetByDate(checkInTime: String): NoteOfProgressTargetModel
    suspend fun updateNoteObject(note: NoteOfObjectModel): Boolean
    suspend fun updateNoteTarget(note: NoteOfProgressTargetModel): Boolean
}

interface ObjectScreenTargetRepository {
    suspend fun addTarget(target: ProgressTargetModel): Boolean
    suspend fun getTarget(checkInTime: String): ProgressTargetModel
    suspend fun deleteTarget(target: ProgressTargetModel): Boolean
    suspend fun updateTarget(target: ProgressTargetModel): Boolean
}

interface ObjectScreenCurrentProgressRepository {
    suspend fun addCurrentProgress(progress: CurrentProgressModel): Boolean
    suspend fun deleteCurrentProgress(progress: CurrentProgressModel): Boolean
    suspend fun getCurrentProgress(checkInTime: String): CurrentProgressModel
    suspend fun updateCurrentProgress(progressModel: CurrentProgressModel): Boolean
}

interface ObjectScreenStandardProgressRepository {
    suspend fun addCurrentProgress(progress: CurrentProgressModel): Boolean
    suspend fun deleteCurrentProgress(progress: CurrentProgressModel): Boolean
    suspend fun getCurrentProgress(checkInTime: String): CurrentProgressModel
    suspend fun updateCurrentProgress(progressModel: CurrentProgressModel): Boolean
}