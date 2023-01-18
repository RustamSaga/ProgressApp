package com.rustamsaga.progress.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rustamsaga.progress.data.Headings

@Entity(
    tableName = Headings.PERSON_TABLE
)
data class PersonEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val observed: Boolean = true
)
