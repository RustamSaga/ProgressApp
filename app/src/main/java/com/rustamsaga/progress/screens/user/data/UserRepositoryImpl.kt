package com.rustamsaga.progress.screens.user.data

import com.rustamsaga.progress.core.domain.models.*
import com.rustamsaga.progress.screens.user.domain.UserCollector
import com.rustamsaga.progress.screens.user.domain.UserRepository

class UserRepositoryImpl(
    private val userCollector: UserCollector
) : UserRepository {
    override suspend fun createUser(obj: ObjectOfObservationModel): Boolean {
        return userCollector.updateUser(obj)
    }

    override suspend fun getUser(): Result<ObjectOfObservationModel> {
        return try {
            val result = userCollector.getUser()
            Result.success(result)
        }catch (e: IllegalStateException){
            Result.failure(e)
        }
    }

    override suspend fun update(obj: ObjectOfObservationModel): Boolean {
        return userCollector.updateUser(obj)
    }

    override suspend fun deleteUser(obj: ObjectOfObservationModel): Boolean {
        return userCollector.deleteUser(obj)
    }

    override suspend fun addTarget(target: ProgressTargetModel): Boolean {
        return userCollector.createTarget(target)
    }

    override suspend fun deleteTarget(target: ProgressTargetModel): Boolean {
        return userCollector.deleteTarget(target)
    }

    override suspend fun updateTarget(target: ProgressTargetModel): Boolean {
        return userCollector.createTarget(target)
    }

    override suspend fun addNoteObject(note: NoteOfObjectModel): Boolean {
        return userCollector.createNoteUser(note)
    }

    override suspend fun addNoteTarget(note: NoteOfProgressTargetModel): Boolean {
        return userCollector.createNoteTarget(note)
    }

    override suspend fun deleteNoteObject(note: NoteOfObjectModel): Boolean {
        return userCollector.deleteNoteUser(note)
    }

    override suspend fun deleteNoteTarget(note: NoteOfProgressTargetModel): Boolean {
        return userCollector.deleteNoteTarget(note)
    }

    override suspend fun updateNoteObject(note: NoteOfObjectModel): Boolean {
        return userCollector.createNoteUser(note)

    }

    override suspend fun updateNoteTarget(note: NoteOfProgressTargetModel): Boolean {
        return userCollector.createNoteTarget(note)
    }

    override suspend fun addCurrentProgress(progress: CurrentProgressModel): Boolean {
        return userCollector.addCurrentProgress(progress)
    }

    override suspend fun deleteCurrentProgress(progress: CurrentProgressModel): Boolean {
        return userCollector.deleteCurrentProgress(progress)
    }

    override suspend fun updateCurrentProgress(progressModel: CurrentProgressModel): Boolean {
        return userCollector.updateCurrentProgress(progressModel)
    }

    override suspend fun addStandardProgress(progress: StandardProgressModel): Boolean {
        return userCollector.addStandardProgress(progress)
    }

    override suspend fun deleteStandardProgress(progress: StandardProgressModel): Boolean {
        return userCollector.deleteStandardProgress(progress)
    }

    override suspend fun updateStandardProgress(progressModel: StandardProgressModel): Boolean {
        return userCollector.updateStandardProgress(progressModel)
    }

}