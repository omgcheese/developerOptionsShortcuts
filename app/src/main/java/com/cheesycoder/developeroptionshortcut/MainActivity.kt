package com.cheesycoder.developeroptionshortcut

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import com.cheesycoder.developeroptionshortcut.common.bind
import com.cheesycoder.developeroptionshortcut.viewmodel.DeveloperOptionViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<DeveloperOptionViewModel>()

    private val checkedChangeListener = CompoundButton.OnCheckedChangeListener { _, isChecked ->
        viewModel.dontKeepActivities(isChecked)
    }

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
            if (it.fromListener) {
                removeViewListeners()
            }
            dont_keep_activity_switch.isChecked = it.isChecked
            if (it.fromListener) {
                setViewListeners()
            }
        }
    }

    private fun setViewListeners() {
        dont_keep_activity_switch.setOnCheckedChangeListener(checkedChangeListener)
    }

    private fun removeViewListeners() {
        dont_keep_activity_switch.setOnCheckedChangeListener(null)
    }
}
