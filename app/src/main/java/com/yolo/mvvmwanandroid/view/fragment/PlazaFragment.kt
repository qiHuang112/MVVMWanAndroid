package com.yolo.mvvmwanandroid.view.fragment

import androidx.lifecycle.Observer
import com.yolo.mvvm.fragment.BaseFragment
import com.yolo.mvvmwanandroid.R
import com.yolo.mvvmwanandroid.databinding.FragmentPlazaBinding
import com.yolo.mvvmwanandroid.view.adapter.BlogAdapter
import com.yolo.mvvmwanandroid.view.adapter.BlogDiffCallBack
import com.yolo.mvvmwanandroid.view.loadmore.CommonLoadMoreView
import com.yolo.mvvmwanandroid.view.loadmore.LoadMoreStatus
import com.yolo.mvvmwanandroid.viewmodel.PlazaFragmentViewModel

/**
 * @author yolo.huang
 * @date 2020/9/15
 */
class PlazaFragment :BaseFragment<PlazaFragmentViewModel,FragmentPlazaBinding>(){

    companion object{
        val instance =  PlazaFragment()
    }

    override val layoutId: Int
        get() = R.layout.fragment_plaza

    override fun initView() {
        val adapter = BlogAdapter().apply {
            loadMoreModule.loadMoreView = CommonLoadMoreView()
            loadMoreModule.setOnLoadMoreListener {
                mViewModel.loadMorePlaza()
            }
            setDiffCallback(BlogDiffCallBack())
            animationEnable = true

        }
        mDataBinding.adapter = adapter

        mDataBinding.srlPlaza.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener {
                mViewModel.getPlaza()
            }
        }

        mViewModel.apply {
            refreshStatus.observe(viewLifecycleOwner, Observer {
                mDataBinding.srlPlaza.isRefreshing = it
            })
            loadMoreStatus.observe(viewLifecycleOwner, Observer {
                when(it){
                    LoadMoreStatus.ERROR -> adapter.loadMoreModule.loadMoreFail()
                    LoadMoreStatus.END -> adapter.loadMoreModule.loadMoreEnd()
                    LoadMoreStatus.COMPLETED ->adapter.loadMoreModule.loadMoreComplete()
                    else -> return@Observer
                }
            })

            plaza.observe(viewLifecycleOwner, Observer {
                adapter.setNewInstance(it)
            })

            getPlaza()
        }
    }
}