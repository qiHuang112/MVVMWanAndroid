package com.yolo.mvvmwanandroid.ui.widget

import android.content.Context
import android.util.AttributeSet
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.yolo.mvvmwanandroid.R

/**
 * @author yolo.huang
 * @date 2020/9/22
 */
class BaseSwipeRefreshLayout(context: Context,attributeSet: AttributeSet?): SwipeRefreshLayout(context,attributeSet) {

    constructor(context: Context) : this(context,null)

    init {
        setColorSchemeResources(R.color.textColorPrimary)
        setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
    }
}