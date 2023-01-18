package com.rustamsaga.progress.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.rustamsaga.progress.data.Headings

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
    val isGroup: Boolean
    )
