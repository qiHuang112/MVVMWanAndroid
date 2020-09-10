package com.yolo.mvvm

import android.app.Application
import android.content.ContextWrapper
import com.yolo.mvvm.util.CrashHandler

/**
 * @author yolo.huang
 * @date 2020/9/10
 */

lateinit var instance: BaseApplication

abstract class BaseApplication :Application() {

    companion object {
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        CrashHandler.instance.init() // 崩溃信息收集

    }
}

object AppContext : ContextWrapper(instance)