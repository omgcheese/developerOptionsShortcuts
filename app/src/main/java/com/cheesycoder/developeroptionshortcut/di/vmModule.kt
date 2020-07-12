package com.cheesycoder.developeroptionshortcut.di

import com.cheesycoder.developeroptionshortcut.viewmodel.DeveloperOptionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val vmModule = module {
    viewModel { DeveloperOptionViewModel(get()) }
}