package com.cheesycoder.developeroptionshortcut.di

import com.cheesycoder.developeroptionshortcut.controller.DeveloperOptionController
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val controllerModule = module {
    single { DeveloperOptionController(androidApplication().contentResolver) }
}