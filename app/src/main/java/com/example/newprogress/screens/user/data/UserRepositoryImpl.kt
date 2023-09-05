package com.example.newprogress.screens.user.data

import com.example.newprogress.core.domain.models.CurrentProgressModel
import com.example.newprogress.core.domain.models.NoteOfObjectModel
import com.example.newprogress.core.domain.models.NoteOfProgressTargetModel
import com.example.newprogress.core.domain.models.ObjectOfObservationModel
import com.example.newprogress.core.domain.models.ProgressTargetModel
import com.example.newprogress.core.domain.models.StandardProgressModel
import com.example.newprogress.screens.user.data.utils.get
import com.example.newprogress.screens.user.domain.UserCollector
import com.example.newprogress.screens.user.domain.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userCollector: UserCollector
) : UserRepository {
    override suspend fun createUser(obj: ObjectOfObservationModel): Boolean {
        return userCollector.updateUser(obj)
    }

    override suspend fun getUser(): Result<ObjectOfObservationModel> {
        return try {
            val result = userCollector.getUser().get()

            Result.success(result)
        }catch (e: Throwable){
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

    override suspend fun addNoteOfObject(note: NoteOfObjectModel): Boolean {
        return userCollector.createNoteUser(note)
    }

    override suspend fun addNoteOfTarget(note: NoteOfProgressTargetModel): Boolean {
        return userCollector.createNoteTarget(note)
    }

    override suspend fun deleteNoteOfObject(note: NoteOfObjectModel): Boolean {
        return userCollector.deleteNoteUser(note)
    }

    override suspend fun deleteNoteOfTarget(note: NoteOfProgressTargetModel): Boolean {
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