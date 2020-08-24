package com.yolo.mvvm.view.recyclerview

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.yolo.mvvm.R

/**
 * @author yolo.huang
 * @date 2020/6/28
 */
class BaseRecyclerViewHolder constructor(val context:Context,var mItemView: View,var viewType:Int):RecyclerView.ViewHolder(mItemView) {

    var mViews:SparseArray<View> = SparseArray()

    companion object {

        /**
         * 使用此方法直接获取holder，然后在adapter的bindItemViewHolder方法中对itemView进行数据绑定
         */
        fun get(rootView: ViewGroup,layoutId:Int,viewType:Int):BaseRecyclerViewHolder{
            val itemView = LayoutInflater.from(rootView.context).inflate(layoutId,rootView,false)
            return BaseRecyclerViewHolder(rootView.context,itemView,viewType)
        }

        /**
         * 直接使用 itemView 来加载到recyclerView中的话，会出现设置match_parent无效的情况。
         * 所以需要在外层包裹一层ViewGroup
         */
        fun get(rootView: ViewGroup,itemView: View,viewType: Int):BaseRecyclerViewHolder{
            val viewGroup:ViewGroup = LayoutInflater.from(rootView.context).inflate(R.layout.item_root_layout,rootView,false) as ViewGroup
            viewGroup.addView(itemView)
            return BaseRecyclerViewHolder(rootView.context,viewGroup,viewType)
        }
    }


    /**
     * 使用这个方法来获取itemView中各个控件view
     */
    fun <T : View> getView(viewId: Int): T {
        var view = mViews.get(viewId)
        if (view == null) {
            view = mItemView.findViewById(viewId)
            mViews.put(viewId, view)
        }
        return view as T
    }

}

class BaseBindingViewHolder<VB : ViewDataBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root)