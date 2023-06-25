package com.rustamsaga.progress.screens.user.domain

import com.rustamsaga.progress.core.domain.models.*
import java.time.OffsetDateTime

interface UserCollector {

    suspend fun getUser(): ObjectOfObservationModel
    suspend fun updateUser(user: ObjectOfObservationModel): Boolean
    suspend fun deleteUser(user: ObjectOfObservationModel): Boolean
    suspend fun containsUser(firstName: String, checkInTime: OffsetDateTime): Boolean

    suspend fun createTarget(target: ProgressTargetModel): Boolean
    suspend fun deleteTarget(target: ProgressTargetModel): Boolean
    suspend fun containsTarget(name: String, checkInTime: OffsetDateTime): Boolean

    suspend fun createNoteUser(note: NoteOfObjectModel): Boolean
    suspend fun deleteNoteUser(note: NoteOfObjectModel): Boolean
    suspend fun containsNoteUser(checkInTime: OffsetDateTime): Boolean

    suspend fun createNoteTarget(note: NoteOfProgressTargetModel): Boolean
    suspend fun deleteNoteTarget(note: NoteOfProgressTargetModel): Boolean
    suspend fun containsNoteTarget(title: String, checkInTime: OffsetDateTime): Boolean

    suspend fun addCurrentProgress(progress: CurrentProgressModel): Boolean
    suspend fun updateCurrentProgress(progress: CurrentProgressModel): Boolean
    suspend fun deleteCurrentProgress(progress: CurrentProgressModel): Boolean
    suspend fun containsCurrentProgress(title: String, checkInTime: OffsetDateTime): Boolean


    suspend fun addStandardProgress(progress: StandardProgressModel): Boolean
    suspend fun updateStandardProgress(progress: StandardProgressModel): Boolean
    suspend fun deleteStandardProgress(progress: StandardProgressModel): Boolean
    suspend fun containsStandardProgress(title: String, checkInTime: OffsetDateTime): Boolean

}