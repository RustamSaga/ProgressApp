package com.rustamsaga.progress.core.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rustamsaga.progress.core.TimeConverters
import com.rustamsaga.progress.core.data.room.dao.CurrentProgressDao
import com.rustamsaga.progress.core.data.room.dao.PersonDao
import com.rustamsaga.progress.core.data.room.dao.StandardProgressDao
import com.rustamsaga.progress.core.data.room.dao.TargetDao
import com.rustamsaga.progress.core.data.room.entity.CurrentProgressEntity
import com.rustamsaga.progress.core.data.room.entity.PersonEntity
import com.rustamsaga.progress.core.data.room.entity.StandardProgressEntity
import com.rustamsaga.progress.core.data.room.entity.TargetEntity


@Database(
    entities = [
        PersonEntity::class,
        TargetEntity::class,
        CurrentProgressEntity::class,
        StandardProgressEntity::class
    ],
    version = 1
)
@TypeConverters(TimeConverters::class)
abstract class ProgressDatabase: RoomDatabase() {

    abstract fun personDao(): PersonDao
    abstract fun targetDao(): TargetDao
    abstract fun currentProgressDao(): CurrentProgressDao
    abstract fun standardProgressDao(): StandardProgressDao

}