package com.rustamsaga.progress.core.room.elements

import com.rustamsaga.progress.core.data.local.entity.CurrentProgressEntity
import com.rustamsaga.progress.core.data.local.entity.StandardProgressEntity
import java.time.OffsetDateTime

object Progresses {

    fun getListCurrentProgresses(targetId: Long): List<CurrentProgressEntity> {
        val list = mutableListOf<CurrentProgressEntity>()
        for (i in 1..100) {
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
        for (i in 1..100) {
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