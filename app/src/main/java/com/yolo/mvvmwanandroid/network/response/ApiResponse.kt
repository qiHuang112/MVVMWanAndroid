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
) : BaseResponse<T>(){
    fun apiData():T{
        if(errorCode==0){
            return data
        }else{
            throw ApiException(errorCode,errorMsg)
        }
    }
}

class ApiException(var code:Int, override val message: String):RuntimeException()