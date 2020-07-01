package com.yolo.mvvm.view.recyclerview

import android.content.Context
import android.os.SystemClock
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author yolo.huang
 * @date 2020/6/28
 */
abstract class BaseRecyclerViewAdapter<T>(var mContext: Context,var layoutId:Int = 0):RecyclerView.Adapter<BaseRecyclerViewHolder>() {

    companion object{
        //防止多次点击的time，后续可以用aop来避免多次点击
        const val CLICK_SPACE_TIME = 500

        //头部，底部和普通的viewType
        const val TYPE_HEADER = -1
        const val TYPE_FOOTER = -2
        const val TYPE_NORMAL = 0
    }

    private var mItems:ArrayList<T> = ArrayList()

    //统一的头部view和底部view
    private var mHeaderView: View? = null
    private var mFooterView: View? = null

    private var mOnItemClickListener: OnItemClickListener? = null
    private var mOnLongItemClickListener: OnLongItemClickListener? = null

    private var mLastClickTime: Long = 0




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder {
        if(isHasHeader() && viewType == TYPE_HEADER){
            return BaseRecyclerViewHolder.get(parent,mHeaderView!!,viewType)
        }
        if(isHasFooter() && viewType == TYPE_FOOTER){
            return BaseRecyclerViewHolder.get(parent,mFooterView!!,viewType)
        }

        val viewHolder = createNormalViewHolder(parent,viewType)

        setItemListener(viewHolder)
        return viewHolder
    }


    /**
     * 这里有针对mHeaderView和mFooterView做处理
     */
    override fun getItemCount(): Int {
        var size = mItems.size

        if(isHasHeader()){
            size+=1
        }
        if(isHasFooter()){
            size+=1
        }
        return size
    }

    override fun onBindViewHolder(holder: BaseRecyclerViewHolder, position: Int) {
        if(getItemViewType(position) == TYPE_HEADER){
            return
        }
        if(getItemViewType(position) == TYPE_FOOTER){
            return
        }

        /**
         * 这里需要计算真实位置
         */
        var finalPotion = position
        if(isHasHeader()){
            finalPotion -=1
        }
        val item = if (mItems.size > 0 && finalPotion < mItems.size) mItems[finalPotion] else null
        /**
         * 子类需要重写bindItemViewHolder方法来对holder中view进行数据绑定
         */
        bindItemViewHolder(holder,item,finalPotion)

        // 更新item position
        holder.mItemView.tag = finalPotion
    }

    abstract fun bindItemViewHolder(holder: BaseRecyclerViewHolder, item: T?, finalPotion: Int)


    private fun createNormalViewHolder(parent: ViewGroup, viewType: Int):BaseRecyclerViewHolder{
        return BaseRecyclerViewHolder.get(parent,layoutId,viewType)
    }

    override fun getItemViewType(position: Int): Int {
        if(isHasHeader() && position == 0){
            return TYPE_HEADER
        }
        /**
         * 注意这里itemCount是有经过重新计算的，详情见getItemCount()
         */
        if(isHasFooter() && position == itemCount-1){
            return TYPE_FOOTER
        }

        return getItemViewTypeFinal(position)
    }

    /**
     * 如果子类有多个type,需要重写这个方法，不要直接重写getItemViewType，因为会影响头部和底部view的计算
     */
    private fun getItemViewTypeFinal(position: Int): Int {
        return TYPE_NORMAL
    }

    /**
     * 这里对GridLayoutManager样式的头部和底部view进行处理
     */
    override fun onViewAttachedToWindow(holder: BaseRecyclerViewHolder) {
        super.onViewAttachedToWindow(holder)
        val layoutParams: ViewGroup.LayoutParams = holder.itemView.layoutParams
        if (layoutParams is StaggeredGridLayoutManager.LayoutParams) {
            if (getItemViewType(holder.layoutPosition) == TYPE_HEADER ||
                getItemViewType(holder.layoutPosition) == TYPE_FOOTER) {
                val params: StaggeredGridLayoutManager.LayoutParams = layoutParams
                params.isFullSpan = true
            }
        }
    }

    /**
     * 对itemView设置点击事件
     */
    private fun setItemListener(viewHolder: BaseRecyclerViewHolder) {
        viewHolder.mItemView.setOnClickListener { view ->
            handleViewClick(
                view,
                viewHolder.layoutPosition
            )
        }
        viewHolder.mItemView.setOnLongClickListener { view ->
            handleViewLongClick(
                view,
                viewHolder.layoutPosition
            )
        }
    }

