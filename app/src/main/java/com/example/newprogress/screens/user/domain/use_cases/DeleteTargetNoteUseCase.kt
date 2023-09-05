package com.example.newprogress.screens.user.domain.use_cases

import com.example.newprogress.core.domain.models.NoteOfProgressTargetModel

interface DeleteTargetNoteUseCase {
    suspend fun deleteNoteOfTarget(note: NoteOfProgressTargetModel): Boolean
}