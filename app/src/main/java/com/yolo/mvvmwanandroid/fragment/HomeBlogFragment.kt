package com.yolo.mvvmwanandroid.fragment

import androidx.lifecycle.Observer
import com.yolo.mvvm.fragment.BaseFragment
import com.yolo.mvvmwanandroid.R
import com.yolo.mvvmwanandroid.adapter.ArticleContentAdapter
import com.yolo.mvvmwanandroid.adapter.BlogAdapter
import com.yolo.mvvmwanandroid.adapter.BlogDiff
import com.yolo.mvvmwanandroid.databinding.FragmentHomeBlogBinding
import com.yolo.mvvmwanandroid.viewmodel.HomeBlogFragmentViewModel

/**
 * @author yolo.huang
 * @date 2020/7/2
 */
class HomeBlogFragment:BaseFragment<HomeBlogFragmentViewModel, FragmentHomeBlogBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_home_blog

    override fun initView() {
        val adapter = BlogAdapter(BlogDiff())
        mDataBinding.rvHomeBlog.adapter = adapter
        mViewModel.blogs.observe(this,Observer{
            adapter.submitList(it)
        })
        mViewModel.getBlogs(true)
    }


}