package com.yolo.mvvmwanandroid.ui.fragment

import androidx.lifecycle.Observer
import com.yolo.mvvm.fragment.BaseFragment
import com.yolo.mvvmwanandroid.ui.activity.DetailActivity
import com.yolo.mvvmwanandroid.R
import com.yolo.mvvmwanandroid.databinding.FragmentPlazaBinding
import com.yolo.mvvmwanandroid.ui.adapter.BlogAdapter
import com.yolo.mvvmwanandroid.ui.adapter.BlogDiffCallBack
import com.yolo.mvvmwanandroid.ui.loadmore.CommonLoadMoreView
import com.yolo.mvvmwanandroid.ui.loadmore.LoadMoreStatus
import com.yolo.mvvmwanandroid.ui.widget.ScrollToTop
import com.yolo.mvvmwanandroid.viewmodel.PlazaFragmentViewModel
import kotlinx.android.synthetic.main.item_reload.view.*

/**
 * @author yolo.huang
 * @date 2020/9/15
 */
class PlazaFragment :BaseFragment<PlazaFragmentViewModel,FragmentPlazaBinding>(),ScrollToTop{


    override val layoutId: Int = R.layout.fragment_plaza

    override fun initView() {
        val adapter = BlogAdapter().apply {
            loadMoreModule.setOnLoadMoreListener {
                mViewModel.loadMorePlaza()
            }
            setOnItemClickListener { _, _, position ->
                DetailActivity.enterDetail(mActivity,data[position])
            }
        }
        mDataBinding.apply {
            this.adapter = adapter
            viewModel = mViewModel
            srlPlaza.setOnRefreshListener {
                    mViewModel.getPlaza()
            }
            reloadView.button_reload.setOnClickListener {
                getData()
            }
        }

        mViewModel.apply {
            loadMoreStatus.observe(viewLifecycleOwner,adapter)
            plaza.observe(viewLifecycleOwner, Observer {
                adapter.setNewInstance(it)
            })


        }
    }

    override fun scrollToTop() {
        mDataBinding.rvPlaza.smoothScrollToPosition(0)
    }

    override fun getData() {
        mViewModel.getPlaza()
    }
}