package com.yolo.mvvmwanandroid.ui.fragment

import androidx.lifecycle.Observer
import com.yolo.mvvm.fragment.BaseFragment
import com.yolo.mvvmwanandroid.R
import com.yolo.mvvmwanandroid.databinding.FragmentProjectBinding
import com.yolo.mvvmwanandroid.ui.activity.DetailActivity
import com.yolo.mvvmwanandroid.ui.adapter.BlogAdapter
import com.yolo.mvvmwanandroid.ui.adapter.TitleAdapter
import com.yolo.mvvmwanandroid.ui.loadmore.LoadMoreStatus
import com.yolo.mvvmwanandroid.ui.widget.ScrollToTop
import com.yolo.mvvmwanandroid.viewmodel.ProjectFragmentViewModel
import kotlinx.android.synthetic.main.item_reload.view.*

/**
 * @author yolo.huang
 * @date 2020/9/10
 */
class ProjectFragment : BaseFragment<ProjectFragmentViewModel, FragmentProjectBinding>(),
    ScrollToTop {


    override val layoutId: Int = R.layout.fragment_project

    override fun initView() {
        val titleAdapter = TitleAdapter().apply {
            clickListener = {
                mViewModel.refreshProject(it)
            }
        }

        val projectAdapter = BlogAdapter().apply {
            loadMoreModule.setOnLoadMoreListener {
                    mViewModel.loadMoreProject()
            }
            setOnItemClickListener { _, _, position ->
                DetailActivity.enterDetail(mActivity, data[position])
            }
        }

        mDataBinding.apply {
            this.titleAdapter = titleAdapter
            this.adapter = projectAdapter
            this.viewModel = mViewModel
            srlProject.setOnRefreshListener {
                mViewModel.refreshProject()
            }

            reloadView.button_reload.setOnClickListener {
                getData()
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

            loadMoreStatus.observe(viewLifecycleOwner, projectAdapter)
        }

    }

    override fun scrollToTop() {
        mDataBinding.rvProject.smoothScrollToPosition(0)
    }

    override fun getData() {
        mViewModel.getTitle()
    }
}