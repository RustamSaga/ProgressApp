package com.rustamsaga.progress.core.data.local.dao

import androidx.room.*
import com.rustamsaga.progress.core.data.local.entity.NoteOfObjectEntity
import com.rustamsaga.progress.core.data.local.entity.NoteOfProgressTargetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteOfTargetDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteOfProgressTargetEntity)

    @Delete
    suspend fun deleteNote(note: NoteOfProgressTargetEntity)

    @Query(
        """
           SELECT * FROM note_of_target 
           WHERE targetId =:targetId AND checkInTime LIKE :date
        """
    )
    suspend fun getNoteByDate(targetId: Long, date: String): List<NoteOfProgressTargetEntity>


    @Query(
        """
           SELECT * FROM note_of_target 
           WHERE targetId =:targetId
        """
    )
    suspend fun getNoteByTargetId(targetId: Long): List<NoteOfProgressTargetEntity>

    @Query(
        """
            SELECT
            EXISTS (SELECT title, checkInTime FROM note_of_target 
            WHERE targetId =:targetId AND checkInTime =:checkInTime)
        """
    )
    suspend fun contains(targetId: Long, checkInTime: String): Boolean

}