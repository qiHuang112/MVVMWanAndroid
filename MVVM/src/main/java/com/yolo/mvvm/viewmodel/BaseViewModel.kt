package com.yolo.mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonParseException
import com.yolo.mvvm.AppContext
import com.yolo.mvvm.R
import com.yolo.mvvm.network.ApiException
import com.yolo.mvvm.util.showToast
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
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
                        showError(e)
                        error?.invoke(e)

                    }
                }
            }
        }
    }

    private fun showError(e: Exception) {
        when(e){
            is ApiException -> {
                when(e.code){
                    -1001 ->{
                        //未登录
                    }
                    else ->{
                        showToast(AppContext,e.message)
                    }
                }
            }
            is ConnectException, is SocketTimeoutException, is UnknownHostException, is HttpException ->{
                showToast(AppContext, R.string.network_request_failed)
            }
            is JsonParseException ->{
                showToast(AppContext,R.string.api_data_parse_error)
            }
            else -> {
                showToast(AppContext,e.message?:AppContext.getString(R.string.error_unknown))
            }

        }
    }


    fun <T> async(
        block: Block<T>
    ):Deferred<T>{
        return viewModelScope.async { block.invoke() }
    }
}


