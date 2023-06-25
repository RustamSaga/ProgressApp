package com.rustamsaga.progress.screens.user.domain

import com.rustamsaga.progress.core.domain.models.*


interface UserRepository :
    UserScreenTargetRepository,
    UserScreenNotesRepository,
    UserScreenCurrentProgressRepository,
    UserScreenStandardProgressRepository
{

    suspend fun createUser(obj: ObjectOfObservationModel): Boolean
    suspend fun getUser(): Result<ObjectOfObservationModel>
    suspend fun update(obj: ObjectOfObservationModel): Boolean
    suspend fun deleteUser(obj: ObjectOfObservationModel): Boolean

}

interface UserScreenNotesRepository {
    suspend fun addNoteObject(note: NoteOfObjectModel): Boolean
    suspend fun addNoteTarget(note: NoteOfProgressTargetModel): Boolean
    suspend fun deleteNoteObject(note: NoteOfObjectModel): Boolean
    suspend fun deleteNoteTarget(note: NoteOfProgressTargetModel): Boolean
    suspend fun updateNoteObject(note: NoteOfObjectModel): Boolean
    suspend fun updateNoteTarget(note: NoteOfProgressTargetModel): Boolean
}

interface UserScreenTargetRepository {
    suspend fun addTarget(target: ProgressTargetModel): Boolean
    suspend fun deleteTarget(target: ProgressTargetModel): Boolean
    suspend fun updateTarget(target: ProgressTargetModel): Boolean
}

interface UserScreenCurrentProgressRepository {
    suspend fun addCurrentProgress(progress: CurrentProgressModel): Boolean
    suspend fun deleteCurrentProgress(progress: CurrentProgressModel): Boolean
    suspend fun updateCurrentProgress(progressModel: CurrentProgressModel): Boolean
}

interface UserScreenStandardProgressRepository {
    suspend fun addStandardProgress(progress: StandardProgressModel): Boolean
    suspend fun deleteStandardProgress(progress: StandardProgressModel): Boolean
    suspend fun updateStandardProgress(progressModel: StandardProgressModel): Boolean
}