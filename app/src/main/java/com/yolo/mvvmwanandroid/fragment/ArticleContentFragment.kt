package com.yolo.mvvmwanandroid.fragment

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.yolo.mvvm.fragment.BaseFragment
import com.yolo.mvvmwanandroid.R
import com.yolo.mvvmwanandroid.adapter.ArticleContentAdapter
import com.yolo.mvvmwanandroid.databinding.FragmentArticleContentBinding
import com.yolo.mvvmwanandroid.viewmodel.ArticleContentFragmentViewModel
import kotlinx.android.synthetic.main.fragment_article_content.*

/**
 * 公众号 - 具体公众号（如：郭霖）
 * @author qiHuang112
 */
class ArticleContentFragment : BaseFragment<ArticleContentFragmentViewModel, FragmentArticleContentBinding>() {
    override val layoutId = R.layout.fragment_article_content

    override fun initView() {

        // 初始化RecyclerView
        val adapter = ArticleContentAdapter()
        rv_article.adapter = adapter
        rv_article.layoutManager = LinearLayoutManager(mActivity)

        // 请求公众号内容
        mViewModel.getArticleContents(true, arguments?.getInt("id")!!)

        // 根据公众号内容加载视图
        mViewModel.data.observe(this, Observer {
            adapter.data = it
            adapter.notifyDataSetChanged()
        })

    }
}