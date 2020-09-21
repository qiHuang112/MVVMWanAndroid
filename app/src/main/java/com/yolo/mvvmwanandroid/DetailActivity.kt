package com.yolo.mvvmwanandroid

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.just.agentweb.WebChromeClient
import com.just.agentweb.WebViewClient
import com.yolo.mvvm.activity.BaseActivity
import com.yolo.mvvmwanandroid.common.customJs
import com.yolo.mvvmwanandroid.common.whiteHostList
import com.yolo.mvvmwanandroid.databinding.ActivityDetailBinding
import com.yolo.mvvmwanandroid.network.bean.Blog
import com.yolo.mvvmwanandroid.viewmodel.DetailViewModel

class DetailActivity : BaseActivity<DetailViewModel, ActivityDetailBinding>() {


    companion object {
        const val PARAM_BLOG = "param_blog"
        fun enterDetail(activity: Activity,blog: Blog?){
            val intent = Intent(activity,DetailActivity::class.java)
            intent.putExtra(PARAM_BLOG,blog)
            activity.startActivity(intent)
        }
    }

    private lateinit var blog: Blog

    private lateinit var agentWeb: AgentWeb


    override val layoutId: Int = R.layout.activity_detail

    override fun initView(savedInstanceState: Bundle?) {
        blog = intent?.getParcelableExtra(PARAM_BLOG) ?: return
        mDataBinding.apply {
            tvDetailTitle.text = blog.title
            ivBack.setOnClickListener {
                finish()
            }
            ivMore.setOnClickListener {

            }
        }

        initWeb()

    }

    private fun initWeb() {
        agentWeb = AgentWeb.with(this)
            .setAgentWebParent(mDataBinding.flDetail, ViewGroup.LayoutParams(-1,-1))
            .useDefaultIndicator(getColor(R.color.textColorPrimary), 2)
            .interceptUnkownUrl()
            .setMainFrameErrorView(R.layout.item_reload,R.id.button_reload)
            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)
            .setWebChromeClient(object:WebChromeClient(){
                override fun onReceivedTitle(view: WebView?, title: String?) {
                    mDataBinding.tvDetailTitle.text = title
                    super.onReceivedTitle(view, title)
                }
            })
            .setWebViewClient(object: WebViewClient(){

                //白名单处理
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    return !whiteHostList().contains(request?.url?.host)

                }
                //加载js，去掉掘金、简书、CSDN等H5页面的Title、底部操作栏，以及部分广告
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    view?.loadUrl(customJs(url,blog.link))
                }

            })
            .createAgentWeb()
            .ready()
            .get()
        agentWeb.webCreator.webView.run {
            overScrollMode = WebView.OVER_SCROLL_NEVER
            settings.run {
                javaScriptCanOpenWindowsAutomatically = false
                loadsImagesAutomatically = true
                useWideViewPort = true
                loadWithOverviewMode = true
                //textZoom = SettingsStore.getWebTextZoom()
            }
        }

        agentWeb.urlLoader.loadUrl(blog.link)
    }



    override fun onPause() {
        super.onPause()
        agentWeb.webLifeCycle.onPause()
    }

    override fun onResume() {
        super.onResume()
        agentWeb.webLifeCycle.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        agentWeb.webLifeCycle.onDestroy()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if(agentWeb.handleKeyEvent(keyCode,event)){
            true
        }else{
            super.onKeyDown(keyCode, event)
        }
    }
}