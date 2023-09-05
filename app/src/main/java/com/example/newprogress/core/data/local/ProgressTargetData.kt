package com.example.newprogress.core.data.local

import androidx.room.Relation
import com.example.newprogress.core.data.local.entity.CurrentProgressEntity
import com.example.newprogress.core.data.local.entity.NoteOfProgressTargetEntity
import com.example.newprogress.core.data.local.entity.StandardProgressEntity
import com.example.newprogress.core.domain.mapper.NoteMapper
import com.example.newprogress.core.domain.mapper.ProgressMapper
import com.example.newprogress.core.domain.mapper.TargetMapper
import com.example.newprogress.core.domain.models.CurrentProgressModel
import com.example.newprogress.core.domain.models.NoteOfProgressTargetModel
import com.example.newprogress.core.domain.models.StandardProgressModel
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
