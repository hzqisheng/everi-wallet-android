package com.qs.modulemain.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.qs.modulemain.R
import com.qs.modulemain.bean.DomainBean
import com.qs.modulemain.bean.GroupNameBean

/**
 * Created by hui on 2018/7/19.
 */
class MyGroupAdapter(data: MutableList<GroupNameBean>?) : BaseQuickAdapter<GroupNameBean, BaseViewHolder>(R.layout.item_group, data) {

    override fun convert(viewHolder: BaseViewHolder, item: GroupNameBean) {
        viewHolder.setText(R.id.tv_name, item.name)
    }
}