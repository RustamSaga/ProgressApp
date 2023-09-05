package com.example.newprogress.core.data.mapper

//class DataToCache() : ObjectOfObservationModel.Mapper<ObjectOfObservationEntity>,
//    ProgressTargetModel.Mapper<ProgressTargetEntity>,
//    Decomposition<List<ProgressTargetEntity>, List<ProgressTargetModel>> {
//    override fun mapObject(
//        id: Long,
//        firstName: String,
//        lastName: String,
//        description: String,
//        observed: Boolean,
//        checkInTime: OffsetDateTime,
//        targets: List<ProgressTargetModel>,
//        notes: List<NoteOfObjectModel>
//    ): ObjectOfObservationEntity {
//        return ObjectOfObservationEntity(
//            id = id,
//            firstName = firstName,
//            lastName = lastName,
//            description = description,
//            observed = observed,
//            checkInTime = checkInTime
//        )
//    }
//
//    override fun mapProgressTarget(
//        id: Long,
//        name: String,
//        description: String,
//        userId: Int,
//        parentTarget: Int,
//        isGroup: Boolean,
//        checkInTime: OffsetDateTime,
//        isActive: Boolean,
//        targets: List<ProgressTargetModel>,
//        notes: List<NoteOfTargetModel>
//    ): ProgressTargetEntity {
//        return ProgressTargetEntity(
//            id = id,
//            name = name,
//            description = description,
//            userId = userId,
//            parentTarget = parentTarget,
//            isGroup = isGroup,
//            checkInTime = checkInTime,
//            isActive = isActive
//        )
//    }
//
//    override fun decompose(item: List<ProgressTargetModel>): List<ProgressTargetEntity> {
//        val result = mutableListOf<ProgressTargetEntity>()
//        if (item.isNotEmpty()) {
//            item.forEach {
//                result.add(
//                    mapProgressTarget(
//                        id = it.id,
//                        name = it.name,
//                        description = it.description,
//                        userId = it.userId,
//                        parentTarget = it.parentTarget,
//                        isGroup = it.isGroup,
//                        checkInTime = it.checkInTime,
//                        isActive = it.isActive,
//                        targets = emptyList(),
//                        notes = emptyList()
//                    )
//                )
//                result.addAll(decompose(it.targets))
//            }
//        }
//        return result
//    }
//
//}