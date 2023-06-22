package com.rustamsaga.progress.core.data.local.entity

import androidx.room.*
import com.rustamsaga.progress.core.data.local.Headings
import com.rustamsaga.progress.core.data.mapper.ObjectOfObservationMapper
import com.rustamsaga.progress.core.domain.models.NoteOfObjectInterface
import com.rustamsaga.progress.core.domain.models.ObjectsInterface
import com.rustamsaga.progress.core.utils.TimeConverters
import java.time.OffsetDateTime

@Entity(
    tableName = Headings.PERSON_TABLE
)
@TypeConverters(TimeConverters::class)
data class ObjectOfObservationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val firstName: String,
    val lastName: String = "",
    val description: String,
    val observed: Boolean = true,
    val checkInTime: OffsetDateTime,
    val isActive: Boolean,
) {
    suspend fun <T> map(mapper: ObjectOfObservationMapper<T>): T = mapper.mapObject(
        id = id!!,
        firstName = firstName,
        lastName = lastName,
        description = description,
        observed = observed,
        checkInTime = checkInTime,
        isActive = isActive,
        targets = emptyList<Any>(),
        notes = emptyList<Any>()
    )
}

@Entity(
    tableName = Headings.NOTE_OF_OBJECT,
    foreignKeys = [
        ForeignKey(
            entity = ObjectOfObservationEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("userId"),
            onDelete = ForeignKey.CASCADE
        )]
)

data class NoteOfObjectEntity(
    @PrimaryKey(autoGenerate = true)
    val noteId: Long? = null,
    val title: String,
    val description: String,
    val userId: Long,
    @TypeConverters(TimeConverters::class)
    val checkInTime: OffsetDateTime
)
