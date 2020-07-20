package com.cheesycoder.developeroptionshortcut

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.cheesycoder.developeroptionshortcut.common.bind
import com.cheesycoder.developeroptionshortcut.common.bindEvent
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
            if (it.disableListener) {
                removeViewListeners()
            }
            dont_keep_activity_switch.isChecked = it.isChecked
            if (it.disableListener) {
                setViewListeners()
            }
        }
        viewModel.developerOptionError.bindEvent(this) {
            val dialogContent = it.toDialogContents()
            AlertDialog.Builder(this, R.style.AppTheme_ErrorDialog).apply {
                setTitle(dialogContent.first)
                setMessage(dialogContent.second)
                setPositiveButton(R.string.error_alert_neutral_btn, null)
            }.show()
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
