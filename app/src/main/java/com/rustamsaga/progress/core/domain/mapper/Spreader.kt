package com.rustamsaga.progress.core.domain.mapper

interface Spreader<T, L>: Packer<T>, Decompose<L>

interface Packer<T> {
    suspend fun get(item: List<T>): List<T>
}

interface Decompose <T> {
    suspend fun decompose(item: T)
}
