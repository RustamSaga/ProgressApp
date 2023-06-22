package com.rustamsaga.progress.core.data.mapper

import com.rustamsaga.progress.core.data.local.entity.NoteOfObjectEntity
import com.rustamsaga.progress.core.data.local.entity.ObjectOfObservationEntity
import com.rustamsaga.progress.core.data.local.entity.ProgressTargetData
import com.rustamsaga.progress.core.data.local.entity.ProgressTargetEntity
import com.rustamsaga.progress.core.domain.models.NoteOfObjectModel
import com.rustamsaga.progress.core.domain.models.ObjectOfObservationModel
import com.rustamsaga.progress.core.domain.models.ProgressTargetModel
import java.time.OffsetDateTime

interface ObjectOfObservationMapper<O, T, N> {
    suspend fun mapObject(
        id: Long,
        firstName: String,
        lastName: String,
        description: String,
        observed: Boolean = true,
        checkInTime: OffsetDateTime,
        isActive: Boolean,
        targets: List<T>,
        notes: List<N>
    ): O

    class ObjectToEntity : ObjectOfObservationMapper<ObjectOfObservationEntity, Any, Any> {
        override suspend fun mapObject(
            id: Long,
            firstName: String,
            lastName: String,
            description: String,
            observed: Boolean,
            checkInTime: OffsetDateTime,
            isActive: Boolean,
            targets: List<Any>,
            notes: List<Any>
        ): ObjectOfObservationEntity = ObjectOfObservationEntity(
            id, firstName, lastName, description, observed, checkInTime, isActive
        )
    }

    class ObjectToDomain :
        ObjectOfObservationMapper<ObjectOfObservationModel, ProgressTargetModel, NoteOfObjectModel> {
        override suspend fun mapObject(
            id: Long,
            firstName: String,
            lastName: String,
            description: String,
            observed: Boolean,
            checkInTime: OffsetDateTime,
            isActive: Boolean,
            targets: List<ProgressTargetModel>,
            notes: List<NoteOfObjectModel>
        ): ObjectOfObservationModel = ObjectOfObservationModel(
            id, firstName, lastName, description, observed, checkInTime, isActive, targets, notes
        )
    }
}