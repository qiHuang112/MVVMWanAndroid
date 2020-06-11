package com.yolo.mvvmwanandroid.network.response

import com.yolo.mvvm.network.BaseResponse

/**
 * WanAndroid API请求结果封装
 */
data class ApiResponse<T>(
    override var code: Int,
    override var msg: String,
    override var data: T
) : BaseResponse<T>()