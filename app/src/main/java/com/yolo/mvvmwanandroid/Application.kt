package com.yolo.mvvmwanandroid

import android.app.Application
import android.content.Context
import android.content.ContextWrapper

private lateinit var INSTANCE: Context

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        CrashHandler.instance.init() // 崩溃信息收集
    }
}

object AppContext : ContextWrapper(INSTANCE)