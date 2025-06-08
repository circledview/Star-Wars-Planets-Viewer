package com.luzia.core_domain.utils

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Provides access to different CoroutineDispatchers for managing coroutine execution.
 * This interface allows for injecting dispatchers, making testing easier by allowing
 * the use of TestDispatchers.
 */
interface CoroutineDispatcherProvider {
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val main: CoroutineDispatcher
}