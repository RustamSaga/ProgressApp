package com.rustamsaga.progress.screens.user.user_repository.elements

import android.util.Log
import com.rustamsaga.progress.core.domain.models.ObjectOfObservationModel
import java.time.OffsetDateTime

object ObjectsModel {

    val user: ObjectOfObservationModel =
        ObjectOfObservationModel(
            id = 1,
            firstName = "Tolib",
            lastName = "TestLastName",
            description = "for test",
            observed = false,
            checkInTime = OffsetDateTime.now(),
            isActive = true,
            targets = emptyList(),
            notes = emptyList()
        )

    val userByDetails:  ObjectOfObservationModel =
        ObjectOfObservationModel(
            id = 1,
            firstName = "Tolib",
            lastName = "TestLastName",
            description = "for test",
            observed = false,
            checkInTime = OffsetDateTime.now(),
            isActive = true,
            targets = TargetsModel.getTargets(1),
            notes = NotesModel.createNotesOfUser(1)
        )

    fun getListOfObject(): List<ObjectOfObservationModel> {
        val list = mutableListOf<ObjectOfObservationModel>()
        for (i in 1..5) {
            list.add(
                ObjectOfObservationModel(
                    id = i.toLong(),
                    firstName = "test name $i",
                    lastName = "test last name $i",
                    description = "for test",
                    observed = i != 1,
                    checkInTime = OffsetDateTime.now().plusDays(1),
                    isActive = i%2 == 0,
                    targets = emptyList(),
                    notes = emptyList()
                )
            )
        }
        return list
    }

    fun log(index: Long, isActive: Boolean, observed: Boolean){
        Log.d("object $index: ", "${if (isActive) "active" else "isn't active"}, ${if (observed) "observed" else "unobserved"}")
    }
}