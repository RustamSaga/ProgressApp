package com.rustamsaga.progress.core.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.rustamsaga.progress.core.data.room.entity.StandardProgressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StandardProgressDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertCurrentProgress(standardProgress: StandardProgressEntity)

    @Query(
        """
        SELECT * FROM standard_progress_table 
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
    ): StandardProgressEntity

    @Query("SELECT * FROM standard_progress_table WHERE targetId =:targetId")
    fun getCurrentProgressByTargetId(targetId: Int): Flow<List<StandardProgressEntity>>

    @Delete
    suspend fun deleteCurrentProgress(standardProgress: StandardProgressEntity)


}