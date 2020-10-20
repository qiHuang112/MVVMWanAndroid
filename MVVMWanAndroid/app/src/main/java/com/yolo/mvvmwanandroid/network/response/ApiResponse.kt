package com.yolo.mvvmwanandroid.network.response

import com.yolo.mvvm.network.BaseResponse
import java.lang.RuntimeException

/**
 * WanAndroid API请求结果封装
 */
data class ApiResponse<T>(
    override var errorCode: Int,
    override var errorMsg: String,
    override var data: T
) : BaseResponse<T>()

