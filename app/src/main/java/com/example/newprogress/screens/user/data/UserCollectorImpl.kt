package com.example.newprogress.screens.user.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.newprogress.core.data.CacheDataSource
import com.example.newprogress.core.data.local.entity.CurrentProgressEntity
import com.example.newprogress.core.data.local.entity.NoteOfObjectEntity
import com.example.newprogress.core.data.local.entity.NoteOfProgressTargetEntity
import com.example.newprogress.core.data.local.entity.ObjectOfObservationEntity
import com.example.newprogress.core.data.local.entity.ProgressTargetEntity
import com.example.newprogress.core.data.local.entity.StandardProgressEntity
import com.rustamsaga.progress.core.data.*
import com.rustamsaga.progress.core.data.local.entity.*
import com.example.newprogress.core.data.mapper.boxes.NoteBoxMapperImp
import com.example.newprogress.core.data.mapper.boxes.TargetBoxMapperImpl
import com.example.newprogress.core.domain.mapper.NoteMapper
import com.example.newprogress.core.domain.mapper.ObjectOfObservationMapper
import com.example.newprogress.core.domain.mapper.ProgressMapper
import com.example.newprogress.core.domain.mapper.Spreader
import com.example.newprogress.core.domain.mapper.TargetMapper
import com.example.newprogress.core.domain.models.CurrentProgressModel
import com.example.newprogress.core.domain.models.NoteOfObjectModel
import com.example.newprogress.core.domain.models.NoteOfProgressTargetModel
import com.example.newprogress.core.domain.models.ObjectOfObservationModel
import com.example.newprogress.core.domain.models.ProgressTargetModel
import com.example.newprogress.core.domain.models.StandardProgressModel
import com.rustamsaga.progress.core.domain.mapper.*
import com.rustamsaga.progress.core.domain.models.*
import com.example.newprogress.core.utils.TimeConverters
import com.example.newprogress.screens.user.data.utils.mapToDomain
import com.example.newprogress.screens.user.data.utils.modelMap
import com.example.newprogress.screens.user.domain.UserCollector
import kotlinx.coroutines.flow.Flow
import java.time.OffsetDateTime
import javax.inject.Inject


class UserCollectorImpl @Inject constructor(
    private val cacheDataSource: CacheDataSource,
    private val objectToDomain: ObjectOfObservationMapper<ObjectOfObservationModel>,
    private val objectToCache: ObjectOfObservationMapper<ObjectOfObservationEntity>,
    private val targetToDomain: TargetMapper<ProgressTargetModel>,
    private val targetToCache: TargetMapper<ProgressTargetEntity>,
    private val currentProgressToDomain: ProgressMapper<CurrentProgressModel>,
    private val currentProgressToCache: ProgressMapper<CurrentProgressEntity>,
    private val standardProgressToDomain: ProgressMapper<StandardProgressModel>,
    private val standardProgressToCache: ProgressMapper<StandardProgressEntity>,
    private val noteTargetToDomain: NoteMapper<NoteOfProgressTargetModel>,
    private val noteTargetToCache: NoteMapper<NoteOfProgressTargetEntity>,
    private val noteUserToCache: NoteMapper<NoteOfObjectEntity>,
    private val targetSpreader: Spreader<ProgressTargetModel>
) : UserCollector {
    private val userId = 1L
    override suspend fun getUser(): Flow<ObjectOfObservationModel> {
        return cacheDataSource.objectOfObservationDao()
            .getObjectWholly(userId).modelMap { observation ->
                observation?.map(
                    mapper = objectToDomain,
                    noteBox = NoteBoxMapperImp(),
                    targetBox = TargetBoxMapperImpl()
                )?.copy(
                    targets = targetSpreader.get(
                        cacheDataSource.progressTargetDao().getTargetsByUser(userId).mapToDomain { targetList ->
                            targetList.map { target ->
                                target.map(
                                    targetToDomain,
                                    currentProgressToDomain,
                                    standardProgressToDomain,
                                    noteTargetToDomain
                                )
                            }
                        }
                    )
                ) ?: throw IllegalStateException()
            }
    }

    override suspend fun updateUser(user: ObjectOfObservationModel): Boolean {
        cacheDataSource.objectOfObservationDao().insertObject(
            user.map(
                mapper = objectToCache,
                noteBox = NoteBoxMapperImp(),
                targetBox = TargetBoxMapperImpl()
            )
        )
        return cacheDataSource.objectOfObservationDao()
            .contains(user.firstName, TimeConverters().fromOffsetDateTime(user.checkInTime))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun deleteUser(user: ObjectOfObservationModel): Boolean {
        cacheDataSource.objectOfObservationDao().insertObject(userDefault)

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
        targetSpreader.decompose(target)

        return containsTarget(target.name, target.checkInTime)
    }

    /**
     * Returns `true` if target deleted.
     */
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

    /**
     * Returns `true` if note deleted.
     */
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

    /**
     * Returns `true` if note deleted.
     */
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

    /**
     * Returns `true` if progress deleted.
     */
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

    /**
     * Returns `true` if progress deleted.
     */
    override suspend fun deleteStandardProgress(progress: StandardProgressModel): Boolean {
        cacheDataSource.standardProgressDao().deleteStandardProgress(
            progress.map(
                mapper = standardProgressToCache
            )
        )
        return !containsStandardProgress(progress.title, progress.checkInTime)
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

    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        val userDefault: ObjectOfObservationEntity =
            ObjectOfObservationEntity(
                id = 1,
                firstName = "Progressor",
                lastName = "",
                description = "",
                observed = false,
                checkInTime = OffsetDateTime.now(),
                isActive = true
            )
    }

}