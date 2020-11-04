package com.yolo.mvvmwanandroid.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.idlefish.flutterboost.containers.FlutterFragment
import com.yolo.mvvm.activity.BaseActivity
import com.yolo.mvvm.util.showToast
import com.yolo.mvvmwanandroid.R
import com.yolo.mvvmwanandroid.databinding.ActivityMainBinding
import com.yolo.mvvmwanandroid.ui.fragment.HomeFragment
import com.yolo.mvvmwanandroid.ui.fragment.MeFragment
import com.yolo.mvvmwanandroid.ui.fragment.WeChatFragment
import com.yolo.mvvmwanandroid.ui.widget.ScrollToTop
import com.yolo.mvvmwanandroid.viewmodel.MainActivityViewModel

/**
 * 项目为单Activity多Fragment模式
 * MainActivity为唯一Activity
 * @author qiHuang112
 */
class MainActivity : BaseActivity<MainActivityViewModel, ActivityMainBinding>() {
    override val layoutId = R.layout.activity_main

    private lateinit var fragments:Map<Int,Fragment>

    private var previousTimeMillis = 0L

    override fun initView(savedInstanceState: Bundle?) {

        val treeFragment :FlutterFragment = FlutterFragment.NewEngineFragmentBuilder().url("tree").build()
        val myFragment :FlutterFragment = FlutterFragment.NewEngineFragmentBuilder().url("my").build()

        fragments = mapOf(
            R.id.nav_home to HomeFragment.instance,
            R.id.nav_article to WeChatFragment.instance,
            R.id.nav_tree to treeFragment,
            R.id.nav_me to myFragment
        )

        mDataBinding.navView.setOnNavigationItemSelectedListener {
            showFragment(id = it.itemId)
            true
        }
        mDataBinding.navView.setOnNavigationItemReselectedListener {
            val fragment = fragments[it.itemId]
            if (fragment is ScrollToTop) {
                fragment.scrollToTop()
            }
        }

        if(savedInstanceState==null){
            val firstId  = R.id.nav_home
            mDataBinding.navView.selectedItemId = firstId
            showFragment(firstId)
        }
    }

    private fun showFragment(id:Int){
        val targetFragment = fragments[id]
        val currentFragment = supportFragmentManager.fragments.find {
            it.isVisible && it in fragments.values
        }

        supportFragmentManager.beginTransaction().apply {
            currentFragment?.let { if(it.isVisible) hide(it) }

            targetFragment?.let { if(it.isAdded) show(it) else add(R.id.nav_host_fragment,it) }
        }.commit()
    }

    override fun onBackPressed() {
        val currentTimMillis = System.currentTimeMillis()
        if (currentTimMillis - previousTimeMillis < 2000) {
            super.onBackPressed()
        } else {
            showToast(R.string.press_again_to_exit)
            previousTimeMillis = currentTimMillis
        }
    }

}