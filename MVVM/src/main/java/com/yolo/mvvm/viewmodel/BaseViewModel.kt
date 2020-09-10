package com.yolo.mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlin.Exception

typealias Block<T> = suspend () ->T
typealias Cancel = suspend (e:Exception) -> Unit
typealias Error = suspend (e:Exception) ->Unit

open class BaseViewModel(application: Application) : AndroidViewModel(application){

    fun launch(
        block:Block<Unit>,
        error:Error? = null,
        cancel:Cancel? = null,
        showErrorToast:Boolean = true
    ):Job{
        return viewModelScope.launch {
            try {
                block.invoke()
            }catch (e:Exception){
                when(e){
                    is CancellationException ->{
                        cancel?.invoke(e)
                    }
                    else ->{
                        error?.invoke(e)

                    }
                }
            }
        }
    }


    fun <T> async(
        block: Block<T>
    ):Deferred<T>{
        return viewModelScope.async { block.invoke() }
    }
}


