package com.yolo.mvvmwanandroid.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.idlefish.flutterboost.containers.BoostFlutterActivity
import com.yolo.mvvmwanandroid.network.bean.Blog
import com.yolo.mvvmwanandroid.ui.activity.DetailActivity
import com.yolo.mvvmwanandroid.ui.activity.MainActivity





/**
 * @author yolo.huang
 * @date 2020/9/27
 */
object PageRouter {

    private val pageName: Map<String, String> = hashMapOf(
        "tree" to "tree",
        "my" to "my",
        "login" to "login",
        "collection" to "collection",
    )

    private const val DETAIL_ACTIVITY_PAGE_URL = "android://detailPage"
    private const val MAIN_PAGE_URL = "android://mainPage"

    private const val DETAIL_KEY= "BLOG_KEY"

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
                    val map = params[DETAIL_KEY] as Map<String,String>
                    val blog = Blog.fromMap(map)
                    val intent = Intent(context,DetailActivity::class.java)
                    intent.putExtra(DetailActivity.PARAM_BLOG,blog)
                    context.startActivity(intent)
                    return true
                }
                else -> false
            }
        } catch (t: Throwable) {
            false
        }
    }
}