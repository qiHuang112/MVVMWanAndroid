package com.yolo.mvvmwanandroid.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yolo.mvvm.viewmodel.BaseViewModel
import com.yolo.mvvmwanandroid.network.bean.Blog
import com.yolo.mvvmwanandroid.repository.AnswerRepository
import com.yolo.mvvmwanandroid.ui.loadmore.LoadMoreStatus

/**
 * @author yolo.huang
 * @date 2020/9/15
 */
class AnswerFragmentViewModel(application: Application):BaseBlogViewModel(application) {

    companion object{
        const val INIT_PAGE = 0
    }

    private var page = INIT_PAGE

    val answer: MutableLiveData<MutableList<Blog>> = MutableLiveData()


    private val answerRepository by lazy { AnswerRepository() }

    fun getAnswer(){
        refreshStatus.set(true)
        launch(
            block = {
                val blog = answerRepository.getAnswer(INIT_PAGE)
                page = blog.curPage
                answer.value = mutableListOf<Blog>().apply{
                    addAll(blog.datas)
                }
                refreshStatus.set(false)
                reloadStatus.set(false)
            },
            error = {
                refreshStatus.set(false)
                reloadStatus.set(true)
            }
        )
    }

    fun loadMoreAnswer(){
        loadMoreStatus.value = LoadMoreStatus.LOADING
        launch(
            block = {
                val blogMore = answerRepository.getAnswer(page+1)
                val currentBlog = answer.value?: mutableListOf()
                currentBlog.addAll(blogMore.datas)
                answer.value = currentBlog
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