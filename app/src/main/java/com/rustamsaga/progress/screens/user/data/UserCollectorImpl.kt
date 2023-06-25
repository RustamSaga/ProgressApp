package com.rustamsaga.progress.screens.user.data

import com.rustamsaga.progress.R
import com.rustamsaga.progress.core.data.*
import com.rustamsaga.progress.core.data.local.entity.*
import com.rustamsaga.progress.core.data.mapper.ObjectToCache
import com.rustamsaga.progress.core.data.mapper.ObjectToDomain
import com.rustamsaga.progress.core.domain.mapper.*
import com.rustamsaga.progress.core.domain.models.*
import com.rustamsaga.progress.core.utils.ManageResources
import com.rustamsaga.progress.core.utils.TimeConverters
import com.rustamsaga.progress.screens.user.domain.UserCollector
import java.time.OffsetDateTime


class UserCollectorImpl(
    private val manageResources: ManageResources,
    private val cacheDataSource: CacheDataSource,
    private val objectToDomain: ObjectToDomain,
    private val objectToCache: ObjectToCache,
    private val noteBoxMapper: NoteBoxMapper,
    private val targetBoxMapper: TargetBoxMapper,
    private val targetToDomain: TargetMapper<ProgressTargetModel>,
    private val targetToCache: TargetMapper<ProgressTargetEntity>,
    private val currentProgressToDomain: ProgressMapper<CurrentProgressModel>,
    private val currentProgressToCache: ProgressMapper<CurrentProgressEntity>,
    private val standardProgressToDomain: ProgressMapper<StandardProgressModel>,
    private val standardProgressToCache: ProgressMapper<StandardProgressEntity>,
    private val noteTargetToDomain: NoteMapper<NoteOfProgressTargetModel>,
    private val noteTargetToCache: NoteMapper<NoteOfProgressTargetEntity>,
    private val noteUserToCache: NoteMapper<NoteOfObjectEntity>,
    private val targetSpreader: Spreader<ProgressTargetModel, ProgressTargetEntity>
) : UserCollector {
    private val userId = 1L
    override suspend fun getUser(): ObjectOfObservationModel {
        return cacheDataSource.objectOfObservationDao()
            .getObjectWholly(userId)?.map(
                mapper = objectToDomain,
                noteBox = noteBoxMapper,
                targetBox = targetBoxMapper
            )?.copy(
                targets = targetSpreader.get(
                    cacheDataSource.progressTargetDao().getTargetsByUser(userId).map {
                        it.map(
                            targetToDomain,
                            currentProgressToDomain,
                            standardProgressToDomain,
                            noteTargetToDomain
                        )
                    }
                )
            ) ?: throw IllegalStateException()
    }

    override suspend fun updateUser(user: ObjectOfObservationModel): Boolean {
        cacheDataSource.objectOfObservationDao().insertObject(
            user.map(
                mapper = objectToCache,
                noteBox = noteBoxMapper,
                targetBox = targetBoxMapper
            )
        )
        return cacheDataSource.objectOfObservationDao()
            .contains(user.firstName, TimeConverters().fromOffsetDateTime(user.checkInTime))
    }

    override suspend fun deleteUser(user: ObjectOfObservationModel): Boolean {
        cacheDataSource.objectOfObservationDao().insertObject(
            user.map(
                mapper = objectToCache,
                noteBox = noteBoxMapper,
                targetBox = targetBoxMapper
            ).copy(
                firstName = manageResources.string(id = R.string.progressor),
                lastName = "",
                description = "",
                observed = false,
                checkInTime = OffsetDateTime.now(),
                isActive = true
            )
        )

        user.targets.forEach {
            deleteTarget(it)
        }
        user.notes.forEach {
            deleteNoteUser(it)
        }
        return !cacheDataSource.objectOfObservationDao()
            .contains(user.firstName, TimeConverters().fromOffsetDateTime(user.checkInTime))
    }

    override suspend fun containsUser(firstName: String, checkInTime: OffsetDateTime): Boolean {
        return cacheDataSource.objectOfObservationDao()
            .contains(firstName, TimeConverters().fromOffsetDateTime(checkInTime))
    }

    override suspend fun createTarget(target: ProgressTargetModel): Boolean {
        cacheDataSource.progressTargetDao().insertTarget(
            target.map(
                targetToCache
            )
        )
        return containsTarget(target.name, target.checkInTime)
    }

    override suspend fun deleteTarget(target: ProgressTargetModel): Boolean {
        cacheDataSource.progressTargetDao().deleteTarget(
            target.map(
                targetToCache
            )
        )
        return !containsTarget(target.name, target.checkInTime)
    }

    override suspend fun createNoteUser(note: NoteOfObjectModel): Boolean {
        cacheDataSource.noteOfObjectDao().insertNote(
            note.map(
                noteUserToCache
            )
        )
        return containsNoteUser(note.checkInTime)
    }


    override suspend fun deleteNoteUser(note: NoteOfObjectModel): Boolean {
        cacheDataSource.noteOfObjectDao().deleteNote(
            note.map(
                mapper = noteUserToCache
            )
        )
        return !containsNoteUser(note.checkInTime)
    }

    override suspend fun containsNoteUser(checkInTime: OffsetDateTime): Boolean {
        return cacheDataSource.noteOfObjectDao().contains(
            userId = userId, checkInTime = TimeConverters().fromOffsetDateTime(checkInTime)
        )
    }

    override suspend fun createNoteTarget(note: NoteOfProgressTargetModel): Boolean {
        cacheDataSource.noteOfTargetDao().insertNote(
            note.map(
                noteTargetToCache
            )
        )
        return containsNoteTarget(note.title, note.checkInTime)
    }

    override suspend fun deleteNoteTarget(note: NoteOfProgressTargetModel): Boolean {
        cacheDataSource.noteOfTargetDao().deleteNote(
            note.map(
                mapper = noteTargetToCache
            )
        )
        return !containsNoteTarget(note.title, note.checkInTime)
    }

    override suspend fun containsNoteTarget(title: String, checkInTime: OffsetDateTime): Boolean {
        return cacheDataSource.noteOfTargetDao().contains(
            title = title,
            TimeConverters().fromOffsetDateTime(checkInTime)
        )
    }

    override suspend fun addCurrentProgress(progress: CurrentProgressModel): Boolean {
        cacheDataSource.currentProgressDao().insertCurrentProgress(
            progress.map(
                mapper = currentProgressToCache
            )
        )
        return containsCurrentProgress(progress.title, progress.checkInTime)
    }

    override suspend fun updateCurrentProgress(progress: CurrentProgressModel): Boolean {
        cacheDataSource.currentProgressDao().updateCurrentProgress(
            progress.map(
                mapper = currentProgressToCache
            )
        )
        return containsCurrentProgress(progress.title, progress.checkInTime)

    }

    override suspend fun deleteCurrentProgress(progress: CurrentProgressModel): Boolean {
        cacheDataSource.currentProgressDao().deleteCurrentProgress(
            progress.map(
                mapper = currentProgressToCache
            )
        )
        return !containsCurrentProgress(progress.title, progress.checkInTime)
    }

    override suspend fun containsCurrentProgress(
        title: String,
        checkInTime: OffsetDateTime
    ): Boolean {
        return cacheDataSource.currentProgressDao().contains(
            title = title,
            checkInTime = TimeConverters().fromOffsetDateTime(checkInTime)
        )
    }

    override suspend fun addStandardProgress(progress: StandardProgressModel): Boolean {
        cacheDataSource.standardProgressDao().insertStandardProgress(
            progress.map(
                mapper = standardProgressToCache
            )
        )
        return containsStandardProgress(progress.title, progress.checkInTime)
    }

    override suspend fun updateStandardProgress(progress: StandardProgressModel): Boolean {
        cacheDataSource.standardProgressDao().updateStandardProgress(
            progress.map(
                mapper = standardProgressToCache
            )
        )
        return containsStandardProgress(progress.title, progress.checkInTime)
    }

    override suspend fun deleteStandardProgress(progress: StandardProgressModel): Boolean {
        cacheDataSource.standardProgressDao().deleteStandardProgress(
            progress.map(
                mapper = standardProgressToCache
            )
        )
        return containsStandardProgress(progress.title, progress.checkInTime)
    }

    override suspend fun containsStandardProgress(
        title: String,
        checkInTime: OffsetDateTime
    ): Boolean {
        return cacheDataSource.standardProgressDao().contains(
            title = title,
            checkInTime = TimeConverters().fromOffsetDateTime(checkInTime)
        )
    }

    override suspend fun containsTarget(name: String, checkInTime: OffsetDateTime): Boolean {
        return cacheDataSource.progressTargetDao().contains(
            name, TimeConverters().fromOffsetDateTime(checkInTime)
        )
    }


}