package com.yolo.mvvmwanandroid.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yolo.mvvm.util.copyTextIntoClipboard
import com.yolo.mvvm.util.showToast
import com.yolo.mvvmwanandroid.R
import com.yolo.mvvmwanandroid.network.bean.Blog
import com.yolo.mvvmwanandroid.ui.activity.DetailActivity
import com.yolo.mvvmwanandroid.ui.activity.DetailActivity.Companion.PARAM_BLOG
import kotlinx.android.synthetic.main.fragment_more.*

/**
 * @author yolo.huang
 * @date 2020/9/21
 */
class MoreFragment:BottomSheetDialogFragment(){

    companion object{
        fun instance(blog: Blog):MoreFragment{
            return MoreFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(PARAM_BLOG, blog)

                }
            }
        }
    }

    private var behavior: BottomSheetBehavior<View>? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_more,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.apply {
            val blog = getParcelable<Blog>(PARAM_BLOG)?:return

            llCollect.visibility = if(blog.id!=0) View.VISIBLE else View.GONE
            ivCollect.isSelected = blog.collect
            llCollect.setOnClickListener {
                behavior?.state = BottomSheetBehavior.STATE_HIDDEN

            }

            llExplorer.setOnClickListener {
                startActivity(Intent().apply {
                    action = Intent.ACTION_VIEW
                    data = Uri.parse(blog.link)
                })
                behavior?.state = BottomSheetBehavior.STATE_HIDDEN

            }

            llCopy.setOnClickListener {
                context?.copyTextIntoClipboard(blog.link,blog.title)
                context?.showToast(R.string.copy_success)
                behavior?.state = BottomSheetBehavior.STATE_HIDDEN
            }

            llRefresh.setOnClickListener {
                (activity as? DetailActivity)?.refreshPage()
                behavior?.state = BottomSheetBehavior.STATE_HIDDEN
            }
        }
    }


    override fun onStart() {
        super.onStart()
        val bottomSheet: View = (dialog as BottomSheetDialog).delegate
            .findViewById(com.google.android.material.R.id.design_bottom_sheet)
            ?: return
        behavior = BottomSheetBehavior.from(bottomSheet)
        behavior?.state = BottomSheetBehavior.STATE_EXPANDED

    }

    fun show(manager: FragmentManager) {
        if (!this.isAdded) {
            super.show(manager, "ActionFragment")
        }
    }
}