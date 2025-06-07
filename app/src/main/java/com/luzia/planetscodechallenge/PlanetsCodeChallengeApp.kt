package com.luzia.planetscodechallenge

import android.app.Application
import com.luzia.core_data.di.dataModule
import com.luzia.core_domain.di.domainModule
import com.luzia.feature_planet_details.di.planetDetailsModule
import com.luzia.feature_planets_list.di.planetsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class PlanetsCodeChallengeApp : Application() {

    companion object {
        val applicationDIModules = dataModule + domainModule + planetsModule + planetDetailsModule
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.INFO else Level.NONE)
            androidContext(this@PlanetsCodeChallengeApp)
            modules(applicationDIModules)
        }
    }

}
