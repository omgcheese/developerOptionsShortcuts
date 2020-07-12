package com.cheesycoder.developeroptionshortcut.controller

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.database.ContentObserver
import android.net.Uri
import android.provider.Settings
import com.cheesycoder.developeroptionshortcut.model.Status

@SuppressLint("PrivateApi")
class DeveloperOptionController(
    private val contentResolver: ContentResolver
) {
    private val activityManagerClass by lazy {
        Class.forName("android.app.ActivityManager")
    }
    private val getServiceInstance by lazy {
        activityManagerClass.getMethod("getService").invoke(activityManagerClass)
    }
    private val methodSetAlwaysFinish by lazy {
        getServiceInstance.javaClass.getMethod("setAlwaysFinish", Boolean::class.javaPrimitiveType)
    }

    /**
     * Get global setting status for passed parameter type
     *
     * @param status attribute to lookup
     * @return 0 for false and 1 for true
     * @see [Settings.Global]
     */
    fun getGlobalStatusFor(status: Status): Int {
        return Settings.Global.getInt(contentResolver, status.toString())
    }

    @Throws(ClassNotFoundException::class)
    fun setDontKeepActivities(newValue: Boolean) {
        methodSetAlwaysFinish.invoke(getServiceInstance, newValue)
        setGlobalStatusFor(Status.DONT_KEEP_ACTIVITY, newValue)
    }

    @Throws(SecurityException::class)
    fun setGlobalStatusFor(status: Status, newValue: Boolean) {
        val previousValue = getGlobalStatusFor(status) == 1
        if (previousValue != newValue) {
            Settings.Global.putInt(contentResolver, status.toString(), if (newValue) 1 else 0)
        }
    }

    fun addStatusListener(statusUri: Uri, observer: ContentObserver) {
        contentResolver.registerContentObserver(statusUri, false, observer)
    }

    fun removeStatusListener(observer: ContentObserver) {
        contentResolver.unregisterContentObserver(observer)
    }
}