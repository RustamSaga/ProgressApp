package com.example.newprogress.screens.user.domain.use_cases

import com.example.newprogress.core.domain.models.ObjectOfObservationModel

interface UpdateUserUseCase {
    suspend fun updateUser(user: ObjectOfObservationModel): Boolean
}