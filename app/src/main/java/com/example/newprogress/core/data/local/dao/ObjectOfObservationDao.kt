package com.example.newprogress.core.data.local.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.Companion.REPLACE
import com.example.newprogress.core.data.local.ObjectOfObservationData
import com.example.newprogress.core.data.local.entity.ObjectOfObservationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ObjectOfObservationDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertObject(obj: ObjectOfObservationEntity)

    //TODO entity in target, note screens
//    @Query("SELECT id, firstName, lastName, isActive FROM person_table WHERE id =:id")
//    suspend fun getAPartOfObject(id: Long): ObjectOfObservationEntity?

    @Query("SELECT * FROM person_table WHERE id =:id")
    @Transaction
    fun getObjectWholly(id: Long): Flow<ObjectOfObservationData?>

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
    fun getAllObjects(): Flow<List<ObjectOfObservationEntity>>

    @Query("SELECT * FROM person_table WHERE isActive =:isActive")
    fun getAllObjects(isActive: Boolean): Flow<List<ObjectOfObservationEntity>>

    @Query("SELECT * FROM person_table WHERE observed =:isObserved")
    fun getAllObserved(isObserved: Boolean): Flow<List<ObjectOfObservationEntity>>

}