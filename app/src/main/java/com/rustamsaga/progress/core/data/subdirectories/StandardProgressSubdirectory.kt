package com.rustamsaga.progress.core.data.subdirectories

import com.rustamsaga.progress.core.data.StandardProgressDataSource
import com.rustamsaga.progress.core.data.local.entity.StandardProgressEntity
import com.rustamsaga.progress.core.utils.TimeConverters
import kotlinx.coroutines.flow.Flow

interface StandardProgressSubdirectory {

    interface Read {
        fun getStandardProgressesWithDate(
            targetId: Long,
            date: String
        ): Flow<List<StandardProgressEntity>>

        fun getStandardProgressesByTargetId(targetId: Long): Flow<List<StandardProgressEntity>>

        fun contains(title: String, checkInTime: String): Boolean

    }

    interface Save {
        suspend fun insertStandardProgress(standardProgress: StandardProgressEntity): Boolean
        suspend fun updateStandardProgress(standardProgress: StandardProgressEntity): Boolean
        suspend fun deleteStandardProgress(standardProgress: StandardProgressEntity): Boolean
    }

    interface Mutate : Save, Read
    class Mutable(
        private val cache: StandardProgressDataSource
    ) : Mutate {
        override suspend fun insertStandardProgress(standardProgress: StandardProgressEntity): Boolean {
            cache.standardProgressDao().insertCurrentProgress(standardProgress)
            return contains(
                standardProgress.title,
                TimeConverters().fromOffsetDateTime(standardProgress.checkInTime)
            )
        }

        override suspend fun updateStandardProgress(standardProgress: StandardProgressEntity): Boolean {
            cache.standardProgressDao().updateStandardProgress(standardProgress)
            return contains(
                standardProgress.title,
                TimeConverters().fromOffsetDateTime(standardProgress.checkInTime)
            )
        }

        override suspend fun deleteStandardProgress(standardProgress: StandardProgressEntity): Boolean {
            cache.standardProgressDao().deleteStandardProgress(standardProgress)
            return !cache.standardProgressDao().contains(
                standardProgress.title,
                TimeConverters().fromOffsetDateTime(standardProgress.checkInTime)
            )
        }

        override fun getStandardProgressesWithDate(
            targetId: Long,
            date: String
        ): Flow<List<StandardProgressEntity>> {
//            return cache.standardProgressDao().getStandardProgressesWithDate(targetId, date)
            TODO("Not yet implemented")

        }

        override fun getStandardProgressesByTargetId(targetId: Long): Flow<List<StandardProgressEntity>> {
//            return cache.standardProgressDao().getStandardProgressByTargetId(targetId)
            TODO("Not yet implemented")

        }

        override fun contains(title: String, checkInTime: String): Boolean {
//            return cache.standardProgressDao().contains(title, checkInTime)
            TODO("Not yet implemented")

        }
    }

    class Immutable(
        private val cache: StandardProgressDataSource
    ): Read {
        override fun getStandardProgressesWithDate(
            targetId: Long,
            date: String
        ): Flow<List<StandardProgressEntity>> {
//            return cache.standardProgressDao().getStandardProgressesWithDate(targetId, date)
            TODO("Not yet implemented")

        }

        override fun getStandardProgressesByTargetId(targetId: Long): Flow<List<StandardProgressEntity>> {
//            return cache.standardProgressDao().getStandardProgressByTargetId(targetId)
            TODO("Not yet implemented")

        }

        override fun contains(title: String, checkInTime: String): Boolean {
//            return cache.standardProgressDao().contains(title, checkInTime)
            TODO("Not yet implemented")

        }

    }
}