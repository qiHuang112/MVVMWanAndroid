package com.yolo.mvvm.view.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.*

/**
 * @author yolo.huang
 * @date 2020/6/28
 */
abstract class BaseRecyclerAdapter<T>(var diffCallback: DiffUtil.ItemCallback<T>) :
    RecyclerView.Adapter<BaseBindingViewHolder<ViewDataBinding>>() {

    private var mSelectionPosition = -1
    var itemListener: OnItemClickListener? = null
    var itemLongListener: OnItemLongClickListener? = null

    //统一的头部view和底部view
    private var mHeaderView: Int = -1
    private var mFooterView: Int = -1

    companion object {
        //防止多次点击的time，后续可以用aop来避免多次点击
        const val CLICK_SPACE_TIME = 500

        //头部，底部和普通的viewType
        const val TYPE_HEADER = -1
        const val TYPE_FOOTER = -2
        const val TYPE_NORMAL = 0
    }

    var mHelper: AsyncListDiffer<T> = AsyncListDiffer(
        AdapterListUpdateCallback(this),
        AsyncDifferConfig.Builder(diffCallback).build()
    )

    open fun submitList(list: List<T>?) {
        mHelper.submitList(list)
    }

    protected open fun getItem(position: Int): T {
        var realPosition = position
        if (isHasHeader()) {
            realPosition -= 1
        }
        return mHelper.currentList[realPosition]
    }

    /**
     * 这里有针对mHeaderView和mFooterView做处理
     */
    override fun getItemCount(): Int {
        var size = mHelper.currentList.size

        if (isHasHeader()) {
            size += 1
        }
        if (isHasFooter()) {
            size += 1
        }
        return size
    }

    private fun isHasHeader(): Boolean {
        return mHeaderView != -1
    }

    private fun isHasFooter(): Boolean {
        return mFooterView != -1
    }

    open fun setHeaderView(headerView: Int) {
        mHeaderView = headerView
        notifyItemInserted(0)
    }

    open fun setFooterView(footerView: Int) {
        mFooterView = footerView
        var position = mHelper.currentList.size
        if (isHasHeader()) {
            position += 1
        }
        notifyItemInserted(position)
    }

    override fun getItemViewType(position: Int): Int {
        if (isHasHeader() && position == 0) {
            return TYPE_HEADER
        }
        /**
         * 注意这里itemCount是有经过重新计算的，详情见getItemCount()
         */
        if (isHasFooter() && position == itemCount - 1) {
            return TYPE_FOOTER
        }

        return getItemViewTypeFinal(position)
    }

    /**
     * 如果子类有多个type,需要重写这个方法，不要直接重写getItemViewType，因为会影响头部和底部view的计算
     */
    open fun getItemViewTypeFinal(position: Int): Int {
        return TYPE_NORMAL
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseBindingViewHolder<ViewDataBinding> {
        if (isHasHeader() && viewType == TYPE_HEADER) {
            return BaseBindingViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    getLayoutId(mHeaderView),
                    parent,
                    false
                )
            )
        }
        if (isHasFooter() && viewType == TYPE_FOOTER) {
            return BaseBindingViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    getLayoutId(mFooterView),
                    parent,
                    false
                )
            )
        }
        return BaseBindingViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                getLayoutId(viewType),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BaseBindingViewHolder<ViewDataBinding>, position: Int) {
        if (getItemViewType(position) == TYPE_HEADER) {
            return
        }
        if (getItemViewType(position) == TYPE_FOOTER) {
            return
        }
        var finalPotion = position
        if (isHasHeader()) {
            finalPotion -= 1
        }
        val data = getItem(finalPotion)
        setVariable(data, finalPotion, holder)
        holder.binding.executePendingBindings()
        holder.binding.root.setOnClickListener { v -> itemListener?.onItemClick(finalPotion, v) }
        holder.binding.root.setOnLongClickListener { v ->
            itemLongListener?.onItemLongClick(finalPotion, v)
            false
        }
    }


    fun updateSelectedPosition(position: Int) {
        this.mSelectionPosition = position
        notifyDataSetChanged()
    }

    /**
     * 获取对应的 item 布局
     */
    abstract fun getLayoutId(viewType: Int): Int

    /**
     * 与 dataBinding 互相绑定的数据操作
     * @param data 列表中当前 position 的数据
     * @param position 数据的位置
     * @param holder
     */
    abstract fun setVariable(data: T, position: Int, holder: BaseBindingViewHolder<ViewDataBinding>)

    /**
     * 移除指定的item
     */
    fun removeItem(position: Int) {
        if (position in 0 until itemCount)
            mHelper.currentList.let {
                it.removeAt(position)
                notifyItemRemoved(position)
                if (position != itemCount) {
                    notifyItemRangeChanged(position, itemCount - position)
                }
            }
    }


    interface OnItemClickListener {
        fun onItemClick(position: Int, view: View)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(position: Int, view: View)
    }

}
