package com.yolo.mvvmwanandroid.view.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.yolo.mvvmwanandroid.R
import com.yolo.mvvmwanandroid.databinding.TitleItemBinding
import com.yolo.mvvmwanandroid.network.bean.Category

/**
 * @author yolo.huang
 * @date 2020/9/14
 */
class TitleAdapter:BaseQuickAdapter<Category,BaseDataBindingHolder<TitleItemBinding>>(R.layout.title_item) {
    private var checkedPosition = 0

    var clickListener:((position:Int) ->Unit)? =null

    override fun convert(holder: BaseDataBindingHolder<TitleItemBinding>, item: Category) {
        holder.dataBinding?.apply {
            title = item.name
            ctvCategory.isChecked = holder.adapterPosition == checkedPosition
        }
        setOnItemClickListener { adapter, view, position ->
            checked(position)
            clickListener?.invoke(position)
        }
    }

    fun checked(position: Int){
        checkedPosition = position
        notifyDataSetChanged()
    }
}