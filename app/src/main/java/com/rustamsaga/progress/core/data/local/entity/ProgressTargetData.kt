package com.rustamsaga.progress.core.data.local.entity

import androidx.room.Relation
import com.rustamsaga.progress.core.data.local.Headings
import com.rustamsaga.progress.core.data.mapper.NoteMapper
import com.rustamsaga.progress.core.data.mapper.ProgressMapper
import com.rustamsaga.progress.core.data.mapper.TargetMapper
import com.rustamsaga.progress.core.domain.models.CurrentProgressModel
import com.rustamsaga.progress.core.domain.models.NoteOfTargetModel
import com.rustamsaga.progress.core.domain.models.StandardProgressModel
import com.rustamsaga.progress.core.domain.models.TargetsInterface
import java.time.OffsetDateTime

data class ProgressTargetData(
    val id: Long,
    val name: String,
    val description: String,
    val userId: Long,
    val parentTarget: Long = Headings.NO_PARENTS,
    val isGroup: Boolean,
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
        noteMapper: NoteMapper<NoteOfTargetModel>
    ): T =
        targetMapper.mapProgressTarget(
            id = id,
            name = name,
            description = description,
            userId = userId,
            parentTarget = parentTarget,
            isGroup = isGroup,
            checkInTime = checkInTime,
            isActive = isActive,
            targets = emptyList(),
            notes = notes.map { it.map(noteMapper) },
            currentProgress = currentProgress.map { it.map(currentProgressMapper) },
            standardProgress = standardProgress.map { it.map(standardProgressMapper) }
        )
}
