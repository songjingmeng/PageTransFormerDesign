package com.test.jingmengsong.pageandcolorformer.transform

import android.animation.ArgbEvaluator
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.View
import com.test.jingmengsong.pageandcolorformer.R


/**
 * 业务名：
 * 功能说明：
 * 创建于：2018/9/3 on 18:11
 * 作者： jingmengsong
 * <p/>
 * 历史记录
 * 修改日期：
 * 修改人：
 * 修改内容：
 */
class HomePagerTransFormer : ViewPager.PageTransformer, ViewPager.OnPageChangeListener {

    private var mPageIndex = 0
    private var MIN_SCALE = 0.8f
    override fun transformPage(view: View, position: Float) {

        //获取mainActivity 的根布局 view
        val parentView = view.parent.parent as View

        val color1 = view.resources.getColor(android.R.color.holo_red_light)
        val color2 = view.resources.getColor(android.R.color.holo_blue_light)
        val color3 = view.resources.getColor(android.R.color.white)
        val color4 = view.resources.getColor(android.R.color.holo_orange_dark)
        val color5 = view.resources.getColor(android.R.color.holo_green_light)
        val color6 = view.resources.getColor(android.R.color.holo_purple)
        val tag = view.tag
        var color = color1

        //用于颜色渐变
        val argbEvaluator = ArgbEvaluator()
        //为了填坑所以传了个Tag
        if (tag == mPageIndex) {
            when (mPageIndex) {
                0 -> {
                    color = argbEvaluator.evaluate(Math.abs(position), color1, color2) as Int
                }
                1 -> {
                    color = argbEvaluator.evaluate(Math.abs(position), color2, color3) as Int
                }
                2 -> {
                    color = argbEvaluator.evaluate(Math.abs(position), color3, color4) as Int
                }
                3 -> {
                    color = argbEvaluator.evaluate(Math.abs(position), color4, color5) as Int
                }
                4 -> {
                    color = argbEvaluator.evaluate(Math.abs(position), color5, color6) as Int
                }
                5 -> {
                    color = argbEvaluator.evaluate(Math.abs(position), color6, color5) as Int
                }
                else -> {
                    return
                }
            }

            parentView.setBackgroundColor(color)
        }

        setPageScaleAnimator(position, view)


    }

    /**
     * 为 page页设置缩放动画
     */
    private fun setPageScaleAnimator(position: Float, view: View) {

        if (position < -1 || position > 1) {
            //对于不可见的两个页面设置为最小缩放值
            view.scaleX = MIN_SCALE
            view.scaleY = MIN_SCALE
        } else if (position <= 1) { // [-1,1]
            //对于可见 及 将要可见的的页面
            if (position < 0) {
                val scaleX = 1 + 0.2f * position
                view.scaleX = scaleX
                view.scaleY = scaleX
            } else {
                val scaleX = 1 - 0.2f * position
                view.scaleX = scaleX
                view.scaleY = scaleX
            }
        }

    }

    override fun onPageScrollStateChanged(p0: Int) {
    }

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
        mPageIndex = p0
        Log.e("位置pageIndex", "" + mPageIndex)
    }

    override fun onPageSelected(position: Int) {

    }
}