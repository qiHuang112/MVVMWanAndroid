package com.yolo.mvvmwanandroid.network.request

import com.yolo.mvvmwanandroid.network.NetworkApi
import com.yolo.mvvmwanandroid.network.bean.*
import com.yolo.mvvmwanandroid.network.response.ApiResponse

class RequestManager private constructor() {
    companion object {
        val instance by lazy { RequestManager() }
    }

    /**
     * 获取微信公众号列表
     */
    suspend fun getArticleTitles(): ApiResponse<List<Article>> {
        return NetworkApi.service.getArticleTitles()
    }

    /**
     * 获取微信公众号列表
     */
    suspend fun getArticleContents(page: Int, id: Int = 0): ApiResponse<PageData<ArticleContent>> {
        return NetworkApi.service.getArticleContents(page, id)
    }

    /**
     * 获取banner
     */
    suspend fun getBanner():ApiResponse<List<BannerBean>>{
        return NetworkApi.service.getBanner()
    }

    suspend fun getBlogs(page: Int):ApiResponse<PageData<Blog>>{
        return NetworkApi.service.getBlog(page)
    }
}