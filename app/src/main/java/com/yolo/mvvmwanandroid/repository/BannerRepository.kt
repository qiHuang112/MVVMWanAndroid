package com.yolo.mvvmwanandroid.repository

import com.yolo.mvvmwanandroid.network.request.RequestManager

/**
 * @author yolo.huang
 * @date 2020/9/14
 */
class BannerRepository {

    suspend fun getBanner() = RequestManager.instance.getBanner()
}