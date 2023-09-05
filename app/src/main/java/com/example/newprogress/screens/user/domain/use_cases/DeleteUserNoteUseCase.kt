package com.example.newprogress.screens.user.domain.use_cases

import com.example.newprogress.core.domain.models.NoteOfObjectModel

interface DeleteUserNoteUseCase {
    suspend fun deleteNoteOfUser(note: NoteOfObjectModel): Boolean
}