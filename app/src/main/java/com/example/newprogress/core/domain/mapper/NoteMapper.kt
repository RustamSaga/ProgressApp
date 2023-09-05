package com.example.newprogress.core.domain.mapper

import java.time.OffsetDateTime

interface NoteMapper<T> {
    fun map(
        noteId: Long,
        title: String,
        description: String,
        parentId: Long,
        checkInTime: OffsetDateTime,
    ): T
}