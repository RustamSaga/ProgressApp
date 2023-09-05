package com.example.newprogress.screens.user.domain.use_cases

import com.example.newprogress.core.domain.models.CurrentProgressModel

interface AddCurrentProgressUseCase {
    suspend fun addCurrentProgress(progress: CurrentProgressModel): Boolean
}