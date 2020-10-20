package com.yolo.mvvmwanandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yolo.mvvmwanandroid.R
import com.yolo.mvvmwanandroid.network.bean.ArticleContent
import kotlinx.android.synthetic.main.article_item.view.*

/**
 * @author: qiHuang112
 * @date: 2020/6/30
 */
class ArticleContentAdapter(
    var data: List<ArticleContent> = mutableListOf()
) : RecyclerView.Adapter<ArticleContentAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mTvTitle = view.tv_title!!
        var mTvSubtitle = view.tv_subtitle!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.article_item, parent, false))
    }

    override fun getItemCount() = data.size
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        data[position].let { articleContent ->
            holder.mTvTitle.text = articleContent.title
            holder.mTvSubtitle.text = articleContent.author
        }
    }

}