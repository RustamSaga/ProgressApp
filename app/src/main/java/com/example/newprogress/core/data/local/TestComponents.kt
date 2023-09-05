package com.example.newprogress.core.data.local

import com.example.newprogress.core.data.local.entity.ObjectOfObservationEntity
import java.time.OffsetDateTime

object TestComponents {

    val person = ObjectOfObservationEntity(
        id = 1,
        firstName = "Test name",
        lastName = "lastname",
        description = "Test description",
        observed = false,
        checkInTime = OffsetDateTime.now(),
        isActive = true
    )
}