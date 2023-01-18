package com.rustamsaga.progress.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.rustamsaga.progress.data.Headings


@Entity(
    tableName = Headings.CURRENT_PROGRESS_TABLE,
    foreignKeys = [
        ForeignKey(
            entity = TargetEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("targetId"),
            onDelete = CASCADE
        )
    ]
)
data class StandardProgressEntity(
    @PrimaryKey(autoGenerate = true)
    val idUnit: Int? = null,
    val progressId: Int,
    val name: String,
    val date: String,
    val level: String
)
