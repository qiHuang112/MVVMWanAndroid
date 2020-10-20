package com.yolo.mvvmwanandroid.repository

import com.yolo.mvvmwanandroid.network.request.RequestManager

/**
 * @author yolo.huang
 * @date 2020/9/14
 */
class BlogRepository {

    suspend fun getTopBlog() = RequestManager.instance.getTopBlog()

    suspend fun getBlog(page:Int) = RequestManager.instance.getBlog(page)
}