package com.cheesycoder.developeroptionshortcut.model

data class Event<T>(
    private val content: T
) {
    var hasBeenHandled = false
        private set

    val unhandledContent: T?
        get() {
            return if (hasBeenHandled) {
                null
            } else {
                hasBeenHandled = true
                content
            }
        }
}