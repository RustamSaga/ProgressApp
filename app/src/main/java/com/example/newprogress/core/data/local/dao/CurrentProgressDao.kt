package com.example.newprogress.core.data.local.dao

import androidx.room.*
import com.example.newprogress.core.data.local.entity.CurrentProgressEntity
import com.example.newprogress.core.utils.TimeConverters
import kotlinx.coroutines.flow.Flow

@Dao
@TypeConverters(TimeConverters::class)
interface CurrentProgressDao {

    @Insert
    suspend fun insertCurrentProgress(currentProgress: CurrentProgressEntity)

    @Insert
    suspend fun insertCurrentProgresses(currentProgresses: List<CurrentProgressEntity>)

    @Update
    suspend fun updateCurrentProgress(currentProgress: CurrentProgressEntity)


    @Query(
        """
            SELECT * FROM current_progress_table 
            WHERE targetId =:targetId AND checkInTime LIKE :date
        """
    )
    fun getCurrentProgressesWithDate(
        targetId: Long,
        date: String
    ): Flow<List<CurrentProgressEntity>>

    @Query("SELECT * FROM current_progress_table WHERE targetId =:targetId")
    fun getCurrentProgressesByTargetId(targetId: Long): Flow<List<CurrentProgressEntity>>

    @Delete
    suspend fun deleteCurrentProgress(currentProgress: CurrentProgressEntity)

    @Query(
        """
            SELECT
                EXISTS (SELECT title, checkInTime FROM current_progress_table 
                WHERE title =:title AND checkInTime =:checkInTime)
        """
    )
    suspend fun contains(title: String, checkInTime: String): Boolean


}