package com.rustamsaga.progress.screens.user.domain.use_cases

import com.example.newprogress.core.domain.models.ProgressTargetModel

interface AddTargetUseCase {
    suspend fun addTarget(target: ProgressTargetModel): Boolean
}