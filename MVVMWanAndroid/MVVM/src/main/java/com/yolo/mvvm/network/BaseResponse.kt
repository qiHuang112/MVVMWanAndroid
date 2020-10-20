package com.yolo.mvvm.network

import java.lang.RuntimeException

/**
 * 请求返回结果封装，默认code为0业务成功
 * 其他结果如网络异常等可继承此基类，根据code判断业务状态
 */
abstract class BaseResponse<T> {
    abstract var errorCode: Int
    abstract var errorMsg: String
    abstract var data: T
    fun isSuccess() = errorCode == 0

    fun apiData():T{
        if(errorCode==0){
            return data
        }else{
            throw ApiException(errorCode,errorMsg)
        }
    }
}

class ApiException(var code:Int, override val message: String): RuntimeException()