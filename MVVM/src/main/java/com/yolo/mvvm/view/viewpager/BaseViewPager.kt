package com.yolo.mvvm.view.viewpager

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * @author yolo.huang
 * @date 2020/6/28
 */
class BaseViewPager(context: Context,attr: AttributeSet): ViewPager(context,attr) {

    var scrollble = true

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return if(!scrollble){
            false
        }else{
            super.onTouchEvent(ev)
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return if(!scrollble){
            false
        }else{
            super.onInterceptTouchEvent(ev)
        }
    }
}