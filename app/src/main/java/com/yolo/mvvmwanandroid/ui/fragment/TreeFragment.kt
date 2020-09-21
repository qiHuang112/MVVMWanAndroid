package com.yolo.mvvmwanandroid.ui.fragment

import com.yolo.mvvm.fragment.BaseFragment
import com.yolo.mvvmwanandroid.R
import com.yolo.mvvmwanandroid.databinding.FragmentTreeBinding
import com.yolo.mvvmwanandroid.viewmodel.TreeFragmentViewModel

/**
 *知识体系
 */
class TreeFragment : BaseFragment<TreeFragmentViewModel, FragmentTreeBinding>() {

    companion object{
        val instance = TreeFragment()
    }

    override val layoutId = R.layout.fragment_tree

    override fun initView() {
    }
}