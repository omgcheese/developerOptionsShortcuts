package com.cheesycoder.developeroptionshortcut

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import com.cheesycoder.developeroptionshortcut.common.bind
import com.cheesycoder.developeroptionshortcut.model.Status
import com.cheesycoder.developeroptionshortcut.viewmodel.DeveloperOptionViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<DeveloperOptionViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindViewModel()
        lifecycle.addObserver(viewModel)
    }

    override fun onResume() {
        super.onResume()
        setViewListeners()
    }

    override fun onPause() {
        removeViewListeners()
        super.onPause()
    }

    private fun bindViewModel() {
        viewModel.dontKeepActivityStatus.bind(this) {
            dont_keep_activity_switch.isChecked = it
        }
    }

    private fun setViewListeners() {
        dont_keep_activity_switch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.dontKeepActivities(isChecked)
        }
    }

    private fun removeViewListeners() {
        dont_keep_activity_switch.setOnCheckedChangeListener(null)
    }
}
