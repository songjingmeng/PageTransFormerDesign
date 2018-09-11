package com.test.jingmengsong.pageandcolorformer.fragment

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.test.jingmengsong.pageandcolorformer.R
import com.test.jingmengsong.pageandcolorformer.adapter.HomeFragmentAdapter
import android.support.v4.app.ActivityOptionsCompat
import android.content.Intent
import android.support.annotation.NonNull
import android.support.design.widget.TabLayout
import android.support.v4.util.Pair
import android.support.v4.view.ViewPager
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.widget.TableLayout
import com.test.jingmengsong.pageandcolorformer.ContentActivity
import com.test.jingmengsong.pageandcolorformer.adapter.HomeAttentionsAdapter
import com.test.jingmengsong.pageandcolorformer.adapter.HomePagerAdapter
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * 业务名：
 * 功能说明：
 * 创建于：2018/9/3 on 17:33
 * 作者： jingmengsong
 * <p/>
 * 历史记录
 * 修改日期：
 * 修改人：
 * 修改内容：
 */
class HomeFragment : Fragment() {

    companion object {
        private val EXTRA_KEY_POSITION = "position"
        fun getInstance(position: Int): HomeFragment {
            val homeFragment = HomeFragment()
            val bundle = Bundle()
            bundle.putInt(EXTRA_KEY_POSITION, position)
            homeFragment.arguments = bundle
            return homeFragment
        }
    }

    /**
     * Home Adapter
     */
    private val homeAdapter: HomeFragmentAdapter by lazy {
        var list = mutableListOf<String>()
        for (i in 0 until 100) {
            list.add("$i + 张三小妹")
        }
        HomeFragmentAdapter(list)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, null)
        val bundle = arguments as Bundle
        val position = bundle.getInt(EXTRA_KEY_POSITION)
        view.tag = position

        //为tab添加标签
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        val viewPager = view.findViewById<ViewPager>(R.id.vp_home_fragment)
        tabLayout.addTab(tabLayout.newTab().setText("个人动态"))
        tabLayout.addTab(tabLayout.newTab().setText("讨论组"))

        //为viewpager设置适配器
        viewPager.run {
            var fragments = mutableListOf<Fragment>()
            for(i in 0 until 2)
            {
                fragments.add(AttentionsFragment.getInstance(i))
            }
            adapter = HomePagerAdapter(childFragmentManager, fragments)
        }

       viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener(){
           override fun onPageSelected(position: Int) {
               tabLayout.getTabAt(position)?.select()
           }
       })

        return view
    }





}