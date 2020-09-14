package com.yolo.mvvmwanandroid.network.bean

/**
 * @author yolo.huang
 * @date 2020/9/10
 */
data class Category(
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int,
    val children: ArrayList<Category>
)