package com.rustamsaga.progress.core.data.local.entity

import androidx.room.Relation
import com.rustamsaga.progress.core.data.local.Headings
import com.rustamsaga.progress.core.domain.mapper.NoteMapper
import com.rustamsaga.progress.core.domain.mapper.ProgressMapper
import com.rustamsaga.progress.core.domain.mapper.TargetMapper
import com.rustamsaga.progress.core.domain.models.CurrentProgressModel
import com.rustamsaga.progress.core.domain.models.NoteOfProgressTargetModel
import com.rustamsaga.progress.core.domain.models.StandardProgressModel
import java.time.OffsetDateTime

data class ProgressTargetData(
    val id: Long,
    val name: String,
    val description: String,
    val userId: Long,
    val parentTarget: Long = Headings.NO_PARENTS,
    val isParent: Boolean,
    val checkInTime: OffsetDateTime,
    val isActive: Boolean,
    @Relation(
        parentColumn = "id",
        entityColumn = "targetId"
    )
    val notes: List<NoteOfProgressTargetEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "targetId"
    )
    val currentProgress: List<CurrentProgressEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "targetId"
    )
    val standardProgress: List<StandardProgressEntity>
) {

    fun <T> map(
        targetMapper: TargetMapper<T>,
        currentProgressMapper: ProgressMapper<CurrentProgressModel>,
        standardProgressMapper: ProgressMapper<StandardProgressModel>,
        noteMapper: NoteMapper<NoteOfProgressTargetModel>
    ): T =
        targetMapper.mapProgressTarget(
            id = id,
            name = name,
            description = description,
            userId = userId,
            parentTarget = parentTarget,
            isParent = isParent,
            checkInTime = checkInTime,
            isActive = isActive,
            targets = emptyList(),
            notes = notes.map { it.map(noteMapper) },
            currentProgress = currentProgress.map { it.map(currentProgressMapper) },
            standardProgress = standardProgress.map { it.map(standardProgressMapper) }
        )
}
