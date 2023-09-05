package com.example.newprogress.screens.user.domain.use_cases

import com.example.newprogress.core.domain.models.StandardProgressModel

interface UpdateStandardProgressUseCase{
    suspend fun updateStandardProgress(progress: StandardProgressModel): Boolean
}