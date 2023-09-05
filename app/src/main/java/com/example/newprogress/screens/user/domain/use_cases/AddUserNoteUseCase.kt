package com.rustamsaga.progress.screens.user.domain.use_cases

import com.example.newprogress.core.domain.models.NoteOfObjectModel

interface AddUserNoteUseCase {
    suspend fun addNoteOfUser(note: NoteOfObjectModel): Boolean
}