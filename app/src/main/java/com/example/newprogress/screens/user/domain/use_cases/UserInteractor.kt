package com.example.newprogress.screens.user.domain.use_cases

import com.example.newprogress.core.domain.models.CurrentProgressModel
import com.example.newprogress.core.domain.models.NoteOfObjectModel
import com.example.newprogress.core.domain.models.NoteOfProgressTargetModel
import com.example.newprogress.core.domain.models.ObjectOfObservationModel
import com.example.newprogress.core.domain.models.ProgressTargetModel
import com.example.newprogress.core.domain.models.StandardProgressModel
import com.example.newprogress.screens.user.domain.UserRepository
import com.rustamsaga.progress.screens.user.domain.use_cases.AddStandardProgressUseCase
import com.rustamsaga.progress.screens.user.domain.use_cases.AddTargetNoteUseCase
import com.rustamsaga.progress.screens.user.domain.use_cases.AddTargetUseCase
import com.rustamsaga.progress.screens.user.domain.use_cases.AddUserNoteUseCase
import com.rustamsaga.progress.screens.user.domain.use_cases.DeleteCurrentProgressUseCase
import com.rustamsaga.progress.screens.user.domain.use_cases.DeleteStandardProgressUseCase
import javax.inject.Inject

class UserInteractor @Inject constructor(private val repository: UserRepository) :
    AddCurrentProgressUseCase,
    AddStandardProgressUseCase,
    AddTargetUseCase,
    AddTargetNoteUseCase,
    AddUserNoteUseCase,
    DeleteCurrentProgressUseCase,
    DeleteStandardProgressUseCase,
    DeleteTargetNoteUseCase,
    DeleteTargetUseCase,
    DeleteUserUseCase,
    DeleteUserNoteUseCase,
    GetUserUseCase,
    UpdateCurrentProgressUseCase,
    UpdateStandardProgressUseCase,
    UpdateTargetUseCase,
    UpdateUserUseCase {

    override suspend fun addCurrentProgress(progress: CurrentProgressModel): Boolean =
        repository.addCurrentProgress(progress)

    override suspend fun addStandardProgress(progress: StandardProgressModel): Boolean =
        repository.addStandardProgress(progress)

    override suspend fun addNoteOfTarget(note: NoteOfProgressTargetModel): Boolean =
        repository.addNoteOfTarget(note)

    override suspend fun addTarget(target: ProgressTargetModel): Boolean =
        repository.addTarget(target)

    override suspend fun addNoteOfUser(note: NoteOfObjectModel): Boolean =
        repository.addNoteOfObject(note)

    override suspend fun deleteCurrentProgress(progress: CurrentProgressModel): Boolean =
        repository.deleteCurrentProgress(progress)

    override suspend fun deleteStandardProgress(progress: StandardProgressModel): Boolean =
        repository.deleteStandardProgress(progress)

    override suspend fun deleteNoteOfTarget(note: NoteOfProgressTargetModel): Boolean =
        repository.deleteNoteOfTarget(note)

    override suspend fun deleteTarget(target: ProgressTargetModel): Boolean =
        repository.deleteTarget(target)

    override suspend fun deleteNoteOfUser(note: NoteOfObjectModel): Boolean =
        repository.deleteNoteOfObject(note)

    override suspend fun deleteUser(user: ObjectOfObservationModel): Boolean =
        repository.deleteUser(user)

    override suspend fun getUser(): Result<ObjectOfObservationModel> =
        repository.getUser()

    override suspend fun updateCurrentProgress(progress: CurrentProgressModel): Boolean =
        repository.updateCurrentProgress(progress)

    override suspend fun updateStandardProgress(progress: StandardProgressModel): Boolean =
        repository.updateStandardProgress(progress)

    override suspend fun updateTarget(target: ProgressTargetModel): Boolean =
        repository.updateTarget(target)

    override suspend fun updateUser(user: ObjectOfObservationModel): Boolean =
        repository.update(user)
}