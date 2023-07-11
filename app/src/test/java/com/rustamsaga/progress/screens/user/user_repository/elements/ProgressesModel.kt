package com.rustamsaga.progress.screens.user.user_repository.elements

import com.rustamsaga.progress.core.domain.models.CurrentProgressModel
import com.rustamsaga.progress.core.domain.models.StandardProgressModel
import java.time.OffsetDateTime

object ProgressesModel {

    fun getListCurrentProgresses(targetId: Long, title: String): List<CurrentProgressModel> {
        val list = mutableListOf<CurrentProgressModel>()
        for (i in 1..5) {
            val idUnit = OffsetDateTime.now().plusNanos(i.toLong()).hashCode().toLong()
            list.add(
                CurrentProgressModel(
                    idUnit = idUnit,
                    targetId = targetId,
                    title = "Current progress of $title",
                    description = "for test",
                    checkInTime = OffsetDateTime.now().plusYears(i.toLong()),
                    level = i.toString()
                )
            )
        }
        return list
    }

    fun getListStandardProgresses(targetId: Long, title: String): List<StandardProgressModel> {
        val list = mutableListOf<StandardProgressModel>()
        for (i in 1..5) {
            val idUnit = OffsetDateTime.now().plusNanos(i.toLong()).hashCode().toLong()
            list.add(
                StandardProgressModel(
                    idUnit = idUnit,
                    targetId = targetId,
                    title = "Current progress of $title",
                    description = "for test",
                    checkInTime = OffsetDateTime.now().plusDays(i.toLong()),
                    level = i.toString()
                )
            )
        }
        return list
    }
}