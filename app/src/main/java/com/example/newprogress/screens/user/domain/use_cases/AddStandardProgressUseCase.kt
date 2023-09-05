package com.rustamsaga.progress.screens.user.domain.use_cases

import com.example.newprogress.core.domain.models.StandardProgressModel

interface AddStandardProgressUseCase {
    suspend fun addStandardProgress(progress: StandardProgressModel): Boolean
}