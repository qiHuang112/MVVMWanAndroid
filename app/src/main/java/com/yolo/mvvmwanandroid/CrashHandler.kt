package com.yolo.mvvmwanandroid

import android.util.Log
import kotlin.system.exitProcess

/**
 * 崩溃收集
 * 目前只是简单的做了个拦截，方便debug的时候分析异常点，后续考虑加入异常日志上传功能
 */
class CrashHandler private constructor() : Thread.UncaughtExceptionHandler {

    companion object {
        val instance: CrashHandler by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CrashHandler() }
    }

    fun init() {
        Thread.setDefaultUncaughtExceptionHandler(instance)
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        Log.e("Exception", "Thread:$t,msg:${e.message}")
        exitProcess(1)
    }
}