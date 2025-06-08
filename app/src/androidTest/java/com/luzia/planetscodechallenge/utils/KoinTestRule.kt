package com.luzia.planetscodechallenge.utils

import androidx.test.platform.app.InstrumentationRegistry
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.getKoinApplicationOrNull
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.unloadKoinModules
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module

/**
 * A JUnit TestRule for managing Koin modules in Android instrumentation tests.
 *
 * This rule ensures that Koin is started with the specified modules before each test
 * and that the modules are unloaded after each test.
 *
 * If Koin is not already started, it will be initialized with the provided modules and
 * the application context from the instrumentation registry.
 * If Koin is already running, the provided modules will be loaded.
 *
 * @param modules The list of Koin modules to be loaded for the test.
 */
class KoinTestRule(
    private val modules: List<Module>
) : TestWatcher() {
    override fun starting(description: Description) {

        if (getKoinApplicationOrNull() == null) {
            startKoin {
                androidContext(InstrumentationRegistry.getInstrumentation().targetContext.applicationContext)
                modules(modules)
            }
        } else {
            loadKoinModules(modules)
        }
    }

    override fun finished(description: Description) {
        unloadKoinModules(modules)
    }
}