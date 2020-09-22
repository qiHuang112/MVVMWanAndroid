package com.yolo.mvvmwanandroid.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yolo.mvvm.viewmodel.BaseViewModel
import com.yolo.mvvmwanandroid.network.bean.Blog
import com.yolo.mvvmwanandroid.repository.PlazaRepository
import com.yolo.mvvmwanandroid.ui.loadmore.LoadMoreStatus

/**
 * @author yolo.huang
 * @date 2020/9/15
 */
class PlazaFragmentViewModel(application: Application):BaseViewModel(application) {

    companion object{
        const val INIT_PAGE = 0
    }

    private var page = INIT_PAGE

    val plaza:MutableLiveData<MutableList<Blog>> = MutableLiveData()
    val refreshStatus:MutableLiveData<Boolean> = MutableLiveData()
    val loadMoreStatus:MutableLiveData<LoadMoreStatus> = MutableLiveData()


    private val plazaRepository by lazy { PlazaRepository() }


    fun getPlaza(){
        refreshStatus.value = true
        launch(
            block = {
                val blog = plazaRepository.getPlaza(INIT_PAGE)
                page = blog.curPage
                plaza.value = mutableListOf<Blog>().apply{
                    addAll(blog.datas)
                }
                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
            }
        )
    }

    fun loadMorePlaza(){
        loadMoreStatus.value = LoadMoreStatus.LOADING
        launch(
            block = {
                val blogMore = plazaRepository.getPlaza(page+1)
                val currentBlog = plaza.value?: mutableListOf()
                currentBlog.addAll(blogMore.datas)
                plaza.value = currentBlog
                page = blogMore.curPage
                loadMoreStatus.value =if(blogMore.offset>=blogMore.total){
                    LoadMoreStatus.END
                }else{
                    LoadMoreStatus.COMPLETED
                }
            },
            error = {
                loadMoreStatus.value  = LoadMoreStatus.ERROR
            }
        )
    }


}