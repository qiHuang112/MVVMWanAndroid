package com.yolo.mvvmwanandroid.repository

import com.yolo.mvvmwanandroid.network.request.RequestManager

/**
 * @author yolo.huang
 * @date 2020/9/15
 */
class AnswerRepository {

    suspend fun getAnswer(page:Int) = RequestManager.instance.getAnswer(page)
}