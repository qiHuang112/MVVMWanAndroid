package com.yolo.mvvmwanandroid.ui.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import com.yolo.mvvm.fragment.BaseFragment
import com.yolo.mvvmwanandroid.R
import com.yolo.mvvmwanandroid.databinding.FragmentWechatBlogBinding
import com.yolo.mvvmwanandroid.network.bean.Category
import com.yolo.mvvmwanandroid.ui.activity.DetailActivity
import com.yolo.mvvmwanandroid.ui.adapter.BlogAdapter
import com.yolo.mvvmwanandroid.ui.adapter.BlogDiffCallBack
import com.yolo.mvvmwanandroid.ui.loadmore.CommonLoadMoreView
import com.yolo.mvvmwanandroid.ui.loadmore.LoadMoreStatus
import com.yolo.mvvmwanandroid.ui.widget.ScrollToTop
import com.yolo.mvvmwanandroid.viewmodel.WeChatBlogViewModel
import kotlinx.android.synthetic.main.item_reload.view.*

class WeChatBlogFragment : BaseFragment<WeChatBlogViewModel, FragmentWechatBlogBinding>(),
    ScrollToTop {

    companion object {
        private const val CATEGORY = "CATEGORY"

        fun newInstance(category: Category) = WeChatBlogFragment().apply {
            arguments = Bundle().apply {
                putParcelable(CATEGORY, category)
            }
        }
    }

    lateinit var category: Category

    override val layoutId: Int
        get() = R.layout.fragment_wechat_blog

    override fun initView() {
        arguments?.apply {
            category = getParcelable<Category>(CATEGORY) ?: return
        }
        val adapter = BlogAdapter().apply {
            loadMoreModule.setOnLoadMoreListener {
                    mViewModel.loadMoreList(category)
                }
            setOnItemClickListener { _, _, position ->
                DetailActivity.enterDetail(mActivity, data[position])
            }
        }
        mDataBinding.apply {
            this.adapter = adapter
            viewModel = mViewModel
            srlWeChat.setOnRefreshListener {
                mViewModel.refreshWeChatList(category)
            }
            reloadView.button_reload.setOnClickListener {
                getData()
            }
        }
        mViewModel.apply {
            blog.observe(viewLifecycleOwner, Observer {
                adapter.setNewInstance(it)
            })
            loadMoreStatus.observe(viewLifecycleOwner,adapter)
        }


    }

    override fun scrollToTop() {
        mDataBinding.rvWeChat.smoothScrollToPosition(0)
    }

    override fun getData() {
        mViewModel.refreshWeChatList(category)
    }


}