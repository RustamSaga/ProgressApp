package com.rustamsaga.progress.screens.user.user_repository.elements

import com.rustamsaga.progress.core.domain.models.NoteOfObjectModel
import com.rustamsaga.progress.core.domain.models.NoteOfProgressTargetModel
import java.time.OffsetDateTime

object NotesModel {

    fun createNotesOfUser(userId: Long): List<NoteOfObjectModel> {
        val list = mutableListOf<NoteOfObjectModel>()
        for (i in 1..5) {
            val noteId = OffsetDateTime.now().hashCode().toLong() + i
            list.add(
                NoteOfObjectModel(
                    noteId = noteId,
                    title = "Test Note",
                    description = "Test description",
                    userId = userId,
                    checkInTime = OffsetDateTime.now().plusDays(1)
                )
            )
        }
        return list
    }

    fun createNoteOfProgressTarget(targetId: Long, targetName: String): List<NoteOfProgressTargetModel> {
        val list = mutableListOf<NoteOfProgressTargetModel>()
        for (i in 1..5) {
            list.add(
                NoteOfProgressTargetModel(
                    noteId = OffsetDateTime.now().hashCode().toLong(),
                    targetId = targetId,
                    title = "Note Test for $targetName",
                    description = "Note Test description for $targetName",
                    checkInTime = OffsetDateTime.now().plusDays(1L)
                )
            )
        }
        return list
    }


}