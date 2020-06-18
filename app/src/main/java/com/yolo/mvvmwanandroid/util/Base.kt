package com.yolo.mvvmwanandroid.util

// toInt是向下取整的，所以加上0.5让它四舍五入
val Int.dp: Int
    get() = (this * AppContext.resources.displayMetrics.density + 0.5).toInt()

val Float.dp: Int
    get() = (this * AppContext.resources.displayMetrics.density + 0.5).toInt()

val Double.dp: Int
    get() = (this * AppContext.resources.displayMetrics.density + 0.5).toInt()
