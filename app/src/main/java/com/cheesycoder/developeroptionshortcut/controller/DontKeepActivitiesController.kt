package com.cheesycoder.developeroptionshortcut.controller

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.database.ContentObserver
import android.provider.Settings
import com.cheesycoder.developeroptionshortcut.model.Status
import java.lang.reflect.InvocationTargetException

@SuppressLint("PrivateApi")
class DontKeepActivitiesController(
    private val contentResolver: ContentResolver
) {
    var isSet: Boolean
        @Throws(SecurityException::class)
        get() = Settings.Global.getInt(contentResolver, Status.DONT_KEEP_ACTIVITY.toString()) == 1
        @Throws(ClassNotFoundException::class, SecurityException::class, InvocationTargetException::class)
        set(value) {
            methodSetAlwaysFinish.invoke(getServiceInstance, value)
            Settings.Global.putInt(
                contentResolver,
                Status.DONT_KEEP_ACTIVITY.toString(),
                if (value) 1 else 0
            )
        }

    private val activityManagerClass by lazy {
        Class.forName("android.app.ActivityManager")
    }
    private val getServiceInstance by lazy {
        activityManagerClass.getMethod("getService").invoke(activityManagerClass)
    }
    private val methodSetAlwaysFinish by lazy {
        getServiceInstance.javaClass.getMethod("setAlwaysFinish", Boolean::class.javaPrimitiveType)
    }

    fun addStatusListener(observer: ContentObserver) {
        contentResolver.registerContentObserver(
            Settings.Global.getUriFor(Settings.Global.ALWAYS_FINISH_ACTIVITIES),
            false,
            observer
        )
    }

    fun removeStatusListener(observer: ContentObserver) {
        contentResolver.unregisterContentObserver(observer)
    }
}
