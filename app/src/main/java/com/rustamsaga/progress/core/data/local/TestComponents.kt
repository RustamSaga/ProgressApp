package com.rustamsaga.progress.core.data.local

import com.rustamsaga.progress.core.data.local.entity.ObjectOfObservationEntity
import java.time.OffsetDateTime

object TestComponents {

    val person = ObjectOfObservationEntity(
        id = 1,
        firstName = "Test name",
        description = "Test description",
        observed = false,
        checkInTime = OffsetDateTime.now()
    )
}