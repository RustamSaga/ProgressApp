package com.example.newprogress.core.domain

interface Decomposition<T, E> {
    fun decompose(item: E): T
}