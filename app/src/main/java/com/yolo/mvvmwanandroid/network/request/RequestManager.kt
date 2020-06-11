package com.yolo.mvvmwanandroid.network.request

import com.yolo.mvvmwanandroid.network.NetworkApi
import com.yolo.mvvmwanandroid.network.bean.Article
import com.yolo.mvvmwanandroid.network.response.ApiResponse

class RequestManager private constructor() {
    companion object {
        val instance by lazy { RequestManager() }
    }

    /**
     * 获取微信公众号列表
     */
    suspend fun getArticles(): ApiResponse<ArrayList<Article>> {
        return NetworkApi.service.getArticles()
    }
}