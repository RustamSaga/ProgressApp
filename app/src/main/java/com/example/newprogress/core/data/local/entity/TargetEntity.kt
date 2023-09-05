package com.example.newprogress.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.newprogress.core.data.local.Headings


// TODO target DAO and how create target from idea
@Entity(
    tableName = Headings.TARGET_ENTITY
)
data class TargetEntity(
    @PrimaryKey (autoGenerate = true)
    val id: Int? = null,
    val name: String,
)

@Entity(
    tableName = Headings.PARAM_OF_TARGET,
    foreignKeys = [
        ForeignKey(
            entity = TargetEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("targetId"),
            onDelete = ForeignKey.CASCADE
        )]
)
data class ParamOfTargetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(index = true)
    val targetId: Int,
    val name: String,
    val value: String
)
