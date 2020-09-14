package com.yolo.mvvmwanandroid.view.fragment

import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import com.yolo.mvvm.fragment.BaseFragment
import com.yolo.mvvm.util.ImageOptions
import com.yolo.mvvm.util.loadImage
import com.yolo.mvvm.view.banner.BannerView
import com.yolo.mvvmwanandroid.R
import com.yolo.mvvmwanandroid.databinding.FragmentHomeBinding
import com.yolo.mvvmwanandroid.network.bean.BannerBean
import com.yolo.mvvmwanandroid.viewmodel.HomeFragmentViewModel

/**
 * 首页
 */
class HomeFragment : BaseFragment<HomeFragmentViewModel, FragmentHomeBinding>() {

    private lateinit var fragments:List<Fragment>

    override val layoutId = R.layout.fragment_home

    companion object{
        val instance = HomeFragment()
    }

    override fun initView() {
        mDataBinding.listener =
            object : BannerView.BannerItemOnClickListener {
                override fun onItemClick(item: BannerView.BannerItemData) {

                }

                override fun onShowItemView(imageView: ImageView, imageUrl: String) {
                    imageView.loadImage(
                        fragment = this@HomeFragment,
                        url = imageUrl,
                        imageOptions = ImageOptions().apply {
                            placeholder = R.drawable.shape_bg_image_default
                        })
                }
            }

        mViewModel.banner.observe(this,
            Observer<List<BannerBean>> { t -> mDataBinding.homeBanner.setData(t) })

        mViewModel.getBanner()

        fragments = listOf(
            HomeBlogFragment(), ProjectFragment(),HomeBlogFragment(),HomeBlogFragment()
        )

        val tabTitle = listOf<String>(
            getString(R.string.tab_hot),
            getString(R.string.tab_project),
            getString(R.string.tab_plaza),
            getString(R.string.article)
        )

        mDataBinding.homeViewpager.adapter = object :FragmentStatePagerAdapter(childFragmentManager,
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ){
            override fun getItem(position: Int): Fragment {
                return fragments[position]
            }

            override fun getCount(): Int {
                return fragments.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return tabTitle[position]
            }
        }

        mDataBinding.homeTab.setupWithViewPager(mDataBinding.homeViewpager)

    }

    override fun onResume() {
        super.onResume()
        mDataBinding.homeBanner.startAutoScroll()
    }

    override fun onPause() {
        super.onPause()
        mDataBinding.homeBanner.stopAutoScroll()

    }
}