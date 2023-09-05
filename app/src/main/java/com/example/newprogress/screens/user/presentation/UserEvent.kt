package com.example.newprogress.screens.user.presentation

import com.example.newprogress.core.domain.models.CurrentProgressModel
import com.example.newprogress.core.domain.models.NoteOfObjectModel
import com.example.newprogress.core.domain.models.NoteOfProgressTargetModel
import com.example.newprogress.core.domain.models.ProgressTargetModel
import com.example.newprogress.core.domain.models.StandardProgressModel

sealed class UserEvent {
    data class AddTarget(val target: ProgressTargetModel): UserEvent()
    data class DeleteTarget(val target: ProgressTargetModel): UserEvent()
    data class UpdateTarget(val target: ProgressTargetModel): UserEvent()
    data class AddNoteOfUser(val note: NoteOfObjectModel): UserEvent()
    data class DeleteNoteOfUser(val note: NoteOfObjectModel): UserEvent()
    data class UpdateNoteOfUser(val note: NoteOfObjectModel): UserEvent()
    data class AddNoteOfTarget(val note: NoteOfProgressTargetModel): UserEvent()
    data class DeleteNoteOfTarget(val note: NoteOfProgressTargetModel): UserEvent()
    data class UpdateNoteOfTarget(val note: NoteOfProgressTargetModel): UserEvent()
    data class AddCurrentProgress(val progress: CurrentProgressModel): UserEvent()
    data class DeleteCurrentProgress(val progress: CurrentProgressModel): UserEvent()
    data class UpdateCurrentProgress(val progress: CurrentProgressModel): UserEvent()
    data class AddStandardProgress(val progress: StandardProgressModel): UserEvent()
    data class DeleteStandardProgress(val progress: StandardProgressModel): UserEvent()
    data class UpdateStandardProgress(val progressModel: StandardProgressModel): UserEvent()

}

