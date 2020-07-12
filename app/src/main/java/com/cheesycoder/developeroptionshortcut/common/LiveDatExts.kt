package com.cheesycoder.developeroptionshortcut.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.cheesycoder.developeroptionshortcut.model.Event

fun <T> LiveData<T>.bind(owner: LifecycleOwner, observer: (T) -> Unit) {
    this.observe(owner, Observer { it?.let { observer.invoke(it) } })
}

fun <T> LiveData<Event<T>>.bindEvent(owner: LifecycleOwner, observer: (T) -> Unit) {
    this.observe(owner, Observer {
        it.unhandledContent?.let {  storedValue -> observer.invoke(storedValue) }
    })
}
