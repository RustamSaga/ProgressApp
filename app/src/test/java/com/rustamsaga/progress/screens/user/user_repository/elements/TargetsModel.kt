package com.rustamsaga.progress.screens.user.user_repository.elements

import com.rustamsaga.progress.core.domain.models.ProgressTargetModel
import java.time.OffsetDateTime

object TargetsModel {

    fun getTargets(userId: Long): List<ProgressTargetModel> {

        val targets = mutableListOf<ProgressTargetModel>()
        for (i in 1..4) {
            var targetTitle: String = ""
            var nameOfChildren = listOf("Reading", "Pronunciation", "Writing")
            var isParent = false
            when (i) {
                1 -> targetTitle = "Arabic"
                2 -> targetTitle = "English"
                3 -> targetTitle = "Turkey"
                4 -> {
                    targetTitle = "Sport"
                    nameOfChildren = listOf("Base", "Isometric", "Samson's bag")
                    isParent = true
                }
            }
            val targetId = OffsetDateTime.now().plusNanos(i.toLong()).hashCode().toLong()
            targets.add(
                ProgressTargetModel(
                    id = targetId,
                    name = targetTitle,
                    description = "Test description",
                    userId = userId,
                    parentTarget = -1,
                    isParent = true,
                    checkInTime = OffsetDateTime.now(),
                    isActive = true,
                    targets = getTargetChildren(userId, targetId, isParent, nameOfChildren),
                    notes = NotesModel.createNoteOfProgressTarget(targetId, targetTitle),
                    currentProgress = ProgressesModel.getListCurrentProgresses(
                        targetId,
                        targetTitle
                    ),
                    standardProgress = ProgressesModel.getListStandardProgresses(
                        targetId,
                        targetTitle
                    )
                )
            )
        }
        return targets
    }

    fun getTargetChildren(
        userId: Long,
        parentId: Long,
        isParent: Boolean,
        namesOfChildren: List<String>
    ): List<ProgressTargetModel> {
        val res = mutableListOf<ProgressTargetModel>()
        for (i in namesOfChildren) {
            val targetId = OffsetDateTime.now().hashCode().toLong()
            res.add(
                ProgressTargetModel(
                    id = targetId,
                    name = i,
                    description = "test des",
                    userId = userId,
                    parentTarget = parentId,
                    isParent = isParent,
                    checkInTime = OffsetDateTime.now(),
                    isActive = true,
                    targets = if (isParent) {
                        getTargetChildren(userId, parentId, false, listOf("childTest, childTest"))
                    } else emptyList(),
                    notes = NotesModel.createNoteOfProgressTarget(targetId, i),
                    currentProgress = ProgressesModel.getListCurrentProgresses(targetId, i),
                    standardProgress = ProgressesModel.getListStandardProgresses(targetId, i)
                )
            )

        }
        return res
    }
}