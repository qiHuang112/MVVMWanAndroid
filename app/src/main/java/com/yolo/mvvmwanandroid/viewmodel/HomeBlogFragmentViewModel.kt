package com.yolo.mvvmwanandroid.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yolo.mvvm.viewmodel.BaseViewModel
import com.yolo.mvvmwanandroid.network.bean.Blog
import com.yolo.mvvmwanandroid.network.request.RequestManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author yolo.huang
 * @date 2020/7/2
 * [com.yolo.mvvmwanandroid.fragment.HomeBlogFragment]
 */
class HomeBlogFragmentViewModel(application: Application):BaseViewModel(application){

    val blogs : MutableLiveData<List<Blog>> = MutableLiveData()
    var page =0

    fun getBlogs(isReflash : Boolean){
        if(isReflash) page = 0
        viewModelScope.launch {
            kotlin.runCatching {
                withContext(Dispatchers.IO){
                    RequestManager.instance.getBlogs(page)
                }
            }.onSuccess {
                page = it.data.curPage
                blogs.value = it.data.datas
            }.onFailure {

            }
        }
    }
}