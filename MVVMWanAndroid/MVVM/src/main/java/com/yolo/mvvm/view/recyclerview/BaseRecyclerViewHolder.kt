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
open class BaseRecyclerViewHolder constructor(var mItemView: View):RecyclerView.ViewHolder(mItemView) {

    var mViews:SparseArray<View> = SparseArray()

    companion object {

        /**
         * 使用此方法直接获取holder，然后在adapter的bindItemViewHolder方法中对itemView进行数据绑定
         */
        fun get(rootView: ViewGroup,layoutId:Int):BaseRecyclerViewHolder{
            val itemView = LayoutInflater.from(rootView.context).inflate(layoutId,rootView,false)
            return BaseRecyclerViewHolder(itemView)
        }

        /**
         * 直接使用 itemView 来加载到recyclerView中的话，会出现设置match_parent无效的情况。
         * 所以需要在外层包裹一层ViewGroup
         */
        fun get(rootView: ViewGroup,itemView: View):BaseRecyclerViewHolder{
            val viewGroup:ViewGroup = LayoutInflater.from(rootView.context).inflate(R.layout.item_root_layout,rootView,false) as ViewGroup
            viewGroup.addView(itemView)
            return BaseRecyclerViewHolder(viewGroup)
        }
    }

    fun setGone(resId:Int,gone:Boolean){
        val view = getView<View>(resId)
        if(gone){
            view.visibility = View.GONE
        }else{
            view.visibility = View.VISIBLE
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

class BaseBindingViewHolder<VB : ViewDataBinding>(val binding: VB) : BaseRecyclerViewHolder(binding.root)