package com.rustamsaga.progress.screens.user.user_repository.elements

import com.rustamsaga.progress.core.data.local.ProgressTargetData
import java.time.OffsetDateTime

object TargetData {
    val progressTargetData = ProgressTargetData(
        id = 1L,
        name = "English",
        description = "for test",
        userId = 1,
        parentTarget = 0,
        isParent = true,
        checkInTime = OffsetDateTime.now(),
        isActive = true,
        notes = NotesEntity.createNoteOfProgressTarget(1L),
        currentProgress = ProgressesEntity.getListCurrentProgresses(1L),
        standardProgress = ProgressesEntity.getListStandardProgresses(1L)
    )


    fun getListOfChildrenTarget(parentId: Long): List<ProgressTargetData> {
        val list = mutableListOf<ProgressTargetData>()
        for (i in 2..5) {
            list.add(
                ProgressTargetData(
                    id = i.toLong(),
                    name = "test target name$i",
                    description = "test description$i",
                    userId = 1,
                    parentTarget = parentId,
                    isParent = false,
                    checkInTime = OffsetDateTime.now().plusDays(1),
                    isActive = true,
                    notes = NotesEntity.createNoteOfProgressTarget(i.toLong()),
                    currentProgress = ProgressesEntity.getListCurrentProgresses(1L),
                    standardProgress = ProgressesEntity.getListStandardProgresses(1L)
                )
            )
        }
        return list
    }
}