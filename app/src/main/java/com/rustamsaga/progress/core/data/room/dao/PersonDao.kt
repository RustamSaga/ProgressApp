package com.rustamsaga.progress.core.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.rustamsaga.progress.core.data.room.entity.PersonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertPerson(person: PersonEntity)

    @Query("SELECT * FROM person_table WHERE id =:personId")
    suspend fun getPersonById(personId: Int): PersonEntity

    @Delete
    suspend fun deletePerson(person: PersonEntity)

    @Query("SELECT * FROM person_table")
    fun getAllPersons(): Flow<List<PersonEntity>>

    @Query("SELECT * FROM person_table WHERE observed =:observed")
    fun getAllObserved(observed: Boolean): Flow<List<PersonEntity>>

}