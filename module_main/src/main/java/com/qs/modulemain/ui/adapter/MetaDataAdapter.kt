package com.qs.modulemain.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.qs.modulemain.R
import com.qs.modulemain.bean.DetailMetas
import com.qs.modulemain.bean.DomainBean
import com.qs.modulemain.bean.GroupNameBean

/**
 * Created by hui on 2018/7/19.
 */
class MetaDataAdapter(data: MutableList<DetailMetas>?) : BaseQuickAdapter<DetailMetas, BaseViewHolder>(R.layout.item_metadata, data) {

    override fun convert(viewHolder: BaseViewHolder, item: DetailMetas) {
        viewHolder.setText(R.id.tv_key1, item.key)
                .setText(R.id.tv_value1, item.value)
                .setText(R.id.tv_creator1, item.creator)
    }
}