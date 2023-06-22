package com.rustamsaga.progress.screens.objects_of_observation.data

import com.rustamsaga.progress.core.data.NoteOfObjectDataSource
import com.rustamsaga.progress.core.data.NoteOfTargetDataSource
import com.rustamsaga.progress.core.data.local.entity.CurrentProgressEntity
//import com.rustamsaga.progress.screens.objects_of_observation.DataToCache
import com.rustamsaga.progress.screens.objects_of_observation.DataToDomain
import com.rustamsaga.progress.core.data.subdirectories.CurrentProgressSubdirectory
import com.rustamsaga.progress.core.data.subdirectories.ObjectOfObservationSubdirectory
import com.rustamsaga.progress.core.data.subdirectories.ProgressTargetSubdirectory
import com.rustamsaga.progress.core.data.subdirectories.StandardProgressSubdirectory
import com.rustamsaga.progress.core.domain.models.ObjectOfObservationModel
import com.rustamsaga.progress.screens.objects_of_observation.domain.ObjectScreenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


// TODO код для получения таргетов которые активны и не активны
class ObjectScreenRepositoryImp(
    private val objectSubdirectory: ObjectOfObservationSubdirectory.Mutable,
//    private val mapperToCache: DataToCache,
    private val mapperToDomain: DataToDomain
) : ObjectScreenRepository {
    override suspend fun createObject(obj: ObjectOfObservationModel): Boolean {
//        return objectSubdirectory.insertObject(obj.map(mapperToCache))
        TODO("Not yet implemented")
    }

    // TODO логика, как сделать правильнее? Получение прогресса только о год последний
    override suspend fun getObjectById(id: Long): Result<ObjectOfObservationModel> {

        val result = objectSubdirectory.getObjectById(id).map(mapperToDomain)


//        return try {
//            val result = objectSubdirectory.getObjectById(id)
//            val targets = progressTarget.getActiveTargetsByUser(result.id!!, true)
//            val currentProg = targets.map { listTargets ->
//                listTargets.map { target ->
//                    target.map(mapperToDomain)
//                }
//            }
//            Result.success(result)
//        } catch (e: IllegalStateException) {
//            Result.failure(e)
//        }
        TODO("Not yet implemented")
    }


    override suspend fun update(obj: ObjectOfObservationModel): Boolean {
//        return objectSubdirectory.insertObject(obj.map(mapperToCache))
        TODO("Not yet implemented")

    }

    override fun getAllObjects(): Flow<List<ObjectOfObservationModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteObject(obj: ObjectOfObservationModel): Boolean {
//        return objectSubdirectory.deleteObject(obj.map(mapperToCache))
        TODO("Not yet implemented")

    }

}