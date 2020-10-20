package com.yolo.mvvmwanandroid.viewmodel

import android.app.Application
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import com.yolo.mvvmwanandroid.network.bean.BannerBean
import com.yolo.mvvmwanandroid.repository.BannerRepository

/**
 * [com.yolo.mvvmwanandroid.fragment.HomeFragment]
 */
class HomeFragmentViewModel(application: Application) : BaseBlogViewModel(application){

    private val bannerRepository by lazy { BannerRepository() }

    val banner: MutableLiveData<List<BannerBean>> = MutableLiveData()

    fun getBanner(){
        reloadStatus.set(false)
        launch(
            block = {
                banner.value = bannerRepository.getBanner()
                reloadStatus.set(false)
            },
            error = {
                reloadStatus.set(true)
            }
        )
    }

}