    private fun handleViewClick(v: View, position: Int) {
        val time = SystemClock.elapsedRealtime()
        if (time - mLastClickTime < CLICK_SPACE_TIME) {
            return
        }
        mLastClickTime = time
        if (mOnItemClickListener != null) {
            mOnItemClickListener!!.onItemClick(v, position)
        }
    }

    private fun handleViewLongClick(v: View, position: Int): Boolean {
        return if (mOnLongItemClickListener != null) {
            mOnLongItemClickListener!!.onLongClick(v, position)
        } else false
    }

    private fun isHasHeader(): Boolean {
        return mHeaderView != null
    }

    private fun isHasFooter(): Boolean {
        return mFooterView != null
    }

    open fun setHeaderView(headerView: View) {
        mHeaderView = headerView
        notifyItemInserted(0)
    }

    open fun setFooterView(footerView: View) {
        mFooterView = footerView
        var position = mItems.size
        if (isHasHeader()) {
            position += 1
        }
        notifyItemInserted(position)
    }

    /**---------针对item的相关处理start------*/
    open fun setItem(position: Int, t: T): Unit {
        if (mItems != null && mItems.size > position) {
            mItems[position] = t
            notifyItemChanged(position)
        }
    }

    open fun moveItemToHead(position: Int) {
        if (mItems != null && mItems.size > position) {
            mItems.add(0, mItems.removeAt(position))
            notifyItemRangeChanged(0, position)
        }
    }

    open fun clearData() {
        mItems.clear()
        notifyDataSetChanged()
    }


    open fun setItems(itemList: List<T>?) {
        mItems.clear()
        if (itemList != null && itemList.isNotEmpty()) {
            mItems.addAll(itemList)
        }
        notifyDataSetChanged()
    }

    open fun addItems(itemList: List<T>?) {
        if (itemList != null && itemList.isNotEmpty()) {
            mItems.addAll(itemList)
            notifyItemRangeInserted(mItems.size - itemList.size, mItems.size)
            notifyItemChanged(mItems.size - itemList.size, mItems.size)
        }
    }

    open fun addItems(position: Int, itemList: List<T>?) {
        if (itemList != null && itemList.isNotEmpty()) {
            mItems.addAll(position, itemList)
            notifyItemRangeInserted(0, itemList.size)
            notifyItemChanged(0, mItems.size)
        }
    }


    open fun addItem(item: T?) {
        if (item == null) {
            return
        }
        val position = mItems.size
        addItem(item, position)
    }

    open fun addItem(item: T?, position: Int) {
        if (item == null) {
            return
        }
        if (position < 0 || position > mItems.size) {
            return
        }
        mItems.add(position, item)
        notifyItemInserted(position)
        notifyItemRangeChanged(position, mItems.size - position)
    }

    open fun removeItem(position: Int) {
        if (position < 0 || position > mItems.size) {
            return
        }
        mItems.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, mItems.size - position)
    }

    open fun updateItem(position: Int, item: T) {
        if (position < 0 || position > mItems.size) {
            return
        }
        mItems[position] = item
        notifyItemChanged(position)
    }

    open fun updateItem(item: T?) {
        if (item == null) {
            return
        }
        val position = mItems.indexOf(item)
        updateItem(position, item)
    }

    open fun removeItem(item: T?) {
        if (item == null) {
            return
        }
        val position = mItems.indexOf(item)
        removeItem(position)
    }

    open fun moveItem(fromPosition: Int, toPosition: Int) {
        // 原数组数据的移动
        Collections.swap(mItems, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    open fun indexItem(item: T): Int {
        return mItems.indexOf(item)
    }

    open fun onMove(fromPosition: Int, toPosition: Int) {
        moveItem(fromPosition, toPosition)
    }

    open fun onSwipe(position: Int) {
        removeItem(position)
    }

    /**---------针对item的相关处理 end------*/


    /**
     * 点击接口
     */
    interface OnItemClickListener {
        fun onItemClick(itemView: View?, position: Int)
    }

    /**
     * 长按接口
     */
    interface OnLongItemClickListener {
        fun onLongClick(itemView: View?, position: Int): Boolean
    }
}