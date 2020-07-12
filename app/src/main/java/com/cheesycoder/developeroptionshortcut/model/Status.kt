package com.cheesycoder.developeroptionshortcut.model

import android.provider.Settings

enum class Status {
    DONT_KEEP_ACTIVITY;

    override fun toString(): String {
        return when (this) {
            DONT_KEEP_ACTIVITY -> Settings.Global.ALWAYS_FINISH_ACTIVITIES
        }
    }
}
