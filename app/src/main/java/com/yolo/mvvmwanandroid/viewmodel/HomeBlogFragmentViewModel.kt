package com.yolo.mvvmwanandroid.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yolo.mvvm.viewmodel.BaseViewModel
import com.yolo.mvvmwanandroid.network.bean.Blog
import com.yolo.mvvmwanandroid.repository.BlogRepository
import com.yolo.mvvmwanandroid.ui.loadmore.LoadMoreStatus

/**
 * @author yolo.huang
 * @date 2020/7/2
 * [com.yolo.mvvmwanandroid.fragment.HomeBlogFragment]
 */
class HomeBlogFragmentViewModel(application: Application):BaseViewModel(application){


    companion object{
        const val INITIAL_PAGE = 0
    }

    private val blogRepository by lazy { BlogRepository() }

    val blog : MutableLiveData<MutableList<Blog>> = MutableLiveData()
    val refreshStatus:MutableLiveData<Boolean> = MutableLiveData()
    val loadMoreStatus:MutableLiveData<LoadMoreStatus> = MutableLiveData()
    var page = INITIAL_PAGE

    fun getBlog(){
        refreshStatus.value = true
        launch(
            block = {
                val topDeferred = async {
                     blogRepository.getTopBlog()
                }
                val blogDeferred = async {
                     blogRepository.getBlog(INITIAL_PAGE)
                }
                val topBlog = topDeferred.await().apply {
                    forEach {
                        it.top = true
                    }
                }
                val blogs = blogDeferred.await()

                page = blogs.curPage

                blog.value = mutableListOf<Blog>().apply {
                    addAll(topBlog)
                    addAll(blogs.datas)
                }

                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
            }
        )
    }

    fun getMoreBlog(){
        launch(
            block = {
                val moreBlog = blogRepository.getBlog(page)
                val collectionsList = blog.value ?: mutableListOf()
                page = moreBlog.curPage
                collectionsList.addAll(moreBlog.datas)
                blog.value = collectionsList
                loadMoreStatus.value =if(moreBlog.offset>=moreBlog.total){
                    LoadMoreStatus.END
                }else{
                    LoadMoreStatus.COMPLETED

                }
            },
            error = {

                loadMoreStatus.value = LoadMoreStatus.ERROR
            }
        )
    }
}