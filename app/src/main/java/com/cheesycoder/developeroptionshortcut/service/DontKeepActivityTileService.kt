package com.cheesycoder.developeroptionshortcut.service

import android.database.ContentObserver
import android.os.Handler
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.widget.Toast
import com.cheesycoder.developeroptionshortcut.R
import com.cheesycoder.developeroptionshortcut.controller.DontKeepActivitiesController
import com.cheesycoder.developeroptionshortcut.model.ErrorCode
import org.koin.android.ext.android.inject
import java.lang.reflect.InvocationTargetException

class DontKeepActivityTileService : TileService() {

    private val dontKeepActivitiesController by inject<DontKeepActivitiesController>()

    private val contentObserver = object : ContentObserver(Handler()) {
        override fun onChange(selfChange: Boolean) {
            updateTile()
        }
    }

    override fun onTileAdded() {
        updateTile()
    }

    override fun onStartListening() {
        updateTile()
        dontKeepActivitiesController.addStatusListener(contentObserver)
    }

    override fun onStopListening() {
        dontKeepActivitiesController.removeStatusListener(contentObserver)
    }

    override fun onClick() {
        val isStatusSet = dontKeepActivitiesController.isSet
        val errorResId = try {
            dontKeepActivitiesController.isSet = !isStatusSet
            null
        } catch (exception: SecurityException) {
            ErrorCode.SETUP_REQUIRED.toToastMessageContent()
        } catch (exception: ClassNotFoundException) {
            ErrorCode.API_INCOMPATIBLE.toToastMessageContent()
        } catch (otherException: InvocationTargetException) {
            ErrorCode.SETUP_MAY_REQUIRED.toToastMessageContent()
        }
        errorResId?.let { Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show() }
    }

    private fun updateTile() {
        val isStatusSet = dontKeepActivitiesController.isSet
        qsTile.state = if (isStatusSet) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        val tileString =
            if (isStatusSet) R.string.dont_keep_activities_tile
            else R.string.keep_activities_tile
        qsTile.label = getString(tileString)
        qsTile.updateTile()
    }
}
