package com.cheesycoder.developeroptionshortcut.service

import android.database.ContentObserver
import android.os.Handler
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import com.cheesycoder.developeroptionshortcut.R
import com.cheesycoder.developeroptionshortcut.controller.DontKeepActivitiesController
import org.koin.android.ext.android.inject

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
        dontKeepActivitiesController.isSet = !isStatusSet
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