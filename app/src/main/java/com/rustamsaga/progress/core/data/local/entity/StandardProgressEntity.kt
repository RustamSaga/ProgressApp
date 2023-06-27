package com.rustamsaga.progress.core.data.local.entity

import androidx.room.*
import com.rustamsaga.progress.core.data.local.Headings
import com.rustamsaga.progress.core.domain.mapper.ProgressMapper
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
    @ColumnInfo(index = true)
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
