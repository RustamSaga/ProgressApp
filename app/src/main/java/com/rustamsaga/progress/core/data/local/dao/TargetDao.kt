package com.rustamsaga.progress.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.rustamsaga.progress.core.data.local.entity.TargetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TargetDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertTarget(target: TargetEntity)

    @Delete
    suspend fun deleteTarget(target: TargetEntity)

    @Query("SELECT * FROM target_entity WHERE id=:id")
    suspend fun getPluginTargetById(id: Int): TargetEntity

    @Query("SELECT * FROM target_entity")
    fun getAllPluginTargets(): Flow<List<TargetEntity>>
}