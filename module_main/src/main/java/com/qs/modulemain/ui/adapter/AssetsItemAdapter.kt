package com.qs.modulemain.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.qs.modulemain.R

/**
 * Created by hui on 2018/7/19.
 */
class AssetsItemAdapter(data: MutableList<String>?) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_assets_item, data) {

    override fun convert(viewHolder: BaseViewHolder, item: String) {
        viewHolder.addOnClickListener(R.id.iv_pay)
        viewHolder.setText(R.id.tv_name, item).setText(R.id.tv_number, item)
    }
}