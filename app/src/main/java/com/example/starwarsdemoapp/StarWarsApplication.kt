package com.example.starwarsdemoapp

import android.app.Application
import com.example.starwarsdemoapp.data.dataModules
import com.example.starwarsdemoapp.ui.theme.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.logger.Level

class StarWarsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        /**
         * Initializes Koin, a popular dependency injection framework in Kotlin, for the application.
         * This block of code sets up Koin with the necessary configurations and modules for dependency injection.
         */

        GlobalContext.startKoin {
            androidLogger(level = Level.INFO)
            androidContext(applicationContext)
            modules(dataModules + uiModule)
        }
    }
}