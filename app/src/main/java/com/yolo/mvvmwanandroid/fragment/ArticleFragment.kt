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

class ArticleFragment : BaseFragment<ArticleFragmentViewModel, FragmentArticleBinding>() {
    override val layoutId = R.layout.fragment_article

    override fun initView() {

        mViewModel.getPublicTitleData()

        mViewModel.articles.observe(this, Observer { articles: List<Article> ->
            vp_articles.adapter = object : FragmentStateAdapter(this) {
                override fun getItemCount() = articles.size

                override fun createFragment(position: Int) = TestFragment().apply {
                    arguments = Bundle().apply {
                        putString("title", "page:$position")
                    }
                }
            }

            TabLayoutMediator(tl_articles, vp_articles) { tab, position ->
                tab.text = articles[position].name
            }.attach()
        })
    }
}