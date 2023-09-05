package com.example.newprogress.di

import android.content.Context
import com.example.newprogress.core.data.CacheDataSource
import com.example.newprogress.core.data.local.entity.CurrentProgressEntity
import com.example.newprogress.core.data.local.entity.NoteOfObjectEntity
import com.example.newprogress.core.data.local.entity.NoteOfProgressTargetEntity
import com.example.newprogress.core.data.local.entity.ObjectOfObservationEntity
import com.example.newprogress.core.data.local.entity.ProgressTargetEntity
import com.example.newprogress.core.data.local.entity.StandardProgressEntity
import com.example.newprogress.core.data.mapper.ObjectToCache
import com.example.newprogress.core.data.mapper.ObjectToDomain
import com.example.newprogress.core.data.mapper.notes.NoteOfObjectToCache
import com.example.newprogress.core.data.mapper.notes.NoteOfTargetToCache
import com.example.newprogress.core.data.mapper.notes.NoteOfTargetToDomain
import com.example.newprogress.core.data.mapper.progresses.CurrentToEntity
import com.example.newprogress.core.data.mapper.progresses.CurrentToModel
import com.example.newprogress.core.data.mapper.progresses.ProgressTargetToCache
import com.example.newprogress.core.data.mapper.progresses.ProgressTargetToDomain
import com.example.newprogress.core.data.mapper.progresses.StandardToEntity
import com.example.newprogress.core.data.mapper.progresses.StandardToModel
import com.example.newprogress.core.domain.mapper.NoteMapper
import com.example.newprogress.core.domain.mapper.ObjectOfObservationMapper
import com.example.newprogress.core.domain.mapper.ProgressMapper
import com.example.newprogress.core.domain.mapper.TargetMapper
import com.example.newprogress.core.domain.models.CurrentProgressModel
import com.example.newprogress.core.domain.models.NoteOfProgressTargetModel
import com.example.newprogress.core.domain.models.ObjectOfObservationModel
import com.example.newprogress.core.domain.models.ProgressTargetModel
import com.example.newprogress.core.domain.models.StandardProgressModel
import com.example.newprogress.core.navigation.AppNavigator
import com.example.newprogress.core.navigation.AppNavigatorImpl
import com.example.newprogress.core.utils.ManageResources
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Singleton
    @Binds
    fun bindContext(@ApplicationContext context: Context): Context

    @Singleton
    @Binds
    fun provideCacheDataSource(cache: CacheDataSource.Base): CacheDataSource

    @Singleton
    @Binds
    fun bindAppNavigator(appNavigatorImpl: AppNavigatorImpl): AppNavigator

    @Singleton
    @Binds
    fun bindManagerResources(manager: ManageResources.Base): ManageResources


}



@Module
@InstallIn(SingletonComponent::class)
class MappersModule {

    @Provides
    fun provideObjectToDomain(): ObjectOfObservationMapper<ObjectOfObservationModel> {
        return ObjectToDomain()
    }

    @Provides
    fun provideObjectToCache(): ObjectOfObservationMapper<ObjectOfObservationEntity> {
        return ObjectToCache()
    }

    @Provides
    fun provideTargetToDomain(): TargetMapper<ProgressTargetModel> {
        return ProgressTargetToDomain()
    }

    @Provides
    fun provideTargetToCache(): TargetMapper<ProgressTargetEntity> {
        return ProgressTargetToCache()
    }

    @Provides
    fun provideCurrentProgressToDomain(): ProgressMapper<CurrentProgressModel> {
        return CurrentToModel()
    }

    @Provides
    fun provideCurrentProgressToCache(): ProgressMapper<CurrentProgressEntity> {
        return CurrentToEntity()
    }

    @Provides
    fun provideStandardProgressToDomain(): ProgressMapper<StandardProgressModel> {
        return StandardToModel()
    }

    @Provides
    fun provideStandardProgressToCache(): ProgressMapper<StandardProgressEntity> {
        return StandardToEntity()
    }

    @Provides
    fun provideNoteTargetToDomain(): NoteMapper<NoteOfProgressTargetModel> {
        return NoteOfTargetToDomain()
    }

    @Provides
    fun provideNoteTargetToCache(): NoteMapper<NoteOfProgressTargetEntity> {
        return NoteOfTargetToCache()
    }

    @Provides
    fun provideNoteUserToCache(): NoteMapper<NoteOfObjectEntity> {
        return NoteOfObjectToCache()
    }


}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope