package com.rustamsaga.progress.core.data.room

import com.rustamsaga.progress.core.data.room.entity.PersonEntity
import java.time.OffsetDateTime

object TestComponents {

    val person = PersonEntity(
        id = 1,
        name = "Test name",
        observed = false,
        checkInTime = OffsetDateTime.now()
    )
}