package com.cheesycoder.developeroptionshortcut.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.bind(owner: LifecycleOwner, observer: (T) -> Unit) {
    this.observe(owner, Observer { it?.let { observer.invoke(it) } })
}
