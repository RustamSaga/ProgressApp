package com.example.newprogress.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.newprogress.core.data.local.entity.TargetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TargetDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertTarget(target: TargetEntity)

    @Delete
    suspend fun deleteTarget(target: TargetEntity)

    @Query("SELECT * FROM target_entity WHERE id=:id")
    fun getPluginTargetById(id: Int): Flow<TargetEntity?>

    @Query("SELECT * FROM target_entity")
    fun getAllPluginTargets(): Flow<List<TargetEntity>>
}