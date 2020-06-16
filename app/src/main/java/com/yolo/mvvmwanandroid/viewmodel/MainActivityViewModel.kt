package com.yolo.mvvmwanandroid.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.yolo.mvvm.viewmodel.BaseViewModel
import com.yolo.mvvmwanandroid.network.request.RequestManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel(application: Application) : BaseViewModel(application) {

    fun getPublicTitleData() {
        viewModelScope.launch {
            kotlin.runCatching {
                withContext(Dispatchers.IO) { RequestManager.instance.getArticles() }
            }.onSuccess {
                Log.d("getPublicTitleData", "$it")
            }.onFailure {
                Log.e("getPublicTitleData", "$it")
            }
        }
    }
}