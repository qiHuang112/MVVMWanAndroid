package com.yolo.mvvmwanandroid.ui.fragment

import androidx.lifecycle.Observer
import com.yolo.mvvm.fragment.BaseFragment
import com.yolo.mvvmwanandroid.ui.activity.DetailActivity
import com.yolo.mvvmwanandroid.R
import com.yolo.mvvmwanandroid.databinding.FragmentAnswerBinding
import com.yolo.mvvmwanandroid.ui.adapter.BlogAdapter
import com.yolo.mvvmwanandroid.ui.adapter.BlogDiffCallBack
import com.yolo.mvvmwanandroid.ui.loadmore.CommonLoadMoreView
import com.yolo.mvvmwanandroid.ui.loadmore.LoadMoreStatus
import com.yolo.mvvmwanandroid.ui.widget.ScrollToTop
import com.yolo.mvvmwanandroid.viewmodel.AnswerFragmentViewModel
import kotlinx.android.synthetic.main.item_reload.view.*

/**
 * @author yolo.huang
 * @date 2020/9/15
 */
class AnswerFragment:BaseFragment<AnswerFragmentViewModel,FragmentAnswerBinding>(),ScrollToTop {

    override val layoutId: Int
        get() = R.layout.fragment_answer

    override fun initView() {
        val adapter = BlogAdapter().apply {
            loadMoreModule.setOnLoadMoreListener {
                mViewModel.loadMoreAnswer()
            }
            setOnItemClickListener { _, _, position ->
                DetailActivity.enterDetail(mActivity,data[position])
            }
        }
        mDataBinding.apply {
            viewModel = mViewModel
            this.adapter = adapter
            srlAnswer.setOnRefreshListener {
                    mViewModel.getAnswer()
            }
            reloadView.button_reload.setOnClickListener {
                getData()
            }
        }

        mViewModel.apply {

            loadMoreStatus.observe(viewLifecycleOwner, adapter)
            answer.observe(viewLifecycleOwner, Observer {
                adapter.setNewInstance(it)
            })
        }
    }

    override fun scrollToTop() {
        mDataBinding.rvAnswer.smoothScrollToPosition(0)
    }

    override fun getData() {
        mViewModel.getAnswer()
    }
}