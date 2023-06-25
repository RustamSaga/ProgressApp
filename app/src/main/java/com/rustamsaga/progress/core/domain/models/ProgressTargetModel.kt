package com.rustamsaga.progress.core.domain.models


import com.rustamsaga.progress.core.domain.mapper.NoteMapper
import com.rustamsaga.progress.core.domain.mapper.TargetMapper
import java.time.OffsetDateTime


data class ProgressTargetModel(
    val id: Long,
    val name: String,
    val description: String,
    val userId: Long,
    val parentTarget: Long,
    val isParent: Boolean,
    val checkInTime: OffsetDateTime,
    val isActive: Boolean,
    val targets: List<ProgressTargetModel>,
    val notes: List<NoteOfProgressTargetModel>,
    val currentProgress: List<CurrentProgressModel>,
    val standardProgress: List<StandardProgressModel>
) {

    fun <T> map(mapper: TargetMapper<T>): T = mapper.mapProgressTarget(
        id,
        name,
        description,
        userId,
        parentTarget,
        isParent,
        checkInTime,
        isActive,
        targets,
        notes,
        currentProgress,
        standardProgress
    )
}

data class NoteOfProgressTargetModel(
    val noteId: Long,
    val title: String,
    val description: String,
    val targetId: Long,
    val checkInTime: OffsetDateTime,
) {
    fun <T> map(mapper: NoteMapper<T>): T = mapper.map(
        noteId = noteId,
        title = title,
        description = description,
        parentId = targetId,
        checkInTime = checkInTime
    )
}
