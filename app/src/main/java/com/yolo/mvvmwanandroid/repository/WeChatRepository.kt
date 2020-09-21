package com.yolo.mvvmwanandroid.repository

import com.yolo.mvvmwanandroid.network.request.RequestManager

/**
 * @author yolo.huang
 * @date 2020/9/21
 */
class WeChatRepository {
    suspend fun getWeChatTitle() = RequestManager.instance.getWeChatTitle()

    suspend fun getWeChatBlog(page:Int,id:Int) = RequestManager.instance.getWeChatBlog(page,id)
}