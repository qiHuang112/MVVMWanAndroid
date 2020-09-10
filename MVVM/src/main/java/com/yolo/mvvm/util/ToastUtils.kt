package com.yolo.mvvm.util

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import java.text.MessageFormat

/**
 * Created by Tony Shen on 2017/2/28.
 */


/**
 * @param activity
 *
 * @param resId string资源id
 *
 * @param duration
 */
private fun showToast(activity: Activity?, resId: Int,
                      duration: Int) {
    if (activity == null)
        return

    val context = activity.application
    activity.runOnUiThread { Toast.makeText(context, resId, duration).show() }
}

/**
 * @param activity
 *
 * @param message toast的内容
 *
 * @param duration
 */
private fun showToast(activity: Activity?, message: String,
                      duration: Int = Toast.LENGTH_SHORT) {
    if (activity == null)
        return
    if (TextUtils.isEmpty(message))
        return

    val context = activity.application
    activity.runOnUiThread { Toast.makeText(context, message, duration).show() }
}

fun showToast(context: Context?, resId: Int,
              duration: Int = Toast.LENGTH_SHORT) {
    if (context == null)
        return

    (context as Activity).runOnUiThread {
        Toast.makeText(context.applicationContext, resId,
                duration).show()
    }
}

fun showToast(context: Context?, message: String,
              duration: Int = Toast.LENGTH_SHORT) {
    if (context == null)
        return

    (context as Activity).runOnUiThread {
        Toast.makeText(context.applicationContext, message,
                duration).show()
    }

}


