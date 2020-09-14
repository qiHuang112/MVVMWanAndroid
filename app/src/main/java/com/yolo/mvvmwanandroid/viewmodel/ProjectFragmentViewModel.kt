package com.yolo.mvvmwanandroid.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yolo.mvvm.viewmodel.BaseViewModel
import com.yolo.mvvmwanandroid.network.bean.Blog
import com.yolo.mvvmwanandroid.network.bean.Category
import com.yolo.mvvmwanandroid.network.request.RequestManager
import com.yolo.mvvmwanandroid.view.loadmore.LoadMoreStatus

/**
 * @author yolo.huang
 * @date 2020/9/14
 */
class ProjectFragmentViewModel(application: Application):BaseViewModel(application) {


    companion object{
        const val INITIAL_PAGE = 0
        const val INITIAL_POSITION = 0

    }
    val refreshStatus:MutableLiveData<Boolean> = MutableLiveData()
    val loadMoreStatus:MutableLiveData<LoadMoreStatus> = MutableLiveData()

    val title: MutableLiveData<MutableList<Category>> = MutableLiveData()
    val projects:MutableLiveData<MutableList<Blog>> = MutableLiveData()

    private var page = INITIAL_PAGE

    val currentPosition : MutableLiveData<Int> = MutableLiveData()

    fun getTitle(){
        refreshStatus.value = true
        val topCategory = Category(101,-1,"最新",0,0,false,0, ArrayList())
        title.value = mutableListOf<Category>().apply {
            add(topCategory)
        }

        launch(
            block = {
                val topProjects = RequestManager.instance.getTopProject(INITIAL_PAGE)

                val titleCategory = RequestManager.instance.getProjectTitle()

                currentPosition.value = INITIAL_POSITION
                title.value?.addAll(titleCategory)
                projects.value = mutableListOf<Blog>().apply {
                    addAll(topProjects.datas)
                }
                page = topProjects.curPage
                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
            }
        )
    }

    fun refreshProject(position:Int = currentPosition.value?: INITIAL_POSITION){
        refreshStatus.value = true
        if(position !=currentPosition.value){
            projects.value = mutableListOf()
            currentPosition.value = position
        }

        launch(
            block = {
                val category = title.value?:return@launch
                val cid = category[position].id
                val project = if(cid == -1){
                    RequestManager.instance.getTopProject(INITIAL_PAGE)
                }else{
                    RequestManager.instance.getProject(INITIAL_PAGE,cid)
                }
                projects.value = project.datas.toMutableList()
                page = project.curPage

                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false

            }
        )
    }

    fun loadMoreProject(){
        launch(
            block = {
                val category = title.value?:return@launch
                val position = currentPosition.value?:return@launch
                val cid = category[position].id

                val project = if(cid == -1){
                    RequestManager.instance.getTopProject(page)
                }else{
                    RequestManager.instance.getProject(page,cid)
                }
                projects.value?.addAll(project.datas)
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