package com.yolo.mvvmwanandroid.ui.fragment

import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import com.yolo.mvvm.fragment.BaseFragment
import com.yolo.mvvm.util.ImageOptions
import com.yolo.mvvm.util.loadImage
import com.yolo.mvvm.view.banner.BannerView
import com.yolo.mvvmwanandroid.ui.activity.DetailActivity
import com.yolo.mvvmwanandroid.R
import com.yolo.mvvmwanandroid.databinding.FragmentHomeBinding
import com.yolo.mvvmwanandroid.network.bean.BannerBean
import com.yolo.mvvmwanandroid.network.bean.Blog
import com.yolo.mvvmwanandroid.ui.widget.ScrollToTop
import com.yolo.mvvmwanandroid.viewmodel.HomeFragmentViewModel
import kotlinx.android.synthetic.main.item_reload.view.*

/**
 * 首页
 */
class HomeFragment : BaseFragment<HomeFragmentViewModel, FragmentHomeBinding>(),ScrollToTop {

    private lateinit var fragments:List<Fragment>

    override val layoutId = R.layout.fragment_home

    companion object{
        val instance = HomeFragment()
    }

    override fun initView() {
        mDataBinding.apply {
            listener =
                object : BannerView.BannerItemOnClickListener {
                    override fun onItemClick(item: BannerView.BannerItemData) {
                        if(item is BannerBean){
                            DetailActivity.enterDetail(mActivity,Blog(title = item.title,link = item.url))
                        }
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
            viewModel = mViewModel
            reloadView.button_reload.setOnClickListener {
                getData()
            }
        }
        initFragment()
    }

    override fun getData() {
        mViewModel.getBanner()
    }

    private fun initFragment() {

        fragments = listOf(
            HomeBlogFragment(), ProjectFragment(),PlazaFragment(),AnswerFragment()
        )

        val tabTitle = listOf<String>(
            getString(R.string.tab_hot),
            getString(R.string.tab_project),
            getString(R.string.tab_plaza),
            getString(R.string.tab_answer)
        )

        mDataBinding.homeViewpager.adapter = object :FragmentPagerAdapter(childFragmentManager,
            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
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

            override fun getItemId(position: Int): Long {
                return fragments[position].hashCode().toLong()
            }

        }
        mDataBinding.homeViewpager.offscreenPageLimit = fragments.size

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

    override fun scrollToTop() {
        if (!this::fragments.isInitialized) return
        val currentFragment = fragments[mDataBinding.homeViewpager.currentItem]
        if (currentFragment is ScrollToTop && currentFragment.isVisible) {
            currentFragment.scrollToTop()
        }
    }


}