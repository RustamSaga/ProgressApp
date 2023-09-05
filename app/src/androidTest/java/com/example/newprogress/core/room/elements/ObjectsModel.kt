package com.example.newprogress.core.room.elements

import android.util.Log
import com.example.newprogress.core.domain.models.ObjectOfObservationModel
import java.time.OffsetDateTime

class ObjectsModel {

    val obj: ObjectOfObservationModel =
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