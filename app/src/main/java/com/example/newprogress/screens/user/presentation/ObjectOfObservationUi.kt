package com.example.newprogress.screens.user.presentation

import com.example.newprogress.core.domain.models.NoteOfObjectModel
import com.example.newprogress.core.domain.models.ProgressTargetModel
import java.time.OffsetDateTime

data class ObjectOfObservationUi (
    val id: Int,
    val name: String,
    val description: String,
    val observed: Boolean,
    val checkInTime: OffsetDateTime,
    val targetList: List<ProgressTargetModel>,
    val noteList: List<NoteOfObjectModel>
) {

}