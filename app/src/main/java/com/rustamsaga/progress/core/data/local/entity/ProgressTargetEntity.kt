package com.rustamsaga.progress.core.data.local.entity

import androidx.room.*
import com.rustamsaga.progress.core.data.local.Headings
import com.rustamsaga.progress.core.domain.mapper.NoteMapper
import com.rustamsaga.progress.core.domain.mapper.TargetMapper
import com.rustamsaga.progress.core.utils.TimeConverters
import java.time.OffsetDateTime

@Entity(
    tableName = Headings.PROGRESS_TARGET_TABLE,
    foreignKeys = [
        ForeignKey(
            entity = ObjectOfObservationEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("userId"),
            onDelete = ForeignKey.CASCADE
        )]
)
data class ProgressTargetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val name: String,
    val description: String,
    val userId: Long,
    val parentTarget: Long = Headings.NO_PARENTS,
    val isParent: Boolean,
    @TypeConverters(TimeConverters::class)
    val checkInTime: OffsetDateTime,
    val isActive: Boolean,
)  {

    fun <T> map(mapper: TargetMapper<T>): T =
        mapper.mapProgressTarget(
            id = id!!,
            name = name,
            description = description,
            userId = userId,
            parentTarget = parentTarget,
            isParent = isParent,
            checkInTime = checkInTime,
            isActive = isActive,
            targets = emptyList(),
            notes = emptyList(),//notes.map { it.map(noteMapper) },
            currentProgress = emptyList(),
            standardProgress = emptyList()
        )

}

@Entity(
    tableName = Headings.NOTE_OF_TARGET,
    foreignKeys = [
        ForeignKey(
            entity = ProgressTargetEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("targetId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class NoteOfProgressTargetEntity(
    @PrimaryKey(autoGenerate = true)
    val noteId: Long? = null,
    val targetId: Long,
    val title: String,
    val description: String,
    @TypeConverters(TimeConverters::class)
    val checkInTime: OffsetDateTime
) {
    fun <T> map(mapper: NoteMapper<T>): T = mapper.map(
        noteId = noteId!!,
        title = title,
        description = description,
        parentId = targetId,
        checkInTime = checkInTime
    )
}


