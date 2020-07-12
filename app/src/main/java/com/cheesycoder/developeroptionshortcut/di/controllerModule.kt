package com.cheesycoder.developeroptionshortcut.di

import com.cheesycoder.developeroptionshortcut.controller.DontKeepActivitiesController
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val controllerModule = module {
    single { DontKeepActivitiesController(androidApplication().contentResolver) }
}
