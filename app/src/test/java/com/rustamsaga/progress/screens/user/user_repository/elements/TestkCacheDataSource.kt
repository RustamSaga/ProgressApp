package com.rustamsaga.progress.screens.user.user_repository.elements

import com.rustamsaga.progress.core.data.local.ProgressTargetData
import com.rustamsaga.progress.core.data.local.dao.*
import com.rustamsaga.progress.core.data.local.entity.*
import com.rustamsaga.progress.core.data.mapper.ObjectToCache
import com.rustamsaga.progress.core.data.mapper.ObjectToData
import com.rustamsaga.progress.core.data.mapper.boxes.NoteBoxMapperImp
import com.rustamsaga.progress.core.data.mapper.boxes.TargetBoxMapperImpl
import com.rustamsaga.progress.core.data.mapper.notes.NoteOfObjectToCache
import com.rustamsaga.progress.core.data.mapper.progresses.CurrentToEntity
import com.rustamsaga.progress.core.data.mapper.progresses.ProgressTargetToCache
import com.rustamsaga.progress.core.data.mapper.progresses.ProgressTargetToData
import com.rustamsaga.progress.core.domain.models.ObjectOfObservationModel
import com.rustamsaga.progress.core.domain.models.ProgressTargetModel
import com.rustamsaga.progress.core.utils.TimeConverters
import io.mockk.*

object TestCacheDataSource {

    suspend fun getMockObjectOfObservationDao(
        user: ObjectOfObservationModel,
        updatedUser: ObjectOfObservationModel
    ): ObjectOfObservationDao {
        val answerUser = user.map(
            ObjectToData(),
            NoteBoxMapperImp(noteEntity = user.notes.map { it.map(NoteOfObjectToCache()) }),
            TargetBoxMapperImpl()
        )
        val insertUser = answerUser.map(ObjectToCache(), NoteBoxMapperImp(), TargetBoxMapperImpl())
        val insertUpdateUserName = updatedUser.map(ObjectToCache(), NoteBoxMapperImp(), TargetBoxMapperImpl())
        val dao = mockk<ObjectOfObservationDao>()
        coEvery { dao.insertObject(insertUser) } just runs
        coEvery { dao.insertObject(insertUpdateUserName) } just runs
        coEvery { dao.getObjectWholly(answerUser.id) } coAnswers { answerUser }
        coEvery { dao.deleteObject(insertUser) } just runs
        coEvery {
            dao.contains(
                answerUser.firstName,
                TimeConverters().fromOffsetDateTime(answerUser.checkInTime)
            )
        } coAnswers { true }
        coEvery {
            dao.contains(
                updatedUser.firstName,
                TimeConverters().fromOffsetDateTime(answerUser.checkInTime)
            )
        } coAnswers { true }

        coEvery {
            dao.contains(
                ObjectsEntity.default.firstName,
                TimeConverters().fromOffsetDateTime(ObjectsEntity.default.checkInTime)
            )
        } coAnswers { true }

        return dao
    }

    suspend fun getMockProgressTargetDao(
        targets: List<ProgressTargetModel>
    ): ProgressTargetDao {

        val dao = mockk<ProgressTargetDao>()
        val children = partitionIntoParts(targets, true)
        val activeTargets = getActiveTargets(targets)

//        coEvery { dao.insertTarget(any()) } just runs
        coEvery { dao.getChildrenTarget(any()) } returnsMany children
        coEvery { dao.getTargetsByUser(any()) } coAnswers { targets.map { it.map(ProgressTargetToData()) } }
        coEvery { dao.getActiveTargetsByUser(any(), any()) } coAnswers { activeTargets }
        targets.map { it.map(ProgressTargetToCache()) }.forEach {
            coEvery { dao.deleteTarget(it) } just runs
        }
//        coEvery { dao.contains(any(), any()) } returns true


        return dao
    }

    suspend fun getMockkCurrentProgressDao(): CurrentProgressDao {
        val dao = mockk<CurrentProgressDao>()
        coEvery { dao.insertCurrentProgress(any()) } just runs
        coEvery { dao.insertCurrentProgresses(any()) } just runs
        coEvery { dao.deleteCurrentProgress(any()) } just runs
        coEvery { dao.updateCurrentProgress(any()) } just runs
        coEvery { dao.contains(any(), any()) } coAnswers { true }

        return dao
    }

    suspend fun getMockkStandardProgressDao(): StandardProgressDao {
        val dao = mockk<StandardProgressDao>()
        coEvery { dao.insertStandardProgress(any()) } just runs
        coEvery { dao.insertStandardProgresses(any()) } just runs
        coEvery { dao.deleteStandardProgress(any()) } just runs
        coEvery { dao.updateStandardProgress(any()) } just runs
        coEvery { dao.contains(any(), any()) } coAnswers { true }

        return dao
    }

    suspend fun getNoteOfObjectDao(): NoteOfObjectDao {
        val dao = mockk<NoteOfObjectDao>()
        coEvery { dao.deleteNote(any()) } just runs
        coEvery { dao.getNotesByUser(any()) } coAnswers { emptyList() }
        coEvery { dao.getNotesByDate(any(), any()) } coAnswers { emptyList() }
        return dao
    }

    suspend fun getNoteOfTargetDao(): NoteOfTargetDao {
        val dao = mockk<NoteOfTargetDao>()
        coEvery { dao.insertNote(any()) } just runs
        coEvery { dao.insertNotes(any()) } just runs
        coEvery { dao.deleteNote(any()) } just runs
        coEvery { dao.getNoteByDate(any(), any()) } coAnswers { emptyList() }
        coEvery { dao.getNotesByTargetId(any()) } coAnswers { emptyList() }
        coEvery { dao.contains(any(), any()) } coAnswers { true }
        return dao
    }

    private fun partitionIntoParts(
        targets: List<ProgressTargetModel>,
        isFirst: Boolean
    ): List<List<ProgressTargetData>> {
        val res = mutableListOf<List<ProgressTargetData>>()
        if (!isFirst) {
            res.add(targets.map { it.map(ProgressTargetToData()) })
        }
        targets.forEach {
            if (it.isParent) {
                res.addAll(partitionIntoParts(it.targets, false))
            }
        }
        return res
    }

    private fun getActiveTargets(targets: List<ProgressTargetModel>): List<ProgressTargetData> {
        val res = mutableListOf<ProgressTargetData>()
        targets.forEach { target ->
            if (target.isActive) {
                res.add(target.map(ProgressTargetToData()))
                if (target.isParent) {
                    val part = getActiveTargets(target.targets)
                    if (part.isNotEmpty())
                        res.addAll(part)
                }
            }
        }
        return res
    }

    private fun getCurrentProgressesWithTarget(target: ProgressTargetModel): List<CurrentProgressEntity> {
        return target.currentProgress.map { it.map(CurrentToEntity()) }
    }
}
