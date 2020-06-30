package com.yolo.mvvmwanandroid.network.bean

import java.io.Serializable

/**
 * 通用分页数据包装类
 * @author qiHuang112
 */
data class PageData<T>(
    var curPage: Int = 0,
    var datas: List<T> = listOf()
) : Serializable