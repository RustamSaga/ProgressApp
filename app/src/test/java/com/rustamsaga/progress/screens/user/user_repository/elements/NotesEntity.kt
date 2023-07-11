package com.rustamsaga.progress.screens.user.user_repository.elements

import com.rustamsaga.progress.core.data.local.entity.NoteOfObjectEntity
import com.rustamsaga.progress.core.data.local.entity.NoteOfProgressTargetEntity
import java.time.OffsetDateTime

object NotesEntity {

    fun createNotesOfUser(userId: Long): List<NoteOfObjectEntity> {
        val list = mutableListOf<NoteOfObjectEntity>()
        for (i in 1..5) {
            list.add(
                NoteOfObjectEntity(
                    noteId = OffsetDateTime.now().hashCode().toLong(),
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
        for (i in 1..5) {
            list.add(
                NoteOfProgressTargetEntity(
                    noteId = OffsetDateTime.now().hashCode().toLong(),
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