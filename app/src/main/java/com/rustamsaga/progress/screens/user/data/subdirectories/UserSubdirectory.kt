package com.rustamsaga.progress.screens.user.data.subdirectories

import com.rustamsaga.progress.core.data.ObjectOfObservationCacheDataSource
import com.rustamsaga.progress.core.data.local.entity.ObjectOfObservationData
import com.rustamsaga.progress.core.data.local.entity.ObjectOfObservationEntity
import com.rustamsaga.progress.core.utils.TimeConverters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface UserSubdirectory {

    interface Read {
        suspend fun getObjectById(id: Long): ObjectOfObservationData
        fun getAllObjects(): Flow<List<ObjectOfObservationEntity>>
        fun getAllObjects(isActive: Boolean): Flow<List<ObjectOfObservationEntity>>
        fun getAllObserved(isObserved: Boolean): Flow<List<ObjectOfObservationEntity>>
        suspend fun contains(firstName: String, checkInTime: String): Boolean
    }

    interface Save {
        suspend fun insertObject(obj: ObjectOfObservationEntity): Boolean
        suspend fun update(obj: ObjectOfObservationEntity): Boolean
        suspend fun deleteObject(obj: ObjectOfObservationEntity): Boolean

    }

    interface Mutate : Read, Save

    class Mutable(private val cacheDataSource: ObjectOfObservationCacheDataSource) : Mutate {
        override suspend fun getObjectById(id: Long): ObjectOfObservationData {
            return cacheDataSource.objectOfObservationDao().getObjectWholly(id)
                ?: throw IllegalStateException()
        }

        override fun getAllObjects(): Flow<List<ObjectOfObservationEntity>> {
            return flow {
                cacheDataSource.objectOfObservationDao().getAllObjects()
            }
        }

        override fun getAllObjects(isActive: Boolean): Flow<List<ObjectOfObservationEntity>> {
            return flow {
                cacheDataSource.objectOfObservationDao().getAllObjects(isActive)
            }
        }

        override fun getAllObserved(isObserved: Boolean): Flow<List<ObjectOfObservationEntity>> {
            return flow {
                cacheDataSource.objectOfObservationDao().getAllObserved(isObserved)
            }
        }

        override suspend fun contains(firstName: String, checkInTime: String): Boolean {
            return cacheDataSource.objectOfObservationDao().contains(firstName, checkInTime)
        }

        override suspend fun insertObject(obj: ObjectOfObservationEntity): Boolean {
            cacheDataSource.objectOfObservationDao().insertObject(obj)

            return cacheDataSource.objectOfObservationDao()
                .contains(obj.firstName, TimeConverters().fromOffsetDateTime(obj.checkInTime))

        }

        override suspend fun deleteObject(obj: ObjectOfObservationEntity): Boolean {
            cacheDataSource.objectOfObservationDao().deleteObject(obj)
            return cacheDataSource.objectOfObservationDao()
                .contains(obj.firstName, TimeConverters().fromOffsetDateTime(obj.checkInTime))
        }

        override suspend fun update(obj: ObjectOfObservationEntity): Boolean {
            cacheDataSource.objectOfObservationDao().insertObject(obj)
            return obj == cacheDataSource.objectOfObservationDao().getObject(obj.id!!)
        }


    }

    class Immutable(private val cacheDataSource: ObjectOfObservationCacheDataSource) : Read {
        override suspend fun getObjectById(id: Long): ObjectOfObservationData {
            return cacheDataSource.objectOfObservationDao().getObjectWholly(id)
                ?: throw IllegalStateException()
        }

        override fun getAllObjects(): Flow<List<ObjectOfObservationEntity>> {
            return flow {
                cacheDataSource.objectOfObservationDao().getAllObjects()
            }
        }

        override fun getAllObjects(isActive: Boolean): Flow<List<ObjectOfObservationEntity>> {
            return flow {
                cacheDataSource.objectOfObservationDao().getAllObjects(isActive)
            }
        }

        override fun getAllObserved(isObserved: Boolean): Flow<List<ObjectOfObservationEntity>> {
            return flow {
                cacheDataSource.objectOfObservationDao().getAllObserved(isObserved)
            }
        }

        override suspend fun contains(firstName: String, checkInTime: String): Boolean {
            return cacheDataSource.objectOfObservationDao().contains(firstName, checkInTime)
        }
    }
}
