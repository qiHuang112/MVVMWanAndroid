package com.yolo.mvvmwanandroid.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yolo.mvvm.viewmodel.BaseViewModel
import com.yolo.mvvmwanandroid.network.bean.Article
import com.yolo.mvvmwanandroid.network.request.RequestManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArticleFragmentViewModel(application: Application) : BaseViewModel(application) {

    val articles: MutableLiveData<List<Article>> by lazy {
        MutableLiveData<List<Article>>()
    }

    fun getPublicTitleData() {
        viewModelScope.launch {
            kotlin.runCatching {
                withContext(Dispatchers.IO) { RequestManager.instance.getArticles() }
            }.onSuccess {
                Log.d("getPublicTitleData", "$it")
                articles.value = it.data
            }.onFailure {
                Log.e("getPublicTitleData", "$it")
            }
        }
    }
}