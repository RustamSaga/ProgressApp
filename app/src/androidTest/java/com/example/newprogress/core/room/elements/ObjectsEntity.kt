package com.rustamsaga.progress.core.room.elements

import android.util.Log
import com.example.newprogress.core.data.local.entity.ObjectOfObservationEntity
import java.time.OffsetDateTime

object ObjectsEntity {

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

    fun getListOfObject(): List<ObjectOfObservationEntity> {
        val list = mutableListOf<ObjectOfObservationEntity>()
        for (i in 1..5) {
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

    fun log(index: Long, isActive: Boolean, observed: Boolean){
        Log.d("object $index: ", "${if (isActive) "active" else "isn't active"}, ${if (observed) "observed" else "unobserved"}")
    }
}