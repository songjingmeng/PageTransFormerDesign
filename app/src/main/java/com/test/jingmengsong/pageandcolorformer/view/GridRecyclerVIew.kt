package com.test.jingmengsong.pageandcolorformer.view

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.GridLayoutAnimationController
import android.view.animation.LayoutAnimationController

/**
 * 业务名：
 * 功能说明：
 * 创建于：2018/9/7 on 10:53
 * 作者： jingmengsong
 * <p/>
 * 历史记录
 * 修改日期：
 * 修改人：
 * 修改内容：
 */
class GridRecyclerVIew @JvmOverloads constructor(context: Context, attrs: AttributeSet) : RecyclerView(context, attrs) {

    override fun attachLayoutAnimationParameters(child: View?, params: ViewGroup.LayoutParams?, index: Int, count: Int) {
        var animationParams: LayoutAnimationController.AnimationParameters?
        var mAnimationParams: GridLayoutAnimationController.AnimationParameters? = null
        //获取layoutManager 布局管理者
        val mLayoutManager = layoutManager
        if (adapter != null && mLayoutManager is GridLayoutManager) {
            animationParams = params?.layoutAnimationParameters
            if (animationParams == null) {
                // If there are no animation parameters, create new once and attach them to
                // the LayoutParams.
                mAnimationParams = GridLayoutAnimationController.AnimationParameters()
                params?.layoutAnimationParameters = mAnimationParams
            }

//          Next we are updating the parameters
//          Set the number of items in the RecyclerView and the index of this item
            mAnimationParams?.count = count
            mAnimationParams?.index = index
            // Calculate the number of columns and rows in the grid
            val columns = (mLayoutManager as GridLayoutManager).spanCount
            mAnimationParams?.columnsCount = columns
            mAnimationParams?.rowsCount = count / columns
            // Calculate the column/row position in the grid
            val invertedIndex = count - 1 - index
            mAnimationParams?.column = columns - 1 - (invertedIndex % columns)
            mAnimationParams?.row = mAnimationParams!!.rowsCount - 1 - invertedIndex / columns

        } else {
            // Proceed as normal if using another type of LayoutManager
            super.attachLayoutAnimationParameters(child, params, index, count)
        }

    }

}