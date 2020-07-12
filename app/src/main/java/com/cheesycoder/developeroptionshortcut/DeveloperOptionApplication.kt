package com.cheesycoder.developeroptionshortcut

import android.app.Application
import com.cheesycoder.developeroptionshortcut.di.controllerModule
import com.cheesycoder.developeroptionshortcut.di.vmModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DeveloperOptionApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DeveloperOptionApplication)
            modules(vmModule, controllerModule)
        }
    }
}
