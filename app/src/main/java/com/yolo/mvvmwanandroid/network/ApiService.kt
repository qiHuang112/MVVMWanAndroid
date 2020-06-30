package com.yolo.mvvmwanandroid.network

import com.yolo.mvvmwanandroid.network.response.ApiResponse
import com.yolo.mvvmwanandroid.network.bean.*
import retrofit2.http.*

interface ApiService {
    companion object {
        const val SERVER_URL = "https://wanandroid.com/"
    }

    @GET("wxarticle/chapters/json")
    suspend fun getArticleTitles(): ApiResponse<List<Article>>

    @GET("wxarticle/list/{id}/{page}/json")
    suspend fun getArticleContents(
        @Path("page") page: Int,
        @Path("id") id: Int
    ): ApiResponse<PageData<ArticleContent>>
}