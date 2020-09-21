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
    suspend fun getWeChatTitle(): MutableList<Category> {
        return NetworkApi.service.getWeChatTitle().apiData()
    }

    /**
     * 获取微信公众号列表
     */
    suspend fun getWeChatBlog(page: Int, id: Int = 0): PageData<Blog> {
        return NetworkApi.service.getWeChatBlog(page, id).apiData()
    }

    /**
     * 获取banner
     */
    suspend fun getBanner():MutableList<BannerBean>{
        return NetworkApi.service.getBanner().apiData()
    }

    /**
     * 获取最新博客，参数为页数
     */
    suspend fun getBlog(page: Int):PageData<Blog>{
        return NetworkApi.service.getBlog(page).apiData()
    }

    /**
     * 获取置顶博客
     */
    suspend fun getTopBlog():MutableList<Blog>{
        return NetworkApi.service.getTopBlog().apiData()
    }

    /**
     * 获取项目分类
     */
    suspend fun getProjectTitle():MutableList<Category>{
        return NetworkApi.service.getProjectTitle().apiData()
    }

    /**
     * 获取最新项目
     */
    suspend fun getTopProject(page:Int):PageData<Blog>{
        return NetworkApi.service.getTopProject(page).apiData()
    }

    /**
     * 获取分类下项目信息
     */
    suspend fun getProject(page: Int,cid:Int):PageData<Blog>{
        return NetworkApi.service.getProject(page,cid).apiData()
    }

    /**
     * 获取广场列表
     */
    suspend fun getPlaza(page: Int):PageData<Blog>{
        return NetworkApi.service.getPlaza(page).apiData()
    }

    /**
     * 获取问答列表
     */
    suspend fun getAnswer(page: Int):PageData<Blog>{
        return NetworkApi.service.getAnswer(page).apiData()
    }


}