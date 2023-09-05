package com.example.newprogress.core.data.local.dao

import androidx.room.*
import com.example.newprogress.core.data.local.entity.StandardProgressEntity
import com.example.newprogress.core.utils.TimeConverters
import kotlinx.coroutines.flow.Flow

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
    fun getStandardProgressesWithDate(
        targetId: Long,
        checkInTime: String
    ): Flow<List<StandardProgressEntity>>

    @Query("SELECT * FROM standard_progress_table WHERE targetId =:targetId")
    fun getStandardProgressByTargetId(targetId: Long): Flow<List<StandardProgressEntity>>

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