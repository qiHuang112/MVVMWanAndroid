package com.yolo.mvvmwanandroid.network.bean

import android.os.Parcelable
import com.yolo.mvvm.view.banner.BannerView
import kotlinx.android.parcel.Parcelize

/**
 * @author yolo.huang
 * @date 2020/6/18
 */
@Parcelize
data class Banner(var desc:String = "", var imagePath:String ="", var isVisible:Int = 0,
                  var order:Int =0, var title:String = "", var type:Int = 0, var url:String = ""): Parcelable,BannerView.BannerItemData {
    override val imageUrl: String
        get() = imagePath
}