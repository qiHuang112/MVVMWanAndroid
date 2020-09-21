package com.yolo.mvvmwanandroid.ui.fragment

import androidx.lifecycle.Observer
import com.yolo.mvvm.fragment.BaseFragment
import com.yolo.mvvmwanandroid.R
import com.yolo.mvvmwanandroid.databinding.FragmentWechatBinding
import com.yolo.mvvmwanandroid.ui.activity.DetailActivity
import com.yolo.mvvmwanandroid.ui.adapter.BlogAdapter
import com.yolo.mvvmwanandroid.ui.adapter.BlogDiffCallBack
import com.yolo.mvvmwanandroid.ui.adapter.TitleAdapter
import com.yolo.mvvmwanandroid.ui.adapter.TitleDiffCallBack
import com.yolo.mvvmwanandroid.ui.loadmore.CommonLoadMoreView
import com.yolo.mvvmwanandroid.ui.loadmore.LoadMoreStatus
import com.yolo.mvvmwanandroid.ui.widget.ScrollToTop
import com.yolo.mvvmwanandroid.viewmodel.WeChatFragmentViewModel

/**
 * 公众号
 * @author qiHuang112
 */
class WeChatFragment : BaseFragment<WeChatFragmentViewModel, FragmentWechatBinding>(),ScrollToTop {

    companion object{
        val instance = WeChatFragment()
    }

    override val layoutId = R.layout.fragment_wechat

    override fun initView() {
        val titleAdapter = TitleAdapter().apply {
            clickListener = {
                mViewModel.refreshWeChatList(it)
            }
            setDiffCallback(TitleDiffCallBack())
        }

        val projectAdapter = BlogAdapter().apply {
            loadMoreModule.apply {
                loadMoreView = CommonLoadMoreView()
                setOnLoadMoreListener {
                    mViewModel.loadMoreList()
                }
            }
            setOnItemClickListener { _, _, position ->
                DetailActivity.enterDetail(mActivity,data[position])
            }
            setDiffCallback(BlogDiffCallBack())
            animationEnable = true
        }

        mDataBinding.apply {
            this.titleAdapter = titleAdapter
            this.adapter = projectAdapter
            srlWeChat.apply {
                setColorSchemeResources(R.color.textColorPrimary)
                setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
                setOnRefreshListener {
                    mViewModel.refreshWeChatList()
                }
            }
        }
        mViewModel.apply {
            title.observe(viewLifecycleOwner, Observer {
                titleAdapter.setNewInstance(it)
            })
            blog.observe(viewLifecycleOwner, Observer {
                projectAdapter.setNewInstance(it)
            })
            currentPosition.observe(viewLifecycleOwner, Observer {
                titleAdapter.checked(position = it)
            })
            refreshStatus.observe(viewLifecycleOwner, Observer {
                mDataBinding.srlWeChat.isRefreshing = it
            })
            loadMoreStatus.observe(viewLifecycleOwner, Observer {
                when(it){
                    LoadMoreStatus.ERROR -> projectAdapter.loadMoreModule.loadMoreFail()
                    LoadMoreStatus.END -> projectAdapter.loadMoreModule.loadMoreEnd()
                    LoadMoreStatus.COMPLETED ->projectAdapter.loadMoreModule.loadMoreComplete()
                    else -> return@Observer
                }
            })

            getTitle()
        }

    }

    override fun scrollToTop() {
        mDataBinding.rvWeChat.smoothScrollToPosition(0)
    }
}