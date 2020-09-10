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

    @GET("banner/json")
    suspend fun getBanner():ApiResponse<List<BannerBean>>

    @GET("article/list/{page}/json")
    suspend fun getBlog(@Path("page") page: Int):ApiResponse<PageData<Blog>>

    @GET("article/top/json")
    suspend fun getTopBlog():ApiResponse<List<Blog>>
}