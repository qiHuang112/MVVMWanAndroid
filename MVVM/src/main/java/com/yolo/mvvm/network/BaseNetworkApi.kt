package com.yolo.mvvm.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit

abstract class BaseNetworkApi {

    /**
     * 子类覆写 httpClientBuilder 用于初始化OkHttpClient
     * 子类覆写 retrofitBuilder 用于初始化Retrofit
     */
    abstract var httpClientBuilder: (OkHttpClient.Builder) -> OkHttpClient.Builder
    abstract var retrofitBuilder: (Retrofit.Builder) -> Retrofit.Builder

    private val okHttpClient by lazy {
        OkHttpClient.Builder().let(httpClientBuilder).build()
    }

    fun <T> getApi(serviceClass: Class<T>, baseUrl: String): T =
        Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient).let(retrofitBuilder)
            .build().create(serviceClass)
}