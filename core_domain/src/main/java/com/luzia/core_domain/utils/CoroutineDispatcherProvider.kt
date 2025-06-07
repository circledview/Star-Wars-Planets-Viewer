package com.luzia.core_domain.utils

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineDispatcherProvider {
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val main: CoroutineDispatcher
}