package com.cheesycoder.developeroptionshortcut.model

import com.cheesycoder.developeroptionshortcut.R

typealias Title = Int
typealias Body = Int

enum class ErrorCode {
    SETUP_REQUIRED,
    API_INCOMPATIBLE;

    fun toDialogContents(): Pair<Title, Body> {
        return when (this) {
            SETUP_REQUIRED ->
                R.string.error_code_setup_required_title to R.string.error_code_setup_required_body
            API_INCOMPATIBLE ->
                R.string.error_code_api_incompat_title to R.string.error_code_api_incompat_body
        }
    }

    fun asEvent(): Event<ErrorCode> = Event(this)
}
