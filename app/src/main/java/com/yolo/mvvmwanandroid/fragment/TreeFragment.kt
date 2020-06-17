package com.yolo.mvvmwanandroid.fragment

import com.yolo.mvvm.fragment.BaseFragment
import com.yolo.mvvmwanandroid.R
import com.yolo.mvvmwanandroid.databinding.FragmentTreeBinding
import com.yolo.mvvmwanandroid.viewmodel.TreeFragmentViewModel

class TreeFragment : BaseFragment<TreeFragmentViewModel, FragmentTreeBinding>() {
    override val layoutId = R.layout.fragment_tree

    override fun initView() {
    }
}