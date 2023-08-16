package com.teamx.equiz.ui.activity.mainActivity

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseActivity
import com.teamx.equiz.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val viewModel: Class<MainViewModel>
        get() = MainViewModel::class.java

    override val layoutId: Int
        get() = R.layout.activity_main

    override val bindingVariable: Int
        get() = BR.viewModel

    lateinit var progress_bar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialising()
    }

    private fun initialising() {
        progress_bar = findViewById(R.id.progress_bar)
    }

    open fun showProgressBar() {
        progress_bar.visibility = View.VISIBLE
    }

    open fun hideProgressBar() {
        progress_bar.visibility = View.GONE
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }
}