package com.yolo.mvvm.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

/**
 * @author yolo.huang
 * @date 2020/9/14
 */


fun Context.showToast(message: CharSequence) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showToast(@StringRes message: Int) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}