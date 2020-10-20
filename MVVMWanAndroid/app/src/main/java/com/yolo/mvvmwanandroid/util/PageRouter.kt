package com.yolo.mvvmwanandroid.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.idlefish.flutterboost.containers.BoostFlutterActivity
import com.yolo.mvvmwanandroid.ui.activity.DetailActivity
import com.yolo.mvvmwanandroid.ui.activity.MainActivity





/**
 * @author yolo.huang
 * @date 2020/9/27
 */
object PageRouter {

    val pageName: Map<String, String> = hashMapOf(
        "tree" to "tree",
        "second" to "second",
        "tab" to "tab",
    )

    val DETAIL_ACTIVITY_PAGE_URL = "sample://detailPage"
    val FLUTTER_PAGE_URL = "sample://flutterPage"
    val MAIN_PAGE_URL = "sample://mainPage"

    fun openPageByUrl(
        context: Context,
        url: String,
        params: MutableMap<String, Any>
    ): Boolean {
        return openPageByUrl(context, url, params, 0)
    }

    fun openPageByUrl(
        context: Context,
        url: String,
        params: Map<String, Any>,
        requestCode: Int
    ): Boolean {
        val path = url.split("\\?".toRegex()).toTypedArray()[0]
        return try {
            when {
                pageName.containsKey(path) -> {
                    val intent: Intent =
                        BoostFlutterActivity.withNewEngine().url(pageName[path] ?: error("")).params(params)
                            .backgroundMode(BoostFlutterActivity.BackgroundMode.opaque).build(context)
                    if (context is Activity) {
                        val activity: Activity = context as Activity
                        activity.startActivityForResult(intent, requestCode)
                    } else {
                        context.startActivity(intent)
                    }
                    return true
                }
                url.startsWith(MAIN_PAGE_URL) -> {
                    context.startActivity(Intent(context, MainActivity::class.java))
                    return true
                }
                url.startsWith(DETAIL_ACTIVITY_PAGE_URL) -> {
                    context.startActivity(Intent(context, DetailActivity::class.java))
                    return true
                }
                else -> false
            }
        } catch (t: Throwable) {
            false
        }
    }
}