package com.yolo.mvvmwanandroid.network

import com.google.gson.GsonBuilder
import com.yolo.mvvm.network.BaseNetworkApi
import com.yolo.mvvm.network.CoroutineAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * NetworkApi 单例
 */
class NetworkApi private constructor() : BaseNetworkApi() {

    /**
     * 子类配置OkHttpClient
     */
    override var httpClientBuilder = { builder: OkHttpClient.Builder ->
        builder.connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
    }

    /**
     * 子类配置Retrofit
     */
    override var retrofitBuilder = { builder: Retrofit.Builder ->
        builder.addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
//            .addCallAdapterFactory(CoroutineAdapterFactory())
    }

    companion object {
        // NetworkApi 单例
        val instance: NetworkApi by lazy { NetworkApi() }

        // ApiService 单例
        val service: ApiService by lazy {
            instance.getApi(ApiService::class.java, ApiService.SERVER_URL)
        }
    }

}