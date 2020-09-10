package com.yolo.mvvm.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.yolo.mvvm.viewmodel.BaseViewModel
import java.lang.reflect.ParameterizedType

@Suppress("UNCHECKED_CAST")
abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding> : AppCompatActivity() {
    lateinit var mViewModel: VM
    lateinit var mDataBinding: DB

    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBinding = DataBindingUtil.setContentView(this, layoutId)
        mDataBinding.lifecycleOwner = this
        mViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))
            .get((this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>)
        initView(savedInstanceState)
    }

    abstract fun initView(savedInstanceState: Bundle?)

}