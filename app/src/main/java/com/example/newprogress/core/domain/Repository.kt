package com.example.newprogress.core.domain

import kotlinx.coroutines.flow.Flow

interface Repository<T> {

    interface Read<T>: Repository<T> {
        suspend fun get(id: Long): T
        fun getAll(): Flow<List<T>>
    }
    interface Save<T> {
        suspend fun set(obj: T): Boolean
        suspend fun update(obj: T): Boolean
        suspend fun delete(obj: T): Boolean
    }

    interface Mutable<T> : Read<T>, Save<T>

}

