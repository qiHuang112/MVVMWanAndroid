package com.yolo.mvvmwanandroid.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yolo.mvvm.viewmodel.BaseViewModel
import com.yolo.mvvmwanandroid.network.bean.Blog
import com.yolo.mvvmwanandroid.network.bean.Category
import com.yolo.mvvmwanandroid.repository.WeChatRepository
import com.yolo.mvvmwanandroid.ui.loadmore.LoadMoreStatus

/**
 * [com.yolo.mvvmwanandroid.fragment.ArticleFragment]
 */
class WeChatFragmentViewModel(application: Application) : BaseViewModel(application) {
    companion object{
        const val INITIAL_PAGE = 0
        const val INITIAL_POSITION = 0
    }


    val refreshStatus:MutableLiveData<Boolean> = MutableLiveData()
    val loadMoreStatus:MutableLiveData<LoadMoreStatus> = MutableLiveData()

    val title: MutableLiveData<MutableList<Category>> = MutableLiveData()
    val blog:MutableLiveData<MutableList<Blog>> = MutableLiveData()

    private var page = INITIAL_PAGE

    val currentPosition : MutableLiveData<Int> = MutableLiveData()


    private val weChatRepository by lazy {
        WeChatRepository()
    }

    fun getTitle(){
        refreshStatus.value = true
        launch(
            block = {

                val titleCategory = weChatRepository.getWeChatTitle()

                val id = titleCategory[INITIAL_POSITION].id
                val pagination = weChatRepository.getWeChatBlog(INITIAL_PAGE, id)
                page = pagination.curPage

                currentPosition.value = INITIAL_POSITION
                title.value =titleCategory
                blog.value = pagination.datas.toMutableList()
                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
            }
        )
    }


    fun refreshWeChatList(position:Int = currentPosition.value?: INITIAL_POSITION){
        refreshStatus.value = true
        if(position !=currentPosition.value){
            blog.value = mutableListOf()
            currentPosition.value = position

        }

        launch(
            block = {
                val category = title.value
                if(category==null){
                    val titleCategory = weChatRepository.getWeChatTitle()

                    val id = titleCategory[position].id
                    val pagination = weChatRepository.getWeChatBlog(INITIAL_PAGE, id)
                    page = pagination.curPage
                    title.value =titleCategory
                    blog.value = pagination.datas.toMutableList()
                }else{

                    val id = category[position].id
                    val pagination = weChatRepository.getWeChatBlog(INITIAL_PAGE, id)
                    page = pagination.curPage
                    blog.value = pagination.datas.toMutableList()
                }
                refreshStatus.value = false

            },
            error = {
                refreshStatus.value = false
            }

        )
    }

    fun loadMoreList(){
        loadMoreStatus.value = LoadMoreStatus.LOADING
        launch(
            block = {
                val category = title.value?:return@launch
                val position = currentPosition.value?:return@launch
                val cid = category[position].id

                val project = weChatRepository.getWeChatBlog(page,cid)
                blog.value?.addAll(project.datas)
                page = project.curPage
                loadMoreStatus.value = if(project.offset>=project.total){
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