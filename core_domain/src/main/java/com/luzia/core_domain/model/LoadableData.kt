package com.luzia.core_domain.model

/**
 * Represents a generic container for tracking the loading state of asynchronous data.
 *
 * This sealed class models four possible states:
 * - [NotLoaded]: Initial/default state, no loading has been triggered yet.
 * - [Loading]: Indicates an ongoing loading operation.
 * - [Loaded]: Represents successfully loaded data.
 * - [Failed]: Represents a failed loading attempt, with optional title and error.
 *
 * The [data] property provides access to the loaded data if available (null otherwise).
 *
 * Usage in ViewModel/UI enables clear and reactive handling of loading, error, and success states.
 */
sealed class LoadableData<out T> {
    abstract val data: T?
}

data class Loaded<T>(override val data: T) : LoadableData<T>()
data class Failed<T>(val throwble: Throwable, val title: String? = null) : LoadableData<T>() {
    override val data: T?
        get() = null
}

object Loading : LoadableData<Nothing>() {
    override val data: Nothing?
        get() = null
}

object NotLoaded : LoadableData<Nothing>() {
    override val data: Nothing?
        get() = null
}

