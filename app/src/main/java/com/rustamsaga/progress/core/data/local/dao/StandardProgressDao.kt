package com.rustamsaga.progress.core.data.local.dao

import androidx.room.*
import com.rustamsaga.progress.core.data.local.entity.StandardProgressEntity
import com.rustamsaga.progress.core.utils.TimeConverters

@Dao
@TypeConverters(TimeConverters::class)
interface StandardProgressDao {

    @Insert
    suspend fun insertStandardProgress(standardProgress: StandardProgressEntity)

    @Update
    suspend fun updateStandardProgress(standardProgress: StandardProgressEntity)

    @Query(
        """
        SELECT * FROM standard_progress_table 
        WHERE targetId =:targetId 
        AND checkInTime =:checkInTime
        """
    )
    suspend fun getStandardProgressesWithDate(
        targetId: Long,
        checkInTime: String
    ): List<StandardProgressEntity>

    @Query("SELECT * FROM standard_progress_table WHERE targetId =:targetId")
    suspend fun getStandardProgressByTargetId(targetId: Long): List<StandardProgressEntity>

    @Delete
    suspend fun deleteStandardProgress(standardProgress: StandardProgressEntity)

    @Query(
        """
            SELECT
                EXISTS (SELECT title, checkInTime FROM standard_progress_table 
                WHERE title =:title AND checkInTime =:checkInTime)
        """
    )
    suspend fun contains(title: String, checkInTime: String): Boolean

    @Insert
    suspend fun insertStandardProgresses(progresses: List<StandardProgressEntity>)

}