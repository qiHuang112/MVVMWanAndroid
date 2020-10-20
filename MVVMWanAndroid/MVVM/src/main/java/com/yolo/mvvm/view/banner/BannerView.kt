package com.yolo.mvvm.view.banner

import android.content.Context
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.yolo.mvvm.R
import com.yolo.mvvm.util.ext.LocalHandler
import com.yolo.mvvm.view.viewpager.BaseViewPager

/**
 * @author yolo.huang
 * @date 2020/6/29
 */
class BannerView @JvmOverloads constructor(
    mContext: Context, attrs: AttributeSet? = null,
    defStyleAttr:Int = 0) : LinearLayout(mContext,attrs,defStyleAttr)  {

    var mHandler:LocalHandler = LocalHandler()

    var mViewPager:BaseViewPager
    var mBannerIndicator: BannerIndicator

    var mBannerItemOnClickListener: BannerItemOnClickListener? = null

    private var listData =ArrayList<BannerItemData>()

    var mAdapter:MyViewPagerAdapter

    private var scrolling = false
    private var scrollable = false

    companion object{
        const val MSG_EVENT_START_AUTO_SCROLL = 1

        const val WAITTING_TIME = 5000L
    }

    init {
        /**
         * 此handler用来处理banner轮转
         */
        mHandler.registerHandleCallback(Handler.Callback { msg ->
            doHandlerCallback(msg)
            true
        })

        val content = LayoutInflater.from(mContext)
            .inflate(R.layout.view_banner_layout,this,true)

        mViewPager = content.findViewById(R.id.view_pager)
        mBannerIndicator = content.findViewById(R.id.banner_indicator)
        mViewPager.offscreenPageLimit = 2
        mAdapter = MyViewPagerAdapter(mContext,R.layout.banner_view_item_layout)
        mViewPager.adapter = mAdapter
        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                mBannerIndicator.index = (mAdapter.getRealPosition(mViewPager.currentItem))
            }
        })
    }

    /**
     * 添加数据
     */
    fun setData(items:List<BannerItemData>?){
        listData.clear()
        if (!items.isNullOrEmpty()) {
            listData.addAll(items)
        }
        mAdapter.setItems(listData)

        //大于1条数据时才能滚动
        if (listData.size > 1) {
            scrollable = true
            //显示指示点
            mBannerIndicator.visibility = View.VISIBLE
            mBannerIndicator.count = mAdapter.getRealCount()
            mBannerIndicator.index = mAdapter.getRealPosition(mViewPager.currentItem)
            mViewPager.currentItem = mAdapter.getRealCount() * 10
        } else {
            scrollable = false
            //不显示指示点
            mBannerIndicator.visibility = View.GONE
        }

        mViewPager.scrollble = scrollable

        startAutoScroll()
    }

    /**
     * 开始轮播
     */
    fun startAutoScroll() {
        if (!scrollable) {
            return
        }
        if (!scrolling) {
            scrolling = true
            mHandler.sendEmptyMessageDelayed(MSG_EVENT_START_AUTO_SCROLL, WAITTING_TIME)
        }
    }


    /**
     * 停止轮播
     */
    fun stopAutoScroll() {
        scrolling = false
        mHandler.removeMessages(MSG_EVENT_START_AUTO_SCROLL)
    }

    /**
     * 当view被销毁时，注销handler消息处理
     */
    fun destroy() {
        mHandler.unRegisterHandleCallback()
    }

    /**
     * 当bannerView不可见的时候，停止轮播
     */
    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)
        if (visibility == View.VISIBLE) {
            startAutoScroll()
        } else {
            stopAutoScroll()
        }
    }

    /**
     * 当bannerView被触摸时，停止轮播，ACTION_UP后开始轮播
     */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val action = ev.action
        if (action == MotionEvent.ACTION_DOWN) {
            stopAutoScroll()
        } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
            startAutoScroll()
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * handler消息处理具体方法,这里做banner轮转
     */
    private fun doHandlerCallback(msg: Message) {
        when(msg.what){
            MSG_EVENT_START_AUTO_SCROLL ->{
                val count = mAdapter.count
                if(count>1){
                    val page = (mViewPager.currentItem+1) % count
                    mViewPager.currentItem = page
                    mHandler.sendEmptyMessageDelayed(MSG_EVENT_START_AUTO_SCROLL, WAITTING_TIME)
                }
            }
        }
    }


    /**
     * 内部adapter类
     */
    inner class MyViewPagerAdapter(private val mContext: Context, private val mLayoutId: Int) :
        PagerAdapter() {

        private var mItems =  ArrayList<BannerItemData>()

        private val mInflater:LayoutInflater = LayoutInflater.from(mContext)

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            //实例化
            val itemView = mInflater.inflate(mLayoutId, null)
            val imageView =
                itemView.findViewById<View>(R.id.image_view) as ImageView
            val item = mItems[getRealPosition(position)]
            val imgUrl: String = item.imageUrl

            imageView.tag = item
            imageView.setOnClickListener { v ->
                val data = v.tag as BannerItemData
                if (mBannerItemOnClickListener != null) {
                    mBannerItemOnClickListener?.onItemClick(data)
                }
            }
            if (mBannerItemOnClickListener != null) {
                mBannerItemOnClickListener?.onShowItemView(imageView, imgUrl)
            }
            container.addView(itemView)
            return itemView
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        /**
         * 之所以乘以一百，是为了左右一直轮播
         */
        override fun getCount(): Int {
            return getRealCount()*100
        }

        fun getRealCount(): Int {
            return mItems.size
        }

        fun getRealPosition(position: Int): Int {
            return if (getRealCount() == 0) {
                0
            } else position % getRealCount()
        }

        fun setItems(itemList: List<BannerItemData>) {
            mItems.clear()
            if (itemList.isNotEmpty()) {
                mItems.addAll(itemList)
            }
            notifyDataSetChanged()
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

    }


    interface BannerItemData {
        val imageUrl: String
    }

    interface BannerItemOnClickListener {
        //item 点击事件
        fun onItemClick(item: BannerItemData)
        //item show事件，需要我们自行设置加载image(考虑到不同界面的默认图片可能不同，所以交给外部来处理)
        fun onShowItemView(
            imageView: ImageView,
            imageUrl: String
        )
    }


}