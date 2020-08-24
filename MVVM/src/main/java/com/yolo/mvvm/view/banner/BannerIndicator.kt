package com.yolo.mvvm.view.banner

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.yolo.mvvm.R

/**
 * @author yolo.huang
 * @date 2020/6/29
 */
class BannerIndicator @JvmOverloads constructor(
    mContext: Context, attrs: AttributeSet? = null,
    defStyleAttr:Int = 0) : LinearLayout(mContext,attrs,defStyleAttr)  {

    val lineWidth = (4 * mContext.resources.displayMetrics.density).toInt()

    val unselectedDrawable: Drawable = resources.getDrawable(R.drawable.shape_banner_indicator_unselected)
    val selectedDrawable: Drawable = resources.getDrawable(R.drawable.shape_banner_indicator_selected)

    var index:Int = 0
        set(value) {
            field = value
            for (i in 0 until count) {
                val imageView =
                    findViewById<View>(i) as ImageView
                if (i == index) {
                    imageView.setImageDrawable(selectedDrawable)
                } else {
                    imageView.setImageDrawable(unselectedDrawable)
                }
            }
        }

    var count:Int = 0
        set(value) {
            field = value
            removeAllViews()
            for (i in 0 until count) {
                val imageView = ImageView(context)
                imageView.setPadding(lineWidth, 0, lineWidth, 0)
                imageView.id = i
                if (i == index) {
                    imageView.setImageDrawable(selectedDrawable)
                } else {
                    imageView.setImageDrawable(unselectedDrawable)
                }
                this.addView(imageView)
            }
        }


}