package com.yolo.mvvmwanandroid.network

import com.yolo.mvvmwanandroid.network.response.ApiResponse
import com.yolo.mvvmwanandroid.network.bean.Article
import retrofit2.http.GET

interface ApiService {
    companion object {
        const val SERVER_URL = "https://wanandroid.com/"
    }

    @GET("wxarticle/chapters/json")
    suspend fun getArticles(): ApiResponse<ArrayList<Article>>
}