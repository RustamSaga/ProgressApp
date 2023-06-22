package com.rustamsaga.progress.core.data.local.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.Companion.REPLACE
import com.rustamsaga.progress.core.data.local.entity.ObjectOfObservationData
import com.rustamsaga.progress.core.data.local.entity.ObjectOfObservationEntity
import kotlinx.coroutines.flow.Flow
import java.time.OffsetDateTime


@Dao
interface ObjectOfObservationDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertObject(obj: ObjectOfObservationEntity)

    @Query("SELECT * FROM person_table WHERE id =:id")
    suspend fun getObjectById(id: Long): ObjectOfObservationEntity?

    @Query("SELECT * FROM person_table WHERE id =:id")
    suspend fun getObjectById2(id: Long): ObjectOfObservationData?

    @Query(
        """
            SELECT
            EXISTS (SELECT firstName, checkInTime FROM person_table 
            WHERE firstName =:firstName AND checkInTime =:checkInTime)
        """
    )
    suspend fun contains(firstName: String, checkInTime: String): Boolean

    @Delete
    suspend fun deleteObject(obj: ObjectOfObservationEntity)

    @Query("SELECT * FROM person_table")
    suspend fun getAllObjects(): List<ObjectOfObservationEntity>

    @Query("SELECT * FROM person_table WHERE isActive =:isActive")
    suspend fun getAllObjects(isActive: Boolean): List<ObjectOfObservationEntity>

    @Query("SELECT * FROM person_table WHERE observed =:isObserved")
    suspend fun getAllObserved(isObserved: Boolean): List<ObjectOfObservationEntity>

}