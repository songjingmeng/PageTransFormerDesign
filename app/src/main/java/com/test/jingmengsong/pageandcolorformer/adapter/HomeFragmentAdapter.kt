package com.test.jingmengsong.pageandcolorformer.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.test.jingmengsong.pageandcolorformer.R


/**
 * 业务名：
 * 功能说明：
 * 创建于：2018/9/4 on 15:00
 * 作者： jingmengsong
 * <p/>
 * 历史记录
 * 修改日期：
 * 修改人：
 * 修改内容：
 */
class HomeFragmentAdapter(datas: MutableList<String>) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_fragment_home_list, datas) {
    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.setText(R.id.tv_name, item)
    }
}