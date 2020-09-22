package com.yolo.mvvmwanandroid.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yolo.mvvm.viewmodel.BaseViewModel
import com.yolo.mvvmwanandroid.network.bean.Blog
import com.yolo.mvvmwanandroid.network.bean.Category
import com.yolo.mvvmwanandroid.repository.WeChatRepository
import com.yolo.mvvmwanandroid.ui.loadmore.LoadMoreStatus

class WeChatBlogViewModel(application: Application) : BaseViewModel(application) {

    companion object {
        const val INITIAL_PAGE = 0
        const val INITIAL_POSITION = 0
    }

    private val weChatRepository by lazy {
        WeChatRepository()
    }
    val refreshStatus: MutableLiveData<Boolean> = MutableLiveData()
    val loadMoreStatus: MutableLiveData<LoadMoreStatus> = MutableLiveData()

    val title: MutableLiveData<MutableList<Category>> = MutableLiveData()
    val blog: MutableLiveData<MutableList<Blog>> = MutableLiveData()

    private var page = INITIAL_PAGE


    fun refreshWeChatList(category: Category) {
        refreshStatus.value = true
        launch(
            block = {
                val id = category.id
                val pagination =
                    weChatRepository.getWeChatBlog(WeChatFragmentViewModel.INITIAL_PAGE, id)
                page = pagination.curPage
                blog.value = pagination.datas.toMutableList()

                refreshStatus.value = false

            },
            error = {
                refreshStatus.value = false
            }
        )
    }

    fun loadMoreList(category: Category) {
        loadMoreStatus.value = LoadMoreStatus.LOADING
        launch(
            block = {
                val cid = category.id

                val project = weChatRepository.getWeChatBlog(page, cid)
                blog.value?.addAll(project.datas)
                page = project.curPage
                loadMoreStatus.value = if (project.offset >= project.total) {
                    LoadMoreStatus.END
                } else {
                    LoadMoreStatus.COMPLETED
                }
            },
            error = {
                loadMoreStatus.value = LoadMoreStatus.ERROR
            }
        )
    }

}