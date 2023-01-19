package com.rustamsaga.progress.core.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.rustamsaga.progress.core.data.room.entity.CurrentProgressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentProgressDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertCurrentProgress(currentProgress: CurrentProgressEntity)

    @Query(
        """
        SELECT * FROM current_progress_table 
        WHERE progressId =:progressId 
        AND dayOfMonth =:dayOfMonth
        AND month =:month
        AND year =:year
        """
    )
    suspend fun getUnitOfCurrentProgressWithDate(
        progressId: Int,
        dayOfMonth: Int,
        month: Int,
        year: Int
    ): CurrentProgressEntity

    @Query("SELECT * FROM current_progress_table WHERE targetId =:targetId")
    fun getCurrentProgressByTargetId(targetId: Int): Flow<List<CurrentProgressEntity>>

    @Delete
    suspend fun deleteCurrentProgress(currentProgress: CurrentProgressEntity)


}