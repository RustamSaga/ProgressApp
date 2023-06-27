package com.rustamsaga.progress.core.data.local.entity

import androidx.room.*
import com.rustamsaga.progress.core.data.local.Headings
import com.rustamsaga.progress.core.domain.mapper.NoteBoxMapper
import com.rustamsaga.progress.core.domain.mapper.NoteMapper
import com.rustamsaga.progress.core.domain.mapper.ObjectOfObservationMapper
import com.rustamsaga.progress.core.domain.mapper.TargetBoxMapper
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
    suspend fun <O> map(
        mapper: ObjectOfObservationMapper<O>,
        targetBox: TargetBoxMapper,
        noteBox: NoteBoxMapper
    ): O = mapper.mapObject(
        id = id!!,
        firstName = firstName,
        lastName = lastName,
        description = description,
        observed = observed,
        checkInTime = checkInTime,
        isActive = isActive,
        targets = targetBox,
        notes = noteBox
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
    @ColumnInfo(index = true)
    val userId: Long,
    @TypeConverters(TimeConverters::class)
    val checkInTime: OffsetDateTime
) {
    fun <T> map(mapper: NoteMapper<T>): T = mapper.map(
        noteId = noteId!!,
        title = title,
        description = description,
        parentId = userId,
        checkInTime = checkInTime
    )
}
