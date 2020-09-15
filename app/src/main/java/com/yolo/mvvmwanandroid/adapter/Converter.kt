
/**
 * @author yolo.huang
 * @date 2020/9/15
 */
@file:JvmName("Converter")
package com.yolo.mvvmwanandroid.adapter

import androidx.core.text.HtmlCompat

fun htmlToSpanned(text:String):CharSequence{
    return if (text.isNullOrEmpty()) ""
    else HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
}

