package com.example.newprogress.screens.user.domain.use_cases

import com.example.newprogress.core.domain.models.ProgressTargetModel

interface DeleteTargetUseCase {
    suspend fun deleteTarget(target: ProgressTargetModel): Boolean
}