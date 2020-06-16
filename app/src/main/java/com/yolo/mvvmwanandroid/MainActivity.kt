package com.yolo.mvvmwanandroid

import androidx.navigation.findNavController
import com.yolo.mvvm.activity.BaseActivity
import com.yolo.mvvmwanandroid.databinding.ActivityMainBinding
import com.yolo.mvvmwanandroid.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainActivityViewModel, ActivityMainBinding>() {
    override val layoutId = R.layout.activity_main

    override fun initView() {

    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()
}