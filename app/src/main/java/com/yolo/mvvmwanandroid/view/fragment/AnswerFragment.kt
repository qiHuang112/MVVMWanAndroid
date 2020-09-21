package com.yolo.mvvmwanandroid.view.fragment

import androidx.lifecycle.Observer
import com.yolo.mvvm.fragment.BaseFragment
import com.yolo.mvvmwanandroid.DetailActivity
import com.yolo.mvvmwanandroid.R
import com.yolo.mvvmwanandroid.databinding.FragmentAnswerBinding
import com.yolo.mvvmwanandroid.view.adapter.BlogAdapter
import com.yolo.mvvmwanandroid.view.adapter.BlogDiffCallBack
import com.yolo.mvvmwanandroid.view.loadmore.CommonLoadMoreView
import com.yolo.mvvmwanandroid.view.loadmore.LoadMoreStatus
import com.yolo.mvvmwanandroid.viewmodel.AnswerFragmentViewModel

/**
 * @author yolo.huang
 * @date 2020/9/15
 */
class AnswerFragment:BaseFragment<AnswerFragmentViewModel,FragmentAnswerBinding>() {

    companion object{
        val instance = AnswerFragment()
    }

    override val layoutId: Int
        get() = R.layout.fragment_answer

    override fun initView() {
        val adapter = BlogAdapter().apply {
            loadMoreModule.loadMoreView = CommonLoadMoreView()
            loadMoreModule.setOnLoadMoreListener {
                mViewModel.loadMoreAnswer()
            }
            setOnItemClickListener { _, _, position ->
                DetailActivity.enterDetail(mActivity,data[position])
            }
            animationEnable = true
            setDiffCallback(BlogDiffCallBack())
        }
        mDataBinding.adapter = adapter
        mDataBinding.srlAnswer.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener {
                mViewModel.getAnswer()
            }
        }

        mViewModel.apply {
            refreshStatus.observe(viewLifecycleOwner, Observer {
                mDataBinding.srlAnswer.isRefreshing = it
            })
            loadMoreStatus.observe(viewLifecycleOwner, Observer {
                when(it){
                    LoadMoreStatus.ERROR -> adapter.loadMoreModule.loadMoreFail()
                    LoadMoreStatus.END -> adapter.loadMoreModule.loadMoreEnd()
                    LoadMoreStatus.COMPLETED ->adapter.loadMoreModule.loadMoreComplete()
                    else -> return@Observer
                }
            })

            answer.observe(viewLifecycleOwner, Observer {
                adapter.setNewInstance(it)
            })

            getAnswer()
        }
    }
}