package com.yolo.mvvmwanandroid.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yolo.mvvm.viewmodel.BaseViewModel
import com.yolo.mvvmwanandroid.network.bean.BannerBean
import com.yolo.mvvmwanandroid.network.request.RequestManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * [com.yolo.mvvmwanandroid.fragment.HomeFragment]
 */
class HomeFragmentViewModel(application: Application) : BaseViewModel(application){

    val banner: MutableLiveData<List<BannerBean>> by lazy {
        MutableLiveData<List<BannerBean>>()
    }

    fun getBanner(){
        viewModelScope.launch {
            kotlin.runCatching {
                withContext(Dispatchers.IO) { RequestManager.instance.getBanner() }
            }.onSuccess {
                Log.d("getBanner", "$it")
                banner.value = it
            }.onFailure {
                Log.e("getBanner", "$it")
            }
        }
    }
}