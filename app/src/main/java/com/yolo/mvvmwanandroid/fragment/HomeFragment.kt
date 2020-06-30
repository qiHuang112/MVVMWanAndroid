package com.yolo.mvvmwanandroid.fragment

import com.yolo.mvvm.fragment.BaseFragment
import com.yolo.mvvmwanandroid.R
import com.yolo.mvvmwanandroid.databinding.FragmentHomeBinding
import com.yolo.mvvmwanandroid.viewmodel.HomeFragmentViewModel
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * 首页
 */
class HomeFragment : BaseFragment<HomeFragmentViewModel, FragmentHomeBinding>() {
    override val layoutId = R.layout.fragment_home

    override fun initView() {
    }
}