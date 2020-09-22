package com.yolo.mvvmwanandroid.ui.fragment

import androidx.lifecycle.Observer
import com.yolo.mvvm.fragment.BaseFragment
import com.yolo.mvvmwanandroid.ui.activity.DetailActivity
import com.yolo.mvvmwanandroid.R
import com.yolo.mvvmwanandroid.databinding.FragmentHomeBlogBinding
import com.yolo.mvvmwanandroid.ui.adapter.BlogAdapter
import com.yolo.mvvmwanandroid.ui.adapter.BlogDiffCallBack
import com.yolo.mvvmwanandroid.ui.loadmore.CommonLoadMoreView
import com.yolo.mvvmwanandroid.ui.loadmore.LoadMoreStatus
import com.yolo.mvvmwanandroid.ui.widget.ScrollToTop
import com.yolo.mvvmwanandroid.viewmodel.HomeBlogFragmentViewModel
import kotlinx.android.synthetic.main.item_reload.view.*

/**
 * @author yolo.huang
 * @date 2020/7/2
 */
class HomeBlogFragment:BaseFragment<HomeBlogFragmentViewModel, FragmentHomeBlogBinding>(),ScrollToTop {

    override val layoutId: Int
        get() = R.layout.fragment_home_blog

    override fun initView() {
        val adapter = BlogAdapter().apply {
            loadMoreModule.setOnLoadMoreListener {
                mViewModel.getMoreBlog()
            }
            setOnItemClickListener { _, _, position ->
                DetailActivity.enterDetail(mActivity,data[position])
            }
        }
        mDataBinding.apply {
            srlBlog.setOnRefreshListener {
                    mViewModel.getBlog()
            }
            reloadView.button_reload.setOnClickListener {
                getData()
            }
            this.adapter = adapter
            viewModel = mViewModel
        }
        mViewModel.apply {
            blog.observe(viewLifecycleOwner,Observer{
                adapter.setNewInstance(it)
            })
            loadMoreStatus.observe(viewLifecycleOwner, adapter)
        }

    }


    override fun getData() {
        mViewModel.getBlog()
    }

    override fun scrollToTop() {
        mDataBinding.rvHomeBlog.smoothScrollToPosition(0)

    }

}