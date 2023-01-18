package com.rustamsaga.progress.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.rustamsaga.progress.data.entity.TargetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TargetDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertTarget(target: TargetEntity)

    @Query("SELECT * FROM target_table WHERE id =:id AND personId =:personId")
    suspend fun getTargetByIdWithPerson(id: Int, personId: Int): TargetEntity

    @Query("SELECT * FROM target_table WHERE parentTarget =:parentTargetId AND personId =:personId")
    fun getChildTargets(parentTargetId: Int, personId: Int): Flow<List<TargetEntity>>

    @Delete
    suspend fun deleteTarget(target: TargetEntity)


}