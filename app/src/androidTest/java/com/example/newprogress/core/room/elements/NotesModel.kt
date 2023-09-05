package com.example.newprogress.core.room.elements

import com.google.common.hash.HashCode
import com.example.newprogress.core.domain.models.NoteOfObjectModel
import com.example.newprogress.core.domain.models.NoteOfProgressTargetModel
import java.time.OffsetDateTime

class NotesModel {

    fun createNotesOfUser(userId: Long): List<NoteOfObjectModel> {
        val list = mutableListOf<NoteOfObjectModel>()
        for (i in 1..5) {
            list.add(
                NoteOfObjectModel(
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

    fun createNoteOfProgressTarget(targetId: Long): List<NoteOfProgressTargetModel> {
        val list = mutableListOf<NoteOfProgressTargetModel>()
        for (i in 1..5) {
            list.add(
                NoteOfProgressTargetModel(
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