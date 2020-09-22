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
            loadMoreModule.apply {
                loadMoreView = CommonLoadMoreView()
                setOnLoadMoreListener {
                    mViewModel.loadMoreList(category)
                }
            }
            setOnItemClickListener { _, _, position ->
                DetailActivity.enterDetail(mActivity, data[position])
            }
            setDiffCallback(BlogDiffCallBack())
            animationEnable = true
        }

        mDataBinding.adapter = adapter
        mDataBinding.srlWeChat.apply {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener {
                mViewModel.refreshWeChatList(category)
            }
        }

        mViewModel.apply {
            blog.observe(viewLifecycleOwner, Observer {
                adapter.setNewInstance(it)
            })
            refreshStatus.observe(viewLifecycleOwner, Observer {
                mDataBinding.srlWeChat.isRefreshing = it
            })
            loadMoreStatus.observe(viewLifecycleOwner, Observer {
                when (it) {
                    LoadMoreStatus.ERROR -> adapter.loadMoreModule.loadMoreFail()
                    LoadMoreStatus.END -> adapter.loadMoreModule.loadMoreEnd()
                    LoadMoreStatus.COMPLETED -> adapter.loadMoreModule.loadMoreComplete()
                    else -> return@Observer
                }
            })
        }

        mViewModel.refreshWeChatList(category)
    }

    override fun scrollToTop() {
        mDataBinding.rvWeChat.smoothScrollToPosition(0)
    }


}