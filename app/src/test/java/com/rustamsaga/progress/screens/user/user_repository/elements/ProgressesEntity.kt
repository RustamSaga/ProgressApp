package com.rustamsaga.progress.screens.user.user_repository.elements

import com.rustamsaga.progress.core.data.local.entity.CurrentProgressEntity
import com.rustamsaga.progress.core.data.local.entity.StandardProgressEntity
import java.time.OffsetDateTime

object ProgressesEntity {

    fun getListCurrentProgresses(targetId: Long): List<CurrentProgressEntity> {
        val list = mutableListOf<CurrentProgressEntity>()
        for (i in 1..5) {
            list.add(
                CurrentProgressEntity(
                    idUnit = i.toLong(),
                    targetId = targetId,
                    title = "current progress",
                    description = "for test",
                    checkInTime = OffsetDateTime.now().plusYears(i.toLong()),
                    level = i.toString()
                )
            )
        }
        return list
    }

    fun getListStandardProgresses(targetId: Long): List<StandardProgressEntity> {
        val list = mutableListOf<StandardProgressEntity>()
        for (i in 1..5) {
            list.add(
                StandardProgressEntity(
                    idUnit = i.toLong(),
                    targetId = targetId,
                    title = "standard progress",
                    description = "for test",
                    checkInTime = OffsetDateTime.now().plusDays(i.toLong()),
                    level = i.toString()
                )
            )
        }
        return list
    }
}