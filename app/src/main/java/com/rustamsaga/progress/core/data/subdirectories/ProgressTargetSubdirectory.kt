package com.rustamsaga.progress.core.data.subdirectories

import com.rustamsaga.progress.core.data.ProgressTargetCacheDataSource
import com.rustamsaga.progress.core.data.local.entity.ProgressTargetEntity
import com.rustamsaga.progress.core.utils.TimeConverters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface ProgressTargetSubdirectory {

    interface Read {
        suspend fun getTargetById(id: Long): ProgressTargetEntity
        fun getTargetsByUser(userId: Long): Flow<List<ProgressTargetEntity>>
        fun getActiveTargetsByUser(userId: Long, isActive: Boolean): Flow<List<ProgressTargetEntity>>
        fun getAllTargets(): Flow<List<ProgressTargetEntity>>
        fun getChildrenTarget(parentTargetId: Long): Flow<List<ProgressTargetEntity>>
        suspend fun contains(name: String, checkInTime: String): Boolean

    }

    interface Save {
        suspend fun insertTarget(target: ProgressTargetEntity): Boolean
        suspend fun deleteTarget(target: ProgressTargetEntity): Boolean
        suspend fun update(obj: ProgressTargetEntity): Boolean
    }

    interface Mutate : Read, Save

    class Mutable(
        private val cache: ProgressTargetCacheDataSource
    ) : Mutate {
        override suspend fun getTargetById(id: Long): ProgressTargetEntity {
//            return cache.progressTargetDao().getTargetById(id)
//                ?: throw IllegalStateException()
            TODO("Not yet implemented")

        }

        override fun getTargetsByUser(userId: Long): Flow<List<ProgressTargetEntity>> {
//            return cache.progressTargetDao().getTargetsByUser(userId)
            TODO("Not yet implemented")

        }

        override fun getActiveTargetsByUser(
            userId: Long,
            isActive: Boolean
        ): Flow<List<ProgressTargetEntity>> {
//            return cache.progressTargetDao().getActiveTargetsByUser(userId, isActive)
            TODO("Not yet implemented")

        }

        override fun getAllTargets(): Flow<List<ProgressTargetEntity>> {
            return flow {
                cache.progressTargetDao().getAllTargets()
            }
        }

        override fun getChildrenTarget(parentTargetId: Long): Flow<List<ProgressTargetEntity>> {
            return flow {
                cache.progressTargetDao().getChildrenTarget(parentTargetId)
            }
        }

        override suspend fun insertTarget(target: ProgressTargetEntity): Boolean {
            cache.progressTargetDao().insertTarget(target)
            return cache.progressTargetDao()
                .contains(target.name, TimeConverters().fromOffsetDateTime(target.checkInTime))
        }

        override suspend fun deleteTarget(target: ProgressTargetEntity): Boolean {
            cache.progressTargetDao().deleteTarget(target)
            return !cache.progressTargetDao()
                .contains(target.name, TimeConverters().fromOffsetDateTime(target.checkInTime))
        }

        override suspend fun contains(name: String, checkInTime: String): Boolean {
            return cache.progressTargetDao().contains(name, checkInTime)
        }

        override suspend fun update(obj: ProgressTargetEntity): Boolean {
            cache.progressTargetDao().insertTarget(obj)
//            return cache.progressTargetDao().getTargetById(obj.id!!) != obj
            TODO("Not yet implemented")

        }

    }

    class Immutable(
        private val cache: ProgressTargetCacheDataSource
    ) : Read {
        override fun getChildrenTarget(parentTargetId: Long): Flow<List<ProgressTargetEntity>> {
            return flow {
                cache.progressTargetDao().getChildrenTarget(parentTargetId)
            }
        }
        override suspend fun getTargetById(id: Long): ProgressTargetEntity {
//            return cache.progressTargetDao().getTargetById(id)
//                ?: throw IllegalStateException()
            TODO("Not yet implemented")

        }

        override fun getTargetsByUser(userId: Long): Flow<List<ProgressTargetEntity>> {
//            return cache.progressTargetDao().getTargetsByUser(userId)
            TODO("Not yet implemented")

        }

        override fun getActiveTargetsByUser(
            userId: Long,
            isActive: Boolean
        ): Flow<List<ProgressTargetEntity>> {
//            return cache.progressTargetDao().getActiveTargetsByUser(userId, isActive)
            TODO("Not yet implemented")

        }

        override fun getAllTargets(): Flow<List<ProgressTargetEntity>> {
            return flow {
                cache.progressTargetDao().getAllTargets()
            }
        }

        override suspend fun contains(name: String, checkInTime: String): Boolean {
            return cache.progressTargetDao().contains(name, checkInTime)
        }
    }
}
