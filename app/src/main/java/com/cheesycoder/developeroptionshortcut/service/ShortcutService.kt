package com.cheesycoder.developeroptionshortcut.service

import android.database.ContentObserver
import android.os.Handler
import android.provider.Settings
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import com.cheesycoder.developeroptionshortcut.controller.DeveloperOptionController
import com.cheesycoder.developeroptionshortcut.model.Status
import org.koin.android.ext.android.inject

class ShortcutService : TileService() {

    private val developerOptionController by inject<DeveloperOptionController>()

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
        developerOptionController.addStatusListener(Settings.Global.getUriFor(Settings.Global.ALWAYS_FINISH_ACTIVITIES), contentObserver)
    }

    override fun onStopListening() {
        developerOptionController.removeStatusListener(contentObserver)
    }

    override fun onClick() {
        val currentStatus = developerOptionController.getGlobalStatusFor(Status.DONT_KEEP_ACTIVITY)
        developerOptionController.setDontKeepActivities(currentStatus != 1)
    }

    private fun updateTile() {
        val isStatusSet = developerOptionController.getGlobalStatusFor(Status.DONT_KEEP_ACTIVITY) == 1
        qsTile.state = if (isStatusSet) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        qsTile.label = if (isStatusSet) "Not Keeping Activities" else "Keeping Activities"
        qsTile.updateTile()
    }
}