package com.yolo.mvvmwanandroid.network.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tags(var name: String, var url: String) : Parcelable