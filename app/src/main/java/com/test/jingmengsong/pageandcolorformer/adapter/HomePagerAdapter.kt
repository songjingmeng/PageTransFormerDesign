package com.test.jingmengsong.pageandcolorformer.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter


/**
 * 业务名：
 * 功能说明：
 * 创建于：2018/9/3 on 17:49
 * 作者： jingmengsong
 * <p/>
 * 历史记录
 * 修改日期：
 * 修改人：
 * 修改内容：
 */
class HomePagerAdapter(fm: FragmentManager, val mutableList: MutableList<Fragment>) : FragmentStatePagerAdapter(fm) {


    override fun getItem(position: Int): Fragment = mutableList[position]

    override fun getCount(): Int = mutableList.size
}