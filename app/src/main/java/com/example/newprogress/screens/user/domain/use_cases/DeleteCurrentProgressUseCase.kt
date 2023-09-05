package com.rustamsaga.progress.screens.user.domain.use_cases

import com.example.newprogress.core.domain.models.CurrentProgressModel

interface DeleteCurrentProgressUseCase {
    suspend fun deleteCurrentProgress(progress: CurrentProgressModel): Boolean
}