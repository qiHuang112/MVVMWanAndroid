package com.yolo.mvvmwanandroid.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yolo.mvvm.viewmodel.BaseViewModel
import com.yolo.mvvmwanandroid.network.bean.ArticleContent
import com.yolo.mvvmwanandroid.network.request.RequestManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * [com.yolo.mvvmwanandroid.fragment.ArticleContentFragment]
 */
class ArticleContentFragmentViewModel(application: Application) : BaseViewModel(application) {

    var page = 1

    var data: MutableLiveData<List<ArticleContent>> = MutableLiveData()

    fun getArticleContents(isRefresh: Boolean, id: Int) {
        if (isRefresh) page = 1

        viewModelScope.launch {
            kotlin.runCatching {
                withContext(Dispatchers.IO) { RequestManager.instance.getArticleContents(page, id) }
            }.onSuccess {
                page++
                data.value = it.datas
                Log.d("getArticleContents", "$it")
            }.onFailure {
                Log.e("getArticleContents", "$it")
            }
        }
    }
}