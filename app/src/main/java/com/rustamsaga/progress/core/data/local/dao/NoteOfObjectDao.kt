package com.rustamsaga.progress.core.data.local.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.Companion.REPLACE
import com.rustamsaga.progress.core.data.local.entity.NoteOfObjectEntity

@Dao
interface NoteOfObjectDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertNote(note: NoteOfObjectEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertNotes(note: List<NoteOfObjectEntity>)

    @Delete
    suspend fun deleteNote(note: NoteOfObjectEntity)

    @Query(
        """
           SELECT * FROM note_of_object 
           WHERE userId =:userId AND checkInTime LIKE :date
        """
    )
    suspend fun getNotesByDate(userId: Long, date: String): List<NoteOfObjectEntity>

    @Query("SELECT * FROM note_of_object WHERE userId =:userId")
    suspend fun getNotesByUser(userId: Long): List<NoteOfObjectEntity>

    @Query(
        """
            SELECT
            EXISTS (SELECT title, checkInTime FROM note_of_object 
            WHERE userId =:userId AND checkInTime =:checkInTime)
        """
    )
    suspend fun contains(userId: Long, checkInTime: String): Boolean


}