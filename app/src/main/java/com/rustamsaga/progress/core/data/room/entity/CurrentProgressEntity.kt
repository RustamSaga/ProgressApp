package com.rustamsaga.progress.core.data.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.rustamsaga.progress.core.data.room.Headings


@Entity(
    tableName = Headings.CURRENT_PROGRESS_TABLE,
    foreignKeys = [
        ForeignKey(
            entity = TargetEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("targetId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CurrentProgressEntity(
    @PrimaryKey(autoGenerate = true)
    val idUnit: Int? = null,
    val progressId: Int,
    val targetId: Int,
    val name: String,
    val time: String,
    val dayOfMonth: Int,
    val month: Int,
    val year: Int,
    val level: String
)
