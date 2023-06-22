package com.rustamsaga.progress.room.elements

import android.util.Log
import com.google.common.hash.HashCode
import com.rustamsaga.progress.core.data.local.entity.*
import java.time.OffsetDateTime

object TestObjectOfObservationElements {

    val obj: ObjectOfObservationEntity =
        ObjectOfObservationEntity(
            id = 1,
            firstName = "Tolib",
            lastName = "TestLastName",
            description = "for test",
            observed = false,
            checkInTime = OffsetDateTime.now(),
            isActive = true
        )

    val progressTarget = ProgressTargetData(
        id = 1L,
        name = "English",
        description = "for test",
        userId = 1,
        parentTarget = 0,
        isGroup = true,
        checkInTime = OffsetDateTime.now(),
        isActive = true,
        notes = getNoteOfProgressTarget(1L),
        currentProgress = getListCurrentProgresses(1L),
        standardProgress = getListStandardProgresses(1L)
    )

    fun getNoteOfProgressTarget(targetId: Long): List<NoteOfProgressTargetEntity> {
        val list = mutableListOf<NoteOfProgressTargetEntity>()
        for (i in 1..10) {
            list.add(
                NoteOfProgressTargetEntity(
                    noteId = HashCode.fromLong(i.toLong()).asLong(),
                    targetId = targetId,
                    title = "Note Test Title #$i",
                    description = "Note Test description #$i",
                    checkInTime = OffsetDateTime.now().plusDays(1L)
                )
            )
        }
        return list
    }


    fun getListCurrentProgresses(targetId: Long): List<CurrentProgressEntity> {
        val list = mutableListOf<CurrentProgressEntity>()
        for (i in 1..100) {
            list.add(
                CurrentProgressEntity(
                    idUnit = i.toLong(),
                    targetId = targetId,
                    title = "current progress",
                    description = "for test",
                    checkInTime = OffsetDateTime.now().plusYears(i.toLong()),
                    level = i.toString()
                )
            )
        }
        return list
    }

    fun getListStandardProgresses(targetId: Long): List<StandardProgressEntity> {
        val list = mutableListOf<StandardProgressEntity>()
        for (i in 1..100) {
            list.add(
                StandardProgressEntity(
                    idUnit = i.toLong(),
                    targetId = targetId,
                    title = "standard progress",
                    description = "for test",
                    checkInTime = OffsetDateTime.now().plusDays(i.toLong()),
                    level = i.toString()
                )
            )
        }
        return list
    }

    fun getListOfObject(): List<ObjectOfObservationEntity> {
        val list = mutableListOf<ObjectOfObservationEntity>()
        for (i in 1..10) {
            list.add(
                ObjectOfObservationEntity(
                    id = i.toLong(),
                    firstName = "test name $i",
                    lastName = "test last name $i",
                    description = "for test",
                    observed = i != 1,
                    checkInTime = OffsetDateTime.now().plusDays(1),
                    isActive = i%2 == 0
                )
            )
        }
        return list
    }

    fun getListOfChildrenTarget(parentId: Long): List<ProgressTargetData> {
        val list = mutableListOf<ProgressTargetData>()
        for (i in 2..10) {
            list.add(
                ProgressTargetData(
                    id = i.toLong(),
                    name = "test target name$i",
                    description = "test description$i",
                    userId = 1,
                    parentTarget = parentId,
                    isGroup = false,
                    checkInTime = OffsetDateTime.now().plusDays(1),
                    isActive = true,
                    notes = getNoteOfProgressTarget(i.toLong()),
                    currentProgress = getListCurrentProgresses(1L),
                    standardProgress = getListStandardProgresses(1L)
                )
            )
        }
        return list
    }

    fun createNotesOfUser(userId: Long): List<NoteOfObjectEntity> {
        val list = mutableListOf<NoteOfObjectEntity>()
        for (i in 1..10) {
            list.add(
                NoteOfObjectEntity(
                    noteId = i.toLong(),
                    title = "Test Note",
                    description = "Test description",
                    userId = userId,
                    checkInTime = OffsetDateTime.now().plusDays(1)
                )
            )
        }
        return list
    }

    fun log(index: Long, isActive: Boolean, observed: Boolean){
        Log.d("object $index: ", "${if (isActive) "active" else "isn't active"}, ${if (observed) "observed" else "unobserved"}")
    }
}

