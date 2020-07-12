package com.cheesycoder.developeroptionshortcut.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.cheesycoder.developeroptionshortcut.controller.DeveloperOptionController
import com.cheesycoder.developeroptionshortcut.model.Status

class DeveloperOptionViewModel(
    private val developerOptionController: DeveloperOptionController
) : ViewModel(), LifecycleObserver {

    val dontKeepActivityStatus: LiveData<Boolean>
        get() = _dontKeepActivityStatus

    private val _dontKeepActivityStatus = MutableLiveData<Boolean>()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        retrieveStatusForSettings()
    }

    fun dontKeepActivities(newValue: Boolean) {
        try {
            developerOptionController.setDontKeepActivities(newValue)
        } catch (exception: Exception) {
            Log.e("Controlelr exception", "Enable root access")
        }
    }

    private fun retrieveStatusForSettings() {
        developerOptionController.apply {
            _dontKeepActivityStatus.value = getGlobalStatusFor(Status.DONT_KEEP_ACTIVITY) == 1
        }
    }
}