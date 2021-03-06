package com.yolo.mvvmwanandroid.ui.adapter

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.yolo.mvvmwanandroid.R
import com.yolo.mvvmwanandroid.databinding.BlogItemBinding
import com.yolo.mvvmwanandroid.network.bean.Blog
import com.yolo.mvvmwanandroid.ui.loadmore.CommonLoadMoreView
import com.yolo.mvvmwanandroid.ui.loadmore.LoadMoreStatus

/**
 * @author yolo.huang
 * @date 2020/9/10
 */
class BlogAdapter:
    BaseQuickAdapter<Blog, BaseDataBindingHolder<BlogItemBinding>>(R.layout.blog_item),
    LoadMoreModule,Observer<LoadMoreStatus> {

    init {
        loadMoreModule.loadMoreView = CommonLoadMoreView()
        setDiffCallback(BlogDiffCallBack())
        animationEnable = true
    }

    override fun convert(holder: BaseDataBindingHolder<BlogItemBinding>, item: Blog) {
        holder.dataBinding?.blog = item
        if (!item.tags.isNullOrEmpty()) {
            holder.dataBinding?.tag = item.tags[0]
        }
        holder.dataBinding?.ivCollect?.isSelected = item.collect
    }

    override fun onChanged(it: LoadMoreStatus?) {
        when(it){
            LoadMoreStatus.ERROR -> loadMoreModule.loadMoreFail()
            LoadMoreStatus.END -> loadMoreModule.loadMoreEnd()
            LoadMoreStatus.COMPLETED ->loadMoreModule.loadMoreComplete()
            else -> return
        }
    }


}

class BlogDiffCallBack: DiffUtil.ItemCallback<Blog>(){
    override fun areItemsTheSame(oldItem: Blog, newItem: Blog): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Blog, newItem: Blog): Boolean {
        return oldItem.collect == newItem.collect
    }

}