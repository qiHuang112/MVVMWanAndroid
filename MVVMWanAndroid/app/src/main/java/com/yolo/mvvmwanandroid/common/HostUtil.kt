package com.yolo.mvvmwanandroid.common

import android.net.Uri

/**
 * @author yolo.huang
 * @date 2020/9/21
 * 允许连接的Host白名单
 * 参照: https://github.com/goweii/WanAndroid
 */
fun whiteHostList() = arrayOf(
    "www.wanandroid.com",
    "study.163.com",
    "juejin.im",
    "www.jianshu.com",
    "mp.weixin.qq.com",
    "blog.csdn.net",
    "github.com",
    "gitee.com",
    "www.oschina.net",
    "developers.google.cn",
    "stackoverflow.com",
    "www.androidweekly.cn",
    "hm.baidu.com",
    "www.github.com",
    "www.csdn.net",
    "toutiao.io",
    "segmentfault.com",
    "www.androiddevtools.cn",
    "developers.googleblog.cn",
    "gank.io",
    "a.codekk.com",
    "xiaozhuanlan.com",
    "www.androidos.net.cn",
    "design.1sters.com",
    "leetcode.com",
    "dl.google.com",
    "jcenter.bintray.com",
    "mvnrepository.com",
    "maven.aliyun.com",
    "tech.meituan.com",
    "joyrun.github.io",
    "kaolamobile.github.io",
    "tech.youzan.com",
    "zhuanlan.zhihu.com",
    "www.apkbus.com",
    "www.chinagdg.com",
    "www.v2ex.com",
    "bbs.51cto.com",
    "www.cnblogs.com",
    "www.ruanyifeng.com",
    "hukai.me",
    "droidyue.com",
    "kymjs.com",
    "weishu.me",
    "gityuan.com",
    "www.gcssloop.com",
    "www.wjdiankong.cn",
    "hencoder.com",
    "www.jowanxu.top",
    "guolei1130.github.io",
    "yifeng.studio",
    "prototypez.github.io",
    "www.androidperformance.com",
    "toughcoder.net",
    "wl9739.github.io",
    "www.androidblog.cn",
    "yrom.net",
    "kaedea.com",
    "yifeiyuan.me",
    "pqpo.me",
    "yutiantina.github.io",
    "blog.imallen.wang",
    "wossoneri.github.io",
    "www.iconfont.cn",
    "tinypng.com",
    "translate.google.cn",
    "www.pdfpai.com",
    "ezgif.com",
    "www.aigei.com",
    "www.imooc.com",
    "www.jikexueyuan.com",
    "ke.qq.com",
    "www.maiziedu.com",
    "mooc.study.163.com",
    "www.nowcoder.com",
    "luo.apkbus.com",
    "mars.apkbus.com",
    "gitbook.cn",
    "androidxref.com",
    "www.upyun.com",
    "www.bmob.cn",
    "fir.im",
    "www.rongcloud.cn",
    "www.easemob.com",
    "www.xfyun.cn",
    "www.faceplusplus.com.cn",
    "www.qiniu.com",
    "www.pgyer.com",
    "bugly.qq.com",
    "developer.baidu.com",
    "www.juhe.cn",
    "dev.mi.com",
    "www.jiguang.cn",
    "tutucloud.com",
    "xg.qq.com",
    "www.umeng.com",
    "readhub.me",
    "wwv.cyzone.cn",
    "36kr.com",
    "www.pingwest.com",
    "sspai.com",
    "www.pmtown.com",
    "www.zhaopin.com",
    "www.lagou.com",
    "cn.100offer.com",
    "www.neitui.me",
    "www.zhipin.com",
    "jiagu.360.cn",
    "dun.163.com",
    "www.ijiami.cn",
    "pay.weixin.qq.com",
    "open.alipay.com",
    "developer.huawei.com",
    "push.baidu.com",
    "sharesdk.mob.com",
    "mobile.umeng.com",
    "lbs.amap.com",
    "lbsyun.baidu.com",
    "lbs.qq.com",
    "cloud.tencent.com",
    "cloud.baidu.com",
    "baichuan.taobao.com",
    "leancloud.cn",
    "x5.tencent.com",
    "baozoumanhua.com",
    "www.chuangkit.com",
    "unsplash.com",
    "iconstore.co",
    "www.uplabs.com",
    "www.lottiefiles.com",
    "cn.data.cmcm.com",
    "mtj.baidu.com",
    "qmuiteam.com",
    "dev.360.cn",
    "tucao.qq.com",
    "docs.qq.com",
    "yuque.com",
    "flutter.link",
    "www.beian.miit.gov.cn",
    "www.baidu.com",
    "www.google.com",
    "www.sogou.com",
    "www.so.com",
    "cn.bing.com",
    "weixin.sogou.com",
    "sug.so.360.cn",
    "market.geekbang.org",
    "www.bilibili.com"
)

/**
 * 加载js，去掉掘金、简书、CSDN等H5页面的Title、底部操作栏，以及部分广告
 */
fun customJs(url: String?,default:String?): String {
    val js = StringBuilder()
    js.append("javascript:(function(){")
    val newUrl = url?:default
    when (Uri.parse(newUrl).host) {
        "juejin.im" -> {
            js.append("var headerList = document.getElementsByClassName('main-header-box');")
            js.append("if(headerList&&headerList.length){headerList[0].parentNode.removeChild(headerList[0])}")
            js.append("var openAppList = document.getElementsByClassName('open-in-app');")
            js.append("if(openAppList&&openAppList.length){openAppList[0].parentNode.removeChild(openAppList[0])}")
            js.append("var actionBox = document.getElementsByClassName('action-box');")
            js.append("if(actionBox&&actionBox.length){actionBox[0].parentNode.removeChild(actionBox[0])}")
            js.append("var actionBarList = document.getElementsByClassName('action-bar');")
            js.append("if(actionBarList&&actionBarList.length){actionBarList[0].parentNode.removeChild(actionBarList[0])}")
            js.append("var columnViewList = document.getElementsByClassName('column-view');")
            js.append("if(columnViewList&&columnViewList.length){columnViewList[0].style.margin = '0px'}")
        }
        "www.jianshu.com" -> {
            js.append("var jianshuHeader = document.getElementById('jianshu-header');")
            js.append("if(jianshuHeader){jianshuHeader.parentNode.removeChild(jianshuHeader)}")
            js.append("var headerShimList = document.getElementsByClassName('header-shim');")
            js.append("if(headerShimList&&headerShimList.length){headerShimList[0].parentNode.removeChild(headerShimList[0])}")
            js.append("var fubiaoList = document.getElementsByClassName('fubiao-dialog');")
            js.append("if(fubiaoList&&fubiaoList.length){fubiaoList[0].parentNode.removeChild(fubiaoList[0])}")
            js.append("var ads = document.getElementsByClassName('note-comment-above-ad-wrap');")
            js.append("if(ads&&ads.length){ads[0].parentNode.removeChild(ads[0])}")

            js.append("var lazyShimList = document.getElementsByClassName('v-lazy-shim');")
            js.append("if(lazyShimList&&lazyShimList.length&&lazyShimList[0]){lazyShimList[0].parentNode.removeChild(lazyShimList[0])}")
            js.append("if(lazyShimList&&lazyShimList.length&&lazyShimList[1]){lazyShimList[1].parentNode.removeChild(lazyShimList[1])}")
        }
        "blog.csdn.net" -> {
            js.append("var csdnToolBar = document.getElementById('csdn-toolbar');")
            js.append("if(csdnToolBar){csdnToolBar.parentNode.removeChild(csdnToolBar)}")
            js.append("var csdnMain = document.getElementById('main');")
            js.append("if(csdnMain){csdnMain.style.margin='0px'}")
            js.append("var operate = document.getElementById('operate');")
            js.append("if(operate){operate.parentNode.removeChild(operate)}")
        }
    }
    js.append("})()")
    return js.toString()
}
