package com.test.jingmengsong.pageandcolorformer.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v4.util.Pair
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.test.jingmengsong.pageandcolorformer.ContentActivity
import com.test.jingmengsong.pageandcolorformer.R
import com.test.jingmengsong.pageandcolorformer.adapter.HomeFragmentAdapter
import com.test.jingmengsong.pageandcolorformer.view.PageLayout
import kotlinx.android.synthetic.main.fragment_attention_persons.*
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * 业务名：
 * 功能说明：
 * 创建于：2018/9/7 on 16:16
 * 作者： jingmengsong
 * <p/>
 * 历史记录
 * 修改日期：
 * 修改人：
 * 修改内容：
 */
class AttentionsFragment : Fragment() {


    private lateinit var layout: View
    private lateinit var mAttentionLl: RecyclerView
    private val mPageLayout by lazy {
        PageLayout.Builder(context!!)
                //传递 一个 view ： recyclerView
                .initPage(mAttentionLl)
                .setLoading(R.layout.layout_loading)
                .setEmpty(R.layout.layout_empty, R.id.tv_page_empty)
                .setError(R.layout.layout_error, R.id.tv_page_error, object : PageLayout.OnRetryClickListener {
                    override fun onRetry() {
                        loadData()
                    }
                })
//                .setEmptyDrawable(R.drawable.abc_ab_share_pack_mtrl_alpha)
//                .setErrorDrawable(R.drawable.abc_ic_arrow_drop_right_black_24dp)
                .create()
    }

    companion object {
        private val EXTRA_KEY_POSITION = "position"
        fun getInstance(position: Int): AttentionsFragment {
            val attentionsFragment = AttentionsFragment()
            val bundle = Bundle()
            bundle.putInt(EXTRA_KEY_POSITION, position)
            attentionsFragment.arguments = bundle
            return attentionsFragment
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

        layout = inflater.inflate(R.layout.fragment_attention_persons, null)

        //查询当前Fragment中 的 组件view
        mAttentionLl = layout.findViewById<RecyclerView>(R.id.rv_home_fragment)

        //加载当前页面 数据
        loadData()

        //设置item 点击事件
        homeAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            val ivImg = view.findViewById<AppCompatImageView>(R.id.iv_img)
            val tvName = view.findViewById<AppCompatTextView>(R.id.tv_name)
            val intent = Intent(activity, ContentActivity::class.java)
            intent.putExtra("extra_nick_name", homeAdapter.data[position])
            val p1: Pair<View, String> = android.support.v4.util.Pair.create(ivImg, "avatar")
            val p2: Pair<View, String> = android.support.v4.util.Pair.create(tvName, "nickname")
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity as Activity, p1, p2)
            startActivity(intent, options.toBundle())

        }


        return layout
    }

    private fun loadData() {

        //展示 加载数据 的进度界面
        mPageLayout.showLoading()
        //这里手动的隐藏 加载 进度界面
        Handler().postDelayed({
            mPageLayout.hide()
        }, 3000)

        //为列表进行设置 adapter
        layout.findViewById<RecyclerView>(R.id.rv_home_fragment).run {
            layoutManager = LinearLayoutManager(activity)
            adapter = homeAdapter
        }


    }
}