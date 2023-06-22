package com.rustamsaga.progress.core.domain.models


import com.rustamsaga.progress.core.data.mapper.NoteMapper
import com.rustamsaga.progress.core.data.mapper.TargetMapper
import java.time.OffsetDateTime


data class ProgressTargetModel(
    val id: Long,
    val name: String,
    val description: String,
    val userId: Long,
    val parentTarget: Long,
    val isGroup: Boolean,
    val checkInTime: OffsetDateTime,
    val isActive: Boolean,
    val targets: List<ProgressTargetModel>,
    val notes: List<NoteOfTargetModel>,
    val currentProgress: List<CurrentProgressModel>,
    val standardProgress: List<StandardProgressModel>
) : TargetsInterface {

    fun <T> map(mapper: TargetMapper<T>): T = mapper.mapProgressTarget(
        id,
        name,
        description,
        userId,
        parentTarget,
        isGroup,
        checkInTime,
        isActive,
        targets,
        notes,
        currentProgress,
        standardProgress
    )
}

data class NoteOfTargetModel(
    val noteId: Long,
    val title: String,
    val description: String,
    val targetId: Long,
    val checkInTime: OffsetDateTime,
) : NoteOfTargetInterface {
    fun <T> map(mapper: NoteMapper<T>): T = mapper.map(
        noteId = noteId,
        title = title,
        description = description,
        parentId = targetId,
        checkInTime = checkInTime
    )
}
