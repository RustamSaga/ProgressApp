package com.rustamsaga.progress.screens.user.data

import com.rustamsaga.progress.core.data.CacheDataSource
import com.rustamsaga.progress.core.domain.Repository
import com.rustamsaga.progress.core.domain.models.ObjectOfObservationModel
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(
    private val cacheDataSource: CacheDataSource
): Repository.Mutable<ObjectOfObservationModel> {
    override suspend fun get(id: Long): ObjectOfObservationModel {
        TODO("Not yet implemented")
    }

    override fun getAll(): Flow<List<ObjectOfObservationModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun set(obj: ObjectOfObservationModel): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun update(obj: ObjectOfObservationModel): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun delete(obj: ObjectOfObservationModel): Boolean {
        TODO("Not yet implemented")
    }



}