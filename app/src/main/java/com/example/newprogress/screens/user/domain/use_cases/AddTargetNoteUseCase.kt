package com.rustamsaga.progress.screens.user.domain.use_cases

import com.example.newprogress.core.domain.models.NoteOfProgressTargetModel

interface AddTargetNoteUseCase {
    suspend fun addNoteOfTarget(note: NoteOfProgressTargetModel): Boolean
}