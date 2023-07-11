package com.rustamsaga.progress.core.data.local.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.Companion.REPLACE
import com.rustamsaga.progress.core.data.local.ProgressTargetData
import com.rustamsaga.progress.core.data.local.entity.ProgressTargetEntity

@Dao
interface ProgressTargetDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertTarget(target: ProgressTargetEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertTargets(targets: List<ProgressTargetEntity>)

    @Transaction
    @Query("SELECT * FROM progress_target_table WHERE id =:id")
    suspend fun getTargetDataById(id: Long): ProgressTargetData?

    @Query("SELECT * FROM progress_target_table WHERE id =:id")
    suspend fun getTargetEntityById(id: Long): ProgressTargetEntity?

    @Transaction
    @Query("SELECT * FROM progress_target_table")
    suspend fun getAllTargets(): List<ProgressTargetData>

    @Transaction
    @Query("SELECT * FROM progress_target_table WHERE userId =:userId")
    suspend fun getTargetsByUser(userId: Long): List<ProgressTargetData>

    @Transaction
    @Query("SELECT * FROM progress_target_table WHERE userId =:userId AND isActive =:isActive")
    suspend fun getActiveTargetsByUser(userId: Long, isActive: Boolean): List<ProgressTargetData>

    @Transaction
    @Query("SELECT * FROM progress_target_table WHERE parentTarget =:parentTargetId")
    suspend fun getChildrenTarget(parentTargetId: Long): List<ProgressTargetData>

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