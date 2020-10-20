package com.yolo.mvvmwanandroid.network.bean

import java.io.Serializable

/**
 * 通用分页数据包装类
 * @author qiHuang112
 */
data class PageData<T>(
    val offset: Int,
    val size: Int,
    val total: Int,
    val pageCount: Int,
    val curPage: Int,
    val over: Boolean,
    val datas: List<T>
) : Serializable