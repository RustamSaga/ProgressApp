package com.rustamsaga.progress.core.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.rustamsaga.progress.core.data.local.Headings
import com.rustamsaga.progress.core.data.mapper.ProgressMapper
import com.rustamsaga.progress.core.domain.models.CurrentProgressModel
import com.rustamsaga.progress.core.domain.models.ProgressInterface
import com.rustamsaga.progress.core.domain.models.StandardProgressModel
import com.rustamsaga.progress.core.utils.TimeConverters
import java.time.OffsetDateTime


@Entity(
    tableName = Headings.STANDARD_PROGRESS_TABLE,
    foreignKeys = [
        ForeignKey(
            entity = ProgressTargetEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("targetId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class StandardProgressEntity(
    @PrimaryKey(autoGenerate = true)
    val idUnit: Long? = null,
    val targetId: Long,
    val title: String,
    val description: String,
    @TypeConverters(TimeConverters::class)
    val checkInTime: OffsetDateTime,
    val level: String
)  {
    // TODO nulable
    fun map(mapper: ProgressMapper<StandardProgressModel>) =
        mapper.map(
            idUnit!!, targetId, title, description, checkInTime, level
        )
}