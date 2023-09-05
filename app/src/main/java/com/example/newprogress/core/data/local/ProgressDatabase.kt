package com.example.newprogress.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newprogress.core.data.local.dao.CurrentProgressDao
import com.example.newprogress.core.data.local.dao.NoteOfObjectDao
import com.example.newprogress.core.data.local.dao.NoteOfTargetDao
import com.example.newprogress.core.data.local.dao.ObjectOfObservationDao
import com.example.newprogress.core.data.local.dao.ProgressTargetDao
import com.example.newprogress.core.data.local.dao.StandardProgressDao
import com.example.newprogress.core.data.local.dao.TargetDao
import com.example.newprogress.core.data.local.entity.CurrentProgressEntity
import com.example.newprogress.core.data.local.entity.NoteOfObjectEntity
import com.example.newprogress.core.data.local.entity.NoteOfProgressTargetEntity
import com.example.newprogress.core.data.local.entity.ObjectOfObservationEntity
import com.example.newprogress.core.data.local.entity.ParamOfTargetEntity
import com.example.newprogress.core.data.local.entity.ProgressTargetEntity
import com.example.newprogress.core.data.local.entity.StandardProgressEntity
import com.example.newprogress.core.data.local.entity.TargetEntity
import com.example.newprogress.core.utils.TimeConverters


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