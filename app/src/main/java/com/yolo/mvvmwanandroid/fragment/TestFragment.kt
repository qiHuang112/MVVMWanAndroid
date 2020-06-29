package com.yolo.mvvmwanandroid.fragment

import com.yolo.mvvm.fragment.BaseFragment
import com.yolo.mvvmwanandroid.R
import com.yolo.mvvmwanandroid.databinding.FragmentTestBinding
import com.yolo.mvvmwanandroid.viewmodel.TestFragmentViewModel
import kotlinx.android.synthetic.main.fragment_test.*

class TestFragment : BaseFragment<TestFragmentViewModel, FragmentTestBinding>() {
    override val layoutId = R.layout.fragment_test

    override fun initView() {
        tv_test.text = arguments?.getString("title") ?: "default"
    }
}