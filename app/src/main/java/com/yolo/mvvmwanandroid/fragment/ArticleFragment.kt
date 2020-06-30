package com.yolo.mvvmwanandroid.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.yolo.mvvm.fragment.BaseFragment
import com.yolo.mvvmwanandroid.R
import com.yolo.mvvmwanandroid.databinding.FragmentArticleBinding
import com.yolo.mvvmwanandroid.network.bean.Article
import com.yolo.mvvmwanandroid.viewmodel.ArticleFragmentViewModel
import kotlinx.android.synthetic.main.fragment_article.*

/**
 * 公众号
 * @author qiHuang112
 */
class ArticleFragment : BaseFragment<ArticleFragmentViewModel, FragmentArticleBinding>() {
    override val layoutId = R.layout.fragment_article

    override fun initView() {

        // 进入公众号模块请求公众号的title
        mViewModel.getArticleTitles()

        // 根据请求结果初始化Viewpager和TabLayout
        mViewModel.articles.observe(this, Observer { articles: List<Article> ->
            vp_articles.adapter = object : FragmentStateAdapter(this) {
                override fun getItemCount() = articles.size

                override fun createFragment(position: Int) = ArticleContentFragment().apply {
                    arguments = Bundle().apply {
                        putInt("id", articles[position].id)
                    }
                }
            }

            TabLayoutMediator(tl_articles, vp_articles) { tab, position ->
                tab.text = articles[position].name
            }.attach()
        })
    }
}