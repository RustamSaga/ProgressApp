package com.rustamsaga.progress.core.domain

interface Decomposition<T, E> {
    fun decompose(item: E): T
}