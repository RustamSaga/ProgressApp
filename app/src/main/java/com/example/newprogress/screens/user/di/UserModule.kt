package com.example.newprogress.screens.user.di

import com.example.newprogress.core.data.CacheDataSource
import com.example.newprogress.core.data.local.entity.CurrentProgressEntity
import com.example.newprogress.core.data.local.entity.NoteOfObjectEntity
import com.example.newprogress.core.data.local.entity.NoteOfProgressTargetEntity
import com.example.newprogress.core.data.local.entity.ObjectOfObservationEntity
import com.example.newprogress.core.data.local.entity.ProgressTargetEntity
import com.example.newprogress.core.data.local.entity.StandardProgressEntity
import com.rustamsaga.progress.core.data.local.entity.*
import com.example.newprogress.core.data.mapper.TargetPacker
import com.example.newprogress.core.domain.mapper.NoteMapper
import com.example.newprogress.core.domain.mapper.ObjectOfObservationMapper
import com.example.newprogress.core.domain.mapper.ProgressMapper
import com.example.newprogress.core.domain.mapper.Spreader
import com.example.newprogress.core.domain.mapper.TargetMapper
import com.example.newprogress.core.domain.models.CurrentProgressModel
import com.example.newprogress.core.domain.models.NoteOfProgressTargetModel
import com.example.newprogress.core.domain.models.ObjectOfObservationModel
import com.example.newprogress.core.domain.models.ProgressTargetModel
import com.example.newprogress.core.domain.models.StandardProgressModel
import com.rustamsaga.progress.core.domain.mapper.*
import com.rustamsaga.progress.core.domain.models.*
import com.example.newprogress.screens.user.data.UserCollectorImpl
import com.example.newprogress.screens.user.data.UserRepositoryImpl
import com.example.newprogress.screens.user.domain.UserCollector
import com.example.newprogress.screens.user.domain.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class UserModule {

    @Provides
    @Singleton
    fun provideTargetPacker(
        cache: CacheDataSource,
        targetToDomain: TargetMapper<ProgressTargetModel>,
        targetToCache: TargetMapper<ProgressTargetEntity>,
        currentToDomain: ProgressMapper<CurrentProgressModel>,
        currentToCache: ProgressMapper<CurrentProgressEntity>,
        standardToDomain: ProgressMapper<StandardProgressModel>,
        standardToCache: ProgressMapper<StandardProgressEntity>,
        noteToDomain: NoteMapper<NoteOfProgressTargetModel>,
        noteToCache: NoteMapper<NoteOfProgressTargetEntity>
    ): Spreader<ProgressTargetModel> {
        return TargetPacker(
            cache,
            targetToDomain,
            targetToCache,
            currentToDomain,
            currentToCache,
            standardToDomain,
            standardToCache,
            noteToDomain,
            noteToCache
        )
    }

    @Provides
    @Singleton
    fun provideUserCollector(
        cacheDataSource: CacheDataSource,
        objectToDomain: ObjectOfObservationMapper<ObjectOfObservationModel>,
        objectToCache: ObjectOfObservationMapper<ObjectOfObservationEntity>,
        targetToDomain: TargetMapper<ProgressTargetModel>,
        targetToCache: TargetMapper<ProgressTargetEntity>,
        currentProgressToDomain: ProgressMapper<CurrentProgressModel>,
        currentProgressToCache: ProgressMapper<CurrentProgressEntity>,
        standardProgressToDomain: ProgressMapper<StandardProgressModel>,
        standardProgressToCache: ProgressMapper<StandardProgressEntity>,
        noteTargetToDomain: NoteMapper<NoteOfProgressTargetModel>,
        noteTargetToCache: NoteMapper<NoteOfProgressTargetEntity>,
        noteUserToCache: NoteMapper<NoteOfObjectEntity>,
        targetSpreader: Spreader<ProgressTargetModel>
    ): UserCollector {
        return UserCollectorImpl(
            cacheDataSource,
            objectToDomain,
            objectToCache,
            targetToDomain,
            targetToCache,
            currentProgressToDomain,
            currentProgressToCache,
            standardProgressToDomain,
            standardProgressToCache,
            noteTargetToDomain,
            noteTargetToCache,
            noteUserToCache,
            targetSpreader
        )
    }

    @Provides
    @Singleton
    fun provideRepository(userCollector: UserCollectorImpl): UserRepository {
        return UserRepositoryImpl(userCollector)
    }
}
