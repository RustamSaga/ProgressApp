package com.rustamsaga.progress.core.room.elements

import com.google.common.hash.HashCode
import com.rustamsaga.progress.core.data.local.entity.NoteOfObjectEntity
import com.rustamsaga.progress.core.data.local.entity.NoteOfProgressTargetEntity
import java.time.OffsetDateTime

object Notes {

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

    fun createNoteOfProgressTarget(targetId: Long): List<NoteOfProgressTargetEntity> {
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

}