package com.yolo.mvvm.util.ext

import android.os.Handler
import android.os.Message

/**
 * @author yolo.huang
 * @date 2020/6/29
 */
class LocalHandler :Handler(){

    protected var mHandlerCallback: Callback? = null

    override fun handleMessage(msg: Message?) {
        if (mHandlerCallback != null) {
            mHandlerCallback!!.handleMessage(msg)
        } else {
            super.handleMessage(msg)
        }
    }

    fun registerHandleCallback(callback: Callback) {
        mHandlerCallback = callback
    }

    fun unRegisterHandleCallback() {
        mHandlerCallback = null
    }

}