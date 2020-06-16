package com.yolo.mvvmwanandroid

import com.yolo.mvvm.activity.BaseActivity
import com.yolo.mvvmwanandroid.databinding.ActivityMainBinding
import com.yolo.mvvmwanandroid.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainActivityViewModel, ActivityMainBinding>() {
    override val layoutId = R.layout.activity_main

    override fun initView() {
        btn_request.setOnClickListener {
            mViewModel.getPublicTitleData()
        }
    }
}