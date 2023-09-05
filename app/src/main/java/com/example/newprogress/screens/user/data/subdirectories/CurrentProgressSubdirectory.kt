package com.example.newprogress.screens.user.data.subdirectories

import com.example.newprogress.core.data.CurrentProgressDataSource
import com.example.newprogress.core.data.local.entity.CurrentProgressEntity
import com.example.newprogress.core.utils.TimeConverters
import kotlinx.coroutines.flow.Flow

interface CurrentProgressSubdirectory {

    interface Read{
        fun getCurrentProgressesWithDate(
            targetId: Long,
            date: String
        ): Flow<List<CurrentProgressEntity>>

        fun getCurrentProgressesByTargetId(targetId: Long): Flow<List<CurrentProgressEntity>>

        fun contains(title: String, checkInTime: String): Boolean

    }

    interface Save{
        suspend fun insertCurrentProgress(currentProgress: CurrentProgressEntity): Boolean
        suspend fun updateCurrentProgress(currentProgress: CurrentProgressEntity): Boolean
        suspend fun deleteCurrentProgress(currentProgress: CurrentProgressEntity): Boolean
    }

    interface Mutate : Save, Read

    class Mutable(
        private val cache: CurrentProgressDataSource
    ) : Mutate {

        override fun getCurrentProgressesWithDate(
            targetId: Long,
            date: String
        ): Flow<List<CurrentProgressEntity>> {
//            return cache.currentProgressDao().getCurrentProgressesWithDate(
//                targetId, date
//            )
            TODO("Not yet implemented")

        }

        override fun getCurrentProgressesByTargetId(targetId: Long): Flow<List<CurrentProgressEntity>> {
//            return cache.currentProgressDao().getCurrentProgressesByTargetId(targetId)
            TODO("Not yet implemented")

        }

        override fun contains(title: String, checkInTime: String): Boolean {
//            return cache.currentProgressDao().contains(title, checkInTime)
            TODO("Not yet implemented")

        }

        override suspend fun insertCurrentProgress(currentProgress: CurrentProgressEntity): Boolean {
            cache.currentProgressDao().insertCurrentProgress(currentProgress)
            return cache.currentProgressDao()
                .contains(currentProgress.title, TimeConverters().fromOffsetDateTime(currentProgress.checkInTime))
        }

        override suspend fun updateCurrentProgress(currentProgress: CurrentProgressEntity): Boolean {
            cache.currentProgressDao().updateCurrentProgress(currentProgress)
            return cache.currentProgressDao()
                .contains(currentProgress.title, TimeConverters().fromOffsetDateTime(currentProgress.checkInTime))
        }

        override suspend fun deleteCurrentProgress(currentProgress: CurrentProgressEntity): Boolean {
            cache.currentProgressDao().deleteCurrentProgress(currentProgress)
            return !cache.currentProgressDao().contains(
                currentProgress.title, TimeConverters().fromOffsetDateTime(currentProgress.checkInTime)
            )
        }
    }

    class Immutable(
        private val cache: CurrentProgressDataSource
    ) : Read {
        override fun getCurrentProgressesWithDate(
            targetId: Long,
            date: String
        ): Flow<List<CurrentProgressEntity>> {
//            return cache.currentProgressDao().getCurrentProgressesWithDate(
//                targetId, date
//            )
            TODO("Not yet implemented")

        }

        override fun getCurrentProgressesByTargetId(targetId: Long): Flow<List<CurrentProgressEntity>> {
//            return cache.currentProgressDao().getCurrentProgressesByTargetId(targetId)
            TODO("Not yet implemented")

        }

        override fun contains(title: String, checkInTime: String): Boolean {
//            return cache.currentProgressDao().contains(title, checkInTime)
            TODO("Not yet implemented")

        }

    }
}