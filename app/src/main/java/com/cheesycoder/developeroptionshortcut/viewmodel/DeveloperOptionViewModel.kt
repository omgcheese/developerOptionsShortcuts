package com.cheesycoder.developeroptionshortcut.viewmodel

import android.database.ContentObserver
import android.os.Handler
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.cheesycoder.developeroptionshortcut.controller.DontKeepActivitiesController
import com.cheesycoder.developeroptionshortcut.model.DontKeepActivitiesSource

class DeveloperOptionViewModel(
    private val dontKeepActivitiesController: DontKeepActivitiesController
) : ViewModel(), LifecycleObserver {

    val dontKeepActivityStatus: LiveData<DontKeepActivitiesSource>
        get() = _dontKeepActivityStatus

    private val _dontKeepActivityStatus = MutableLiveData<DontKeepActivitiesSource>()

    private val contentObserver = object : ContentObserver(Handler()) {
        override fun onChange(selfChange: Boolean) {
            if (!selfChange) retrieveStatusForSettings(true)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        retrieveStatusForSettings(false)
        dontKeepActivitiesController.addStatusListener(contentObserver)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        dontKeepActivitiesController.removeStatusListener(contentObserver)
    }

    fun dontKeepActivities(newValue: Boolean) {
        try {
            dontKeepActivitiesController.isSet = newValue
        } catch (exception: SecurityException) {

        } catch (exception: ClassNotFoundException) {

        }
    }

    private fun retrieveStatusForSettings(fromListener: Boolean) {
        val isStatusSet = dontKeepActivitiesController.isSet
        _dontKeepActivityStatus.value = DontKeepActivitiesSource(fromListener, isStatusSet)
    }
}