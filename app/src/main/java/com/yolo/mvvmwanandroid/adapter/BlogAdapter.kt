package com.yolo.mvvmwanandroid.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.yolo.mvvm.view.recyclerview.BaseBindingViewHolder
import com.yolo.mvvm.view.recyclerview.BaseRecyclerAdapter
import com.yolo.mvvmwanandroid.R
import com.yolo.mvvmwanandroid.databinding.BlogItemBinding
import com.yolo.mvvmwanandroid.network.bean.Blog

/**
 * @author yolo.huang
 * @date 2020/8/24
 */
class BlogAdapter(blogDiff: BlogDiff) :BaseRecyclerAdapter<Blog>(blogDiff){
    override fun getLayoutId(viewType: Int): Int {
        return R.layout.blog_item
    }

    override fun setVariable(
        data: Blog,
        position: Int,
        holder: BaseBindingViewHolder<ViewDataBinding>
    ) {

        (holder.binding as BlogItemBinding).blog = data
        (holder.binding as BlogItemBinding).ivCollect.isSelected = data.collect
        if(!data.tags.isNullOrEmpty()){
            (holder.binding as BlogItemBinding).tag = data.tags[0]
        }
    }


}
class BlogDiff: DiffUtil.ItemCallback<Blog>(){
    override fun areItemsTheSame(oldItem: Blog, newItem: Blog): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Blog, newItem: Blog): Boolean {
        return oldItem.top == newItem.top
        //TODO
    }

}