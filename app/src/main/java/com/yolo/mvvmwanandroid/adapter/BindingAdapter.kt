package com.yolo.mvvmwanandroid.adapter

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yolo.mvvm.view.banner.BannerView

/**
 * @author yolo.huang
 * @date 2020/8/20
 */
object BindingAdapter {


    @BindingAdapter("BannerItemOnClickListener")
    @JvmStatic
    fun setBannerItemOnClickListener(bannerView: BannerView, listener: BannerView.BannerItemOnClickListener){
        bannerView.mBannerItemOnClickListener = listener
    }

    @BindingAdapter("visibility")
    @JvmStatic
    fun isVisibility(view: View,visibility:Boolean){
        if(visibility){
            view.visibility =View.VISIBLE
        }else{
            view.visibility = View.GONE
        }
    }

    @BindingAdapter("Adapter")
    fun setAdapter(view:RecyclerView,adapter:RecyclerView.Adapter<RecyclerView.ViewHolder>){
        view.adapter = adapter
    }

}