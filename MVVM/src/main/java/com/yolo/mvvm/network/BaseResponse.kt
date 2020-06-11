package com.yolo.mvvm.network

/**
 * 请求返回结果封装，默认code为0业务成功
 * 其他结果如网络异常等可继承此基类，根据code判断业务状态
 */
abstract class BaseResponse<T> {
    abstract var code: Int
    abstract var msg: String
    abstract var data: T
    protected fun isSuccess() = code == 0
}