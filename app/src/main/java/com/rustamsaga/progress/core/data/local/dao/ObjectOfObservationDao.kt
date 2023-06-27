package com.rustamsaga.progress.core.data.local.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.Companion.REPLACE
import com.rustamsaga.progress.core.data.local.ObjectOfObservationData
import com.rustamsaga.progress.core.data.local.entity.ObjectOfObservationEntity

@Dao
interface ObjectOfObservationDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertObject(obj: ObjectOfObservationEntity)

    //TODO entity in target, note screens
//    @Query("SELECT id, firstName, lastName, isActive FROM person_table WHERE id =:id")
//    suspend fun getAPartOfObject(id: Long): ObjectOfObservationEntity?

    @Query("SELECT * FROM person_table WHERE id =:id")
    @Transaction
    suspend fun getObjectWholly(id: Long): ObjectOfObservationData?

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