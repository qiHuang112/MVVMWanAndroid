package com.yolo.mvvmwanandroid.viewmodel

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.yolo.mvvm.viewmodel.BaseViewModel
import com.yolo.mvvmwanandroid.ui.loadmore.LoadMoreStatus

/**
 * @author yolo.huang
 * @date 2020/9/22
 */
abstract class BaseBlogViewModel(application: Application) :BaseViewModel(application){

    val refreshStatus: ObservableBoolean = ObservableBoolean()
    val loadMoreStatus: MutableLiveData<LoadMoreStatus> = MutableLiveData()
    val reloadStatus:ObservableBoolean = ObservableBoolean()


}