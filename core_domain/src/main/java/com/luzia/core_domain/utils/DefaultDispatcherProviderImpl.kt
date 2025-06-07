package com.luzia.core_domain.utils

import kotlinx.coroutines.Dispatchers

class DefaultDispatcherProviderImpl : CoroutineDispatcherProvider {
    override val io = Dispatchers.IO
    override val default = Dispatchers.Default
    override val main = Dispatchers.Main
}