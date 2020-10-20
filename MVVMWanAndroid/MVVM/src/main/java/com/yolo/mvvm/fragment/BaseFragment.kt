package com.yolo.mvvm.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.yolo.mvvm.viewmodel.BaseViewModel
import java.lang.reflect.ParameterizedType

@Suppress("UNCHECKED_CAST")
abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding> : Fragment() {

    lateinit var mDataBinding: DB
    lateinit var mViewModel: VM
    lateinit var mActivity: AppCompatActivity

    abstract val layoutId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        mDataBinding.lifecycleOwner = this
        return mDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = activity as AppCompatActivity
        mViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory(mActivity.application)
        ).get((this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>)
        initView()
        getData()
    }

    abstract fun initView()

    abstract fun getData()

}