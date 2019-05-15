package com.qs.modulemain.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.qs.modulemain.R
import com.qs.modulemain.bean.Authorizer
import com.qs.modulemain.bean.ChooseGetBean
import com.qs.modulemain.bean.DomainBean
import com.qs.modulemain.bean.DomainDetailBean

/**
 * Created by hui on 2018/7/19.
 */
class DomainDetailAdapter(data: MutableList<Authorizer>?) : BaseQuickAdapter<Authorizer, BaseViewHolder>(R.layout.item_domain_detail, data) {

    override fun convert(viewHolder: BaseViewHolder, item: Authorizer) {
        viewHolder.setText(R.id.tv_text, "${item.ref},${item.weight}")
    }
}