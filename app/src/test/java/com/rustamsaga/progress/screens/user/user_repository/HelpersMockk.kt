package com.rustamsaga.progress.screens.user.user_repository

import com.rustamsaga.progress.core.data.local.dao.*
import com.rustamsaga.progress.core.data.local.entity.*
import com.rustamsaga.progress.core.data.mapper.progresses.ProgressTargetToCache
import com.rustamsaga.progress.core.domain.models.*
import com.rustamsaga.progress.core.utils.TimeConverters
import io.mockk.CapturingSlot
import io.mockk.coEvery
import io.mockk.just
import io.mockk.runs


fun ObjectOfObservationDao.captureInserting(item: CapturingSlot<ObjectOfObservationEntity>) {
    coEvery { insertObject(capture(item)) } just runs

}

fun ObjectOfObservationDao.setBehaviorContains(obj: ObjectOfObservationModel, result: Boolean) {
    coEvery {
        contains(
            obj.firstName,
            TimeConverters().fromOffsetDateTime(obj.checkInTime)
        )
    } returns result
}

fun ProgressTargetDao.setBehaviorContains(target: ProgressTargetModel, result: Boolean) {
    coEvery {
        contains(
            target.name,
            TimeConverters().fromOffsetDateTime(target.checkInTime)
        )
    } returns result
}

fun ProgressTargetDao.captureInserting(item: MutableList<ProgressTargetEntity>) {
    coEvery {
        insertTarget(capture(item))
    } just runs
}

fun CurrentProgressDao.captureInserting(item: MutableList<List<CurrentProgressEntity>>) {
    coEvery {
        insertCurrentProgresses(capture(item))
    } just runs

}

fun CurrentProgressDao.captureSingleInserting(item: MutableList<CurrentProgressEntity>) {
    coEvery {
        insertCurrentProgress(capture(item))
    } just runs
}

fun CurrentProgressDao.captureDeleting(item: MutableList<CurrentProgressEntity>) {
    coEvery {
        deleteCurrentProgress(capture(item))
    } just runs
}

fun CurrentProgressDao.captureUpdating(item: MutableList<CurrentProgressEntity>) {
    coEvery {
        updateCurrentProgress(capture(item))
    } just runs
}

fun CurrentProgressDao.setBehaviorContains(items: List<CurrentProgressModel>, result: Boolean) {
    items.forEach { progress ->
        coEvery {
            contains(progress.title, TimeConverters().fromOffsetDateTime(progress.checkInTime))
        } returns result
    }

}

fun StandardProgressDao.captureSingleInserting(item: MutableList<StandardProgressEntity>) {
    coEvery {
        insertStandardProgress(capture(item))
    } just runs
}

fun StandardProgressDao.captureDeleting(item: MutableList<StandardProgressEntity>) {
    coEvery {
        deleteStandardProgress(capture(item))
    } just runs
}

fun StandardProgressDao.captureUpdating(item: MutableList<StandardProgressEntity>) {
    coEvery {
        updateStandardProgress(capture(item))
    } just runs
}

fun StandardProgressDao.setBehaviorContains(items: List<StandardProgressModel>, result: Boolean) {
    items.forEach { progress ->
        coEvery {
            contains(progress.title, TimeConverters().fromOffsetDateTime(progress.checkInTime))
        } returns result
    }

}

fun StandardProgressDao.captureInserting(item: MutableList<List<StandardProgressEntity>>) {
    coEvery {
        insertStandardProgresses(capture(item))
    } just runs

}

fun NoteOfTargetDao.captureInserting(item: MutableList<List<NoteOfProgressTargetEntity>>){
    coEvery {
        insertNotes(capture(item))
    } just runs
}

fun ProgressTargetDao.captureDeleting(item: MutableList<ProgressTargetEntity>){
    coEvery {
        deleteTarget(capture(item))
    } just runs

}

fun ProgressTargetDao.setBehaviorDelete(target: ProgressTargetModel) {
    coEvery {
        deleteTarget(target.map(ProgressTargetToCache()))
    } just runs
}

fun ProgressTargetDao.setBehaviorInsert(target: ProgressTargetModel) {
    coEvery {
        insertTarget(target.map(ProgressTargetToCache()))
    } just runs
}

fun NoteOfObjectDao.captureDeleting(list: MutableList<NoteOfObjectEntity>){
    coEvery { deleteNote(capture(list)) } just runs
}

fun NoteOfObjectDao.captureInserting(note: MutableList<NoteOfObjectEntity>){
    coEvery { insertNote(capture(note)) } just runs
}

fun NoteOfObjectDao.setBehaviorContains(notes: List<NoteOfObjectModel>, result: Boolean) {
    notes.forEach { note ->
        coEvery {
            contains(
                note.userId,
                TimeConverters().fromOffsetDateTime(note.checkInTime)
            )
        } returns result
    }

}
