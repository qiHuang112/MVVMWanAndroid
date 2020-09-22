package com.yolo.mvvmwanandroid.adapter

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.yolo.mvvm.view.banner.BannerView

/**
 * @author yolo.huang
 * @date 2020/8/20
 */

object BindingAdapter {


    @BindingAdapter(value = ["BannerItemOnClickListener","serBannerData"],requireAll = true)
    @JvmStatic
    fun setBannerItemOnClickListener(bannerView: BannerView
                                     , listener: BannerView.BannerItemOnClickListener
    ,list:List<BannerView.BannerItemData>?){
        bannerView.mBannerItemOnClickListener = listener
        bannerView.setData(list)
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

    @BindingAdapter("setAdapter")
    @JvmStatic
    fun setAdapter(view:RecyclerView,adapter:RecyclerView.Adapter<RecyclerView.ViewHolder>){
        view.adapter = adapter
    }

    @BindingAdapter("onClick")
    @JvmStatic
    fun setOnclickListener(view:View,listener:View.OnClickListener){
        view.setOnClickListener {
            listener.onClick(it)
        }
    }

    @BindingAdapter("isRefreshing")
    @JvmStatic
    fun isRefreshing(view:SwipeRefreshLayout,isRefreshing:Boolean){
        view.isRefreshing = isRefreshing
    }

}