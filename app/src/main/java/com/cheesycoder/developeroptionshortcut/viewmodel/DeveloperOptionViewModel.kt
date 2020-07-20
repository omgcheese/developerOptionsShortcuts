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
import com.cheesycoder.developeroptionshortcut.model.ErrorCode
import com.cheesycoder.developeroptionshortcut.model.Event
import java.lang.Exception
import java.lang.RuntimeException
import java.lang.reflect.InvocationTargetException

class DeveloperOptionViewModel(
    private val dontKeepActivitiesController: DontKeepActivitiesController
) : ViewModel(), LifecycleObserver {

    val dontKeepActivityStatus: LiveData<DontKeepActivitiesSource>
        get() = _dontKeepActivityStatus
    val developerOptionError: LiveData<Event<ErrorCode>>
        get() = _developerOptionError

    private val _dontKeepActivityStatus = MutableLiveData<DontKeepActivitiesSource>()
    private val _developerOptionError = MutableLiveData<Event<ErrorCode>>()

    private val contentObserver = object : ContentObserver(Handler()) {
        override fun onChange(selfChange: Boolean) {
            retrieveStatusForSettings(true)
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
        var isExceptionRaised = false
        try {
            dontKeepActivitiesController.isSet = newValue
        } catch (exception: SecurityException) {
            _developerOptionError.value = ErrorCode.SETUP_REQUIRED.asEvent()
            isExceptionRaised = true
        } catch (exception: ClassNotFoundException) {
            _developerOptionError.value = ErrorCode.API_INCOMPATIBLE.asEvent()
            isExceptionRaised = true
        } catch (otherException: InvocationTargetException) {
            _developerOptionError.value = ErrorCode.SETUP_MAY_REQUIRED.asEvent()
            isExceptionRaised = true
        } finally {
            if (isExceptionRaised) {
                _dontKeepActivityStatus.value = DontKeepActivitiesSource(true, !newValue)
            }
        }
    }

    private fun retrieveStatusForSettings(fromListener: Boolean) {
        val isStatusSet = dontKeepActivitiesController.isSet
        _dontKeepActivityStatus.value = DontKeepActivitiesSource(fromListener, isStatusSet)
    }
}
