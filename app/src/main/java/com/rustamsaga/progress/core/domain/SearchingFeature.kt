package com.rustamsaga.progress.core.domain

interface SearchingFeature<T> {

    suspend fun searching(
        block: suspend () -> T,
        result: (T) -> Unit
    ): Result<List<T>>
}