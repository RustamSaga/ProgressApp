package com.rustamsaga.progress.screens.user.user_repository.elements

import com.rustamsaga.progress.core.data.local.entity.ProgressTargetEntity
import java.time.OffsetDateTime

object TargetsEntity {
    val progressTargetEntity = ProgressTargetEntity(
        id = 1L,
        name = "English",
        description = "for test",
        userId = 1,
        parentTarget = 0,
        isParent = true,
        checkInTime = OffsetDateTime.now(),
        isActive = true
    )
}