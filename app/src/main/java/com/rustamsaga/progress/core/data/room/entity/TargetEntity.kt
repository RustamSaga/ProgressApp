package com.rustamsaga.progress.core.data.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.rustamsaga.progress.core.data.room.Headings

@Entity(
    tableName = Headings.TARGET_TABLE,
    foreignKeys = [
        ForeignKey(
            entity = PersonEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("personId"),
            onDelete = CASCADE
        )]
)
data class TargetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val personId: Int,
    val parentTarget: Int,
    val isGroup: Boolean,
    val checkInTime: String
    )
