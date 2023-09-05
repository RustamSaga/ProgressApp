package com.rustamsaga.progress.core.room.elements

import com.example.newprogress.core.data.local.ProgressTargetData
import com.example.newprogress.core.data.local.entity.ProgressTargetEntity
import com.rustamsaga.progress.core.room.elements.NotesEntity.createNoteOfProgressTarget
import com.rustamsaga.progress.core.room.elements.Progresses.getListCurrentProgresses
import com.rustamsaga.progress.core.room.elements.Progresses.getListStandardProgresses
import java.time.OffsetDateTime

object Targets {

    val progressTargetData = ProgressTargetData(
        id = 1L,
        name = "English",
        description = "for test",
        userId = 1,
        parentTarget = 0,
        isParent = true,
        checkInTime = OffsetDateTime.now(),
        isActive = true,
        notes = createNoteOfProgressTarget(1L),
        currentProgress = getListCurrentProgresses(1L),
        standardProgress = getListStandardProgresses(1L)
    )

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


    fun getListOfChildrenTarget(parentId: Long): List<ProgressTargetData> {
        val list = mutableListOf<ProgressTargetData>()
        for (i in 2..10) {
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
                    notes = createNoteOfProgressTarget(i.toLong()),
                    currentProgress = getListCurrentProgresses(1L),
                    standardProgress = getListStandardProgresses(1L)
                )
            )
        }
        return list
    }

    fun getListTargets(): List<ProgressTargetEntity> {
        val list = mutableListOf<ProgressTargetEntity>()
        for (i in 1..5) {
            list.add(
                ProgressTargetEntity(
                    id = i.toLong(),
                    name = "Group of targets$i",
                    description = "test description$i",
                    userId = 1,
                    parentTarget = -1,
                    isParent = false,
                    checkInTime = OffsetDateTime.now().plusDays(1),
                    isActive = true
                )
            )
        }
        return list
    }
}