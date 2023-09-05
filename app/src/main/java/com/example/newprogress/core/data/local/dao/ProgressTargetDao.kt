package com.example.newprogress.core.data.local.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.Companion.REPLACE
import com.example.newprogress.core.data.local.ProgressTargetData
import com.example.newprogress.core.data.local.entity.ProgressTargetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressTargetDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertTarget(target: ProgressTargetEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertTargets(targets: List<ProgressTargetEntity>)

    @Transaction
    @Query("SELECT * FROM progress_target_table WHERE id =:id")
    fun getTargetDataById(id: Long): Flow<ProgressTargetData?>

    @Query("SELECT * FROM progress_target_table WHERE id =:id")
    fun getTargetEntityById(id: Long): Flow<ProgressTargetEntity?>

    @Transaction
    @Query("SELECT * FROM progress_target_table")
    fun getAllTargets(): Flow<List<ProgressTargetData>>

    @Transaction
    @Query("SELECT * FROM progress_target_table WHERE userId =:userId")
    fun getTargetsByUser(userId: Long): Flow<List<ProgressTargetData>>

    @Transaction
    @Query("SELECT * FROM progress_target_table WHERE userId =:userId AND isActive =:isActive")
    fun getActiveTargetsByUser(userId: Long, isActive: Boolean): Flow<List<ProgressTargetData>>

    @Transaction
    @Query("SELECT * FROM progress_target_table WHERE parentTarget =:parentTargetId")
    fun getChildrenTarget(parentTargetId: Long): Flow<List<ProgressTargetData>>

    @Delete
    suspend fun deleteTarget(target: ProgressTargetEntity)

    @Query(
        """
            SELECT
            EXISTS (SELECT name, checkInTime FROM progress_target_table 
            WHERE name =:name AND checkInTime =:checkInTime)
        """
    )
    suspend fun contains(name: String, checkInTime: String): Boolean


}