package com.test.jingmengsong.pageandcolorformer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.test.jingmengsong.pageandcolorformer.adapter.HomeFragmentAdapter
import kotlinx.android.synthetic.main.activity_content.*
import android.support.v7.widget.RecyclerView
import android.view.animation.AnimationUtils
import com.test.jingmengsong.pageandcolorformer.view.PageLayout


class ContentActivity : AppCompatActivity() {


    private var mNickName: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        //获取传递过来的originVIew信息
        extractViewInfoFromBundle(intent)

        //将获取的数据赋值给nickName
        tv_name.text = mNickName

        tv_name.setOnClickListener {
            supportFinishAfterTransition()
        }

        rv_content_activity.run {
            layoutManager = LinearLayoutManager(this@ContentActivity)
            rv_content_activity.adapter = homeAdapter
            rv_content_activity.scheduleLayoutAnimation()
        }
    }
    private fun extractViewInfoFromBundle(intent: Intent) {
        mNickName = intent.getStringExtra("extra_nick_name")
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


}
