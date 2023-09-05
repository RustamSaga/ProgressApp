package com.example.newprogress.core.data.local.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.Companion.REPLACE
import com.example.newprogress.core.data.local.entity.NoteOfProgressTargetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteOfTargetDao {


    @Insert(onConflict = REPLACE)
    suspend fun insertNote(note: NoteOfProgressTargetEntity)

    @Delete
    suspend fun deleteNote(note: NoteOfProgressTargetEntity)

    @Query(
        """
           SELECT * FROM note_of_target 
           WHERE targetId =:targetId AND checkInTime LIKE :date
        """
    )
    fun getNoteByDate(targetId: Long, date: String): Flow<List<NoteOfProgressTargetEntity>>


    @Query(
        """
           SELECT * FROM note_of_target 
           WHERE targetId =:targetId
        """
    )
    fun getNotesByTargetId(targetId: Long): Flow<List<NoteOfProgressTargetEntity>>

    @Query(
        """
            SELECT
            EXISTS (SELECT title, checkInTime FROM note_of_target 
            WHERE title =:title AND checkInTime =:checkInTime)
        """
    )
    suspend fun contains(title: String, checkInTime: String): Boolean

    @Insert(onConflict = REPLACE)
    suspend fun insertNotes(notes: List<NoteOfProgressTargetEntity>)

}