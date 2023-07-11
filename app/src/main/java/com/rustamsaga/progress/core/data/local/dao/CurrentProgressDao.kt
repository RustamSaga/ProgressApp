package com.rustamsaga.progress.core.data.local.dao

import androidx.room.*
import com.rustamsaga.progress.core.data.local.entity.CurrentProgressEntity
import com.rustamsaga.progress.core.utils.TimeConverters

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
    suspend fun getCurrentProgressesWithDate(
        targetId: Long,
        date: String
    ): List<CurrentProgressEntity>

    @Query("SELECT * FROM current_progress_table WHERE targetId =:targetId")
    suspend fun getCurrentProgressesByTargetId(targetId: Long): List<CurrentProgressEntity>

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