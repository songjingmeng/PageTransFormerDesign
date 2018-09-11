package com.test.jingmengsong.pageandcolorformer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.test.jingmengsong.pageandcolorformer.adapter.HomePagerAdapter
import com.test.jingmengsong.pageandcolorformer.fragment.HomeFragment
import com.test.jingmengsong.pageandcolorformer.transform.HomePagerTransFormer
import kotlinx.android.synthetic.main.activity_main.*
import android.view.View
import android.widget.Toast
import com.test.jingmengsong.pageandcolorformer.adapter.HomeAttentionsAdapter
import kotlinx.android.synthetic.main.attention_persons.*



class MainActivity : AppCompatActivity() {

    private var clickPosition = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homePagerTransFormer = HomePagerTransFormer()
        vp_main.run {
            adapter = HomePagerAdapter(supportFragmentManager, initFragment())
            addOnPageChangeListener(homePagerTransFormer)
            setPageTransformer(true, homePagerTransFormer)
        }


        iv_home_main.setOnClickListener {

            if(clickPosition % 2 == 0)
            {
                Toast.makeText(this@MainActivity, "点击了中间按钮", Toast.LENGTH_SHORT).show()
                attentions_layout.visibility = View.VISIBLE
                rv_attention_persons.run{
                    layoutManager = GridLayoutManager(this@MainActivity, 3)
                    adapter = homeAdapter
                    itemAnimator = DefaultItemAnimator()
                }
            }else{
                attentions_layout.visibility = View.GONE
            }

            clickPosition++
        }

        tv_left_main.setOnClickListener {
            Toast.makeText(this@MainActivity, "点击了左侧按钮", Toast.LENGTH_SHORT).show()
        }


        tv_right_main.setOnClickListener {
            Toast.makeText(this@MainActivity, "点击了肉测按钮", Toast.LENGTH_SHORT).show()
        }


    }

    /**
     * Home Adapter
     */
    private val homeAdapter: HomeAttentionsAdapter by lazy {
        var list = mutableListOf<String>()
        for (i in 0 until 5) {
            list.add("$i + 张三小妹")
        }
        HomeAttentionsAdapter(list)
    }

    /**
     * 初始化fragment
     */
    fun initFragment(): MutableList<Fragment> {
        var fragmentList = mutableListOf<Fragment>()

        //6个fragment
        for (i in 0 until 5) {
            fragmentList.add(HomeFragment.getInstance(i))
        }

        return fragmentList
    }




}


