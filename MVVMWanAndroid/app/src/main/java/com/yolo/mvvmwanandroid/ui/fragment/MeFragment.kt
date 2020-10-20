package com.yolo.mvvmwanandroid.ui.fragment

import com.yolo.mvvm.fragment.BaseFragment
import com.yolo.mvvmwanandroid.R
import com.yolo.mvvmwanandroid.databinding.FragmentMeBinding
import com.yolo.mvvmwanandroid.viewmodel.MeFragmentViewModel

/**
 * 我的
 */
class MeFragment : BaseFragment<MeFragmentViewModel, FragmentMeBinding>() {

    companion object{
        val instance = MeFragment()
    }

    override val layoutId = R.layout.fragment_me

    override fun initView() {
    }

    override fun getData() {

    }
}