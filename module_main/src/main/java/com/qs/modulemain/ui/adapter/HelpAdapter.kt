package com.qs.modulemain.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.qs.modulemain.R
import com.qs.modulemain.bean.HelpCenterBean


class HelpAdapter(data: MutableList<HelpCenterBean>) : BaseQuickAdapter<HelpCenterBean, BaseViewHolder>(R.layout.item_help, data) {

    override fun convert(helper: BaseViewHolder, item: HelpCenterBean) {

        helper.setText(R.id.tv_text, item.title)
                .setText(R.id.tv_msg, item.msg)
    }

}