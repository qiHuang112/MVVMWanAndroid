package com.yolo.mvvmwanandroid.ui.fragment

import androidx.lifecycle.Observer
import com.yolo.mvvm.fragment.BaseFragment
import com.yolo.mvvmwanandroid.ui.activity.DetailActivity
import com.yolo.mvvmwanandroid.R
import com.yolo.mvvmwanandroid.databinding.FragmentProjectBinding
import com.yolo.mvvmwanandroid.ui.adapter.BlogAdapter
import com.yolo.mvvmwanandroid.ui.adapter.BlogDiffCallBack
import com.yolo.mvvmwanandroid.ui.adapter.TitleAdapter
import com.yolo.mvvmwanandroid.ui.adapter.TitleDiffCallBack
import com.yolo.mvvmwanandroid.ui.loadmore.CommonLoadMoreView
import com.yolo.mvvmwanandroid.ui.loadmore.LoadMoreStatus
import com.yolo.mvvmwanandroid.ui.widget.ScrollToTop
import com.yolo.mvvmwanandroid.viewmodel.ProjectFragmentViewModel

/**
 * @author yolo.huang
 * @date 2020/9/10
 */
class ProjectFragment: BaseFragment<ProjectFragmentViewModel, FragmentProjectBinding>(),ScrollToTop {

    companion object{
        val instance =  ProjectFragment()
    }
    override val layoutId: Int
        get() = R.layout.fragment_project

    override fun initView() {
        val titleAdapter = TitleAdapter().apply {
            clickListener = {
                mViewModel.refreshProject(it)
            }
            setDiffCallback(TitleDiffCallBack())
        }

        val projectAdapter = BlogAdapter().apply {
            loadMoreModule.apply {
                loadMoreView = CommonLoadMoreView()
                setOnLoadMoreListener {
                    mViewModel.loadMoreProject()
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
            srlProject.apply {
                setColorSchemeResources(R.color.textColorPrimary)
                setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
                setOnRefreshListener {
                    mViewModel.refreshProject()
                }
            }
        }
        mViewModel.apply {
            title.observe(viewLifecycleOwner, Observer {
                titleAdapter.setNewInstance(it)
            })
            projects.observe(viewLifecycleOwner, Observer {
                projectAdapter.setNewInstance(it)
            })
            currentPosition.observe(viewLifecycleOwner, Observer {
                titleAdapter.checked(position = it)
            })
            refreshStatus.observe(viewLifecycleOwner, Observer {
                mDataBinding.srlProject.isRefreshing = it
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
        mDataBinding.rvProject.smoothScrollToPosition(0)
    }
}