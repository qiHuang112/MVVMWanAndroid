package com.yolo.mvvmwanandroid.fragment

import com.yolo.mvvm.fragment.BaseFragment
import com.yolo.mvvmwanandroid.R
import com.yolo.mvvmwanandroid.databinding.FragmentArticleBinding
import com.yolo.mvvmwanandroid.viewmodel.ArticleFragmentViewModel
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment : BaseFragment<ArticleFragmentViewModel, FragmentArticleBinding>() {
    override val layoutId = R.layout.fragment_article

    override fun initView() {
        btn_request.setOnClickListener {
            mViewModel.getPublicTitleData()
        }
    }
}