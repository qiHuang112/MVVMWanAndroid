package com.yolo.mvvmwanandroid.fragment

import com.yolo.mvvm.fragment.BaseFragment
import com.yolo.mvvmwanandroid.R
import com.yolo.mvvmwanandroid.databinding.FragmentMeBinding
import com.yolo.mvvmwanandroid.viewmodel.MeFragmentViewModel

/**
 * 我的
 */
class MeFragment : BaseFragment<MeFragmentViewModel, FragmentMeBinding>() {
    override val layoutId = R.layout.fragment_me

    override fun initView() {
    }
}