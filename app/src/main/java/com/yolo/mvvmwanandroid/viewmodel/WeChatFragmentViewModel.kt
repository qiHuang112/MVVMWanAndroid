package com.yolo.mvvmwanandroid.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yolo.mvvm.viewmodel.BaseViewModel
import com.yolo.mvvmwanandroid.network.bean.Blog
import com.yolo.mvvmwanandroid.network.bean.Category
import com.yolo.mvvmwanandroid.repository.WeChatRepository
import com.yolo.mvvmwanandroid.ui.loadmore.LoadMoreStatus

/**
 * [com.yolo.mvvmwanandroid.fragment.ArticleFragment]
 */
class WeChatFragmentViewModel(application: Application) : BaseViewModel(application) {
    companion object {
        const val INITIAL_PAGE = 0
    }

    val title: MutableLiveData<MutableList<Category>> = MutableLiveData()
    private val weChatRepository by lazy {
        WeChatRepository()
    }

    fun getTitle() {
        launch(
            block = {
                val titleCategory = weChatRepository.getWeChatTitle()
                title.value = titleCategory
            },
            error = {
            }
        )
    }

}