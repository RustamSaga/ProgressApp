package com.rustamsaga.progress.core.data.mapper

interface Decomposition<T, E> {
    fun decompose(item: E): T
}