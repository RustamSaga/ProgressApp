package com.example.newprogress.core.domain.mapper

interface Spreader<T>: Packer<T>, Decompose<T>

interface Packer<T> {
    suspend fun get(item: List<T>): List<T>
}

interface Decompose <T> {
    suspend fun decompose(item: T)
}
