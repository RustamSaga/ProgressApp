package com.example.newprogress.screens.user.domain.use_cases

import com.example.newprogress.core.domain.models.ObjectOfObservationModel

interface DeleteUserUseCase {
    suspend fun deleteUser(user: ObjectOfObservationModel): Boolean
}