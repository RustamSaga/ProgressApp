package com.rustamsaga.progress.screens.user.domain.use_cases

import com.example.newprogress.core.domain.models.StandardProgressModel

interface DeleteStandardProgressUseCase {
    suspend fun deleteStandardProgress(progress: StandardProgressModel): Boolean
}