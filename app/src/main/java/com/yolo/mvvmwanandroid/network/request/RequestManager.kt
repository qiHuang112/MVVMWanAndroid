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
    suspend fun getArticleTitles(): List<Article> {
        return NetworkApi.service.getArticleTitles().apiData()
    }

    /**
     * 获取微信公众号列表
     */
    suspend fun getArticleContents(page: Int, id: Int = 0): PageData<ArticleContent> {
        return NetworkApi.service.getArticleContents(page, id).apiData()
    }

    /**
     * 获取banner
     */
    suspend fun getBanner():List<BannerBean>{
        return NetworkApi.service.getBanner().apiData()
    }

    /**
     * 获取最新博客，参数为页数
     */
    suspend fun getBlogs(page: Int):PageData<Blog>{
        return NetworkApi.service.getBlog(page).apiData()
    }

    /**
     * 获取置顶博客
     */
    suspend fun getTopBlog():List<Blog>{
        return NetworkApi.service.getTopBlog().apiData()
    }
}