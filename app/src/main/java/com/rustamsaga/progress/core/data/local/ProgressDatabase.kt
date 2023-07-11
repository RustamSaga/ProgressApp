package com.rustamsaga.progress.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rustamsaga.progress.core.utils.TimeConverters
import com.rustamsaga.progress.core.data.local.dao.*
import com.rustamsaga.progress.core.data.local.entity.*


@Database(
    entities = [
        ObjectOfObservationEntity::class,
        ProgressTargetEntity::class,
        CurrentProgressEntity::class,
        StandardProgressEntity::class,
        TargetEntity::class,
        ParamOfTargetEntity::class,
        NoteOfObjectEntity::class,
        NoteOfProgressTargetEntity::class
    ],
    version = 1
)
@TypeConverters(TimeConverters::class)
abstract class ProgressDatabase: RoomDatabase() {

    abstract fun objectOfObservationDao(): ObjectOfObservationDao
    abstract fun progressTargetDao(): ProgressTargetDao
    abstract fun currentProgressDao(): CurrentProgressDao
    abstract fun standardProgressDao(): StandardProgressDao
    abstract fun targetDao(): TargetDao
    abstract fun noteObjectOfObservationDao(): NoteOfObjectDao
    abstract fun noteOfTargetDao(): NoteOfTargetDao

}