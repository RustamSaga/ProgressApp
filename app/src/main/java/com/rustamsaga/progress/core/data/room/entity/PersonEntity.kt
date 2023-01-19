package com.rustamsaga.progress.core.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.rustamsaga.progress.core.data.room.Headings
import java.time.OffsetDateTime

@Entity(
    tableName = Headings.PERSON_TABLE
)
data class PersonEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val observed: Boolean = true,
    val checkInTime: OffsetDateTime? = null
)
