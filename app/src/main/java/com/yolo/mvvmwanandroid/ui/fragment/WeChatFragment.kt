package com.yolo.mvvmwanandroid.ui.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import com.yolo.mvvm.fragment.BaseFragment
import com.yolo.mvvmwanandroid.R
import com.yolo.mvvmwanandroid.databinding.FragmentWechatBinding
import com.yolo.mvvmwanandroid.network.bean.Category
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
class WeChatFragment : BaseFragment<WeChatFragmentViewModel, FragmentWechatBinding>(), ScrollToTop {

    companion object {
        val instance = WeChatFragment()
    }

    val fragments = mutableListOf<WeChatBlogFragment>()
    private val titles = mutableListOf<String>()

    override val layoutId = R.layout.fragment_wechat

    override fun initView() {




        mViewModel.apply {
            title.observe(viewLifecycleOwner, Observer { mutableList ->
                mutableList.forEach {
                    titles.add(it.name)
                    fragments.add(WeChatBlogFragment.newInstance(it))
                }

                mDataBinding.vpWechat.adapter = object : FragmentPagerAdapter(
                    childFragmentManager,
                    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
                ) {
                    override fun getItem(position: Int): Fragment {
                        return fragments[position]
                    }

                    override fun getCount(): Int {
                        return fragments.size
                    }

                    override fun getPageTitle(position: Int): CharSequence? {
                        return titles[position]
                    }

                    override fun getItemId(position: Int): Long {
                        return fragments[position].hashCode().toLong()
                    }

                }

                mDataBinding.vpWechat.offscreenPageLimit = fragments.size

                mDataBinding.tabWeChat.setupWithViewPager(mDataBinding.vpWechat)


            })

            getTitle()
        }

    }

    override fun scrollToTop() {
        if (fragments.isEmpty()) return
        fragments[mDataBinding.vpWechat.currentItem].scrollToTop()
    }
}