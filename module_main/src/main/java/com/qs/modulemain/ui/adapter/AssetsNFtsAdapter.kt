package com.qs.modulemain.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.qs.modulemain.R
import com.smallcat.shenhai.mvpbase.model.bean.NFtsBean

/**
 * Created by hui on 2018/7/19.
 */
class AssetsNFtsAdapter(data: MutableList<NFtsBean>?) : BaseQuickAdapter<NFtsBean, BaseViewHolder>(R.layout.item_assets_nfts_item, data) {

    override fun convert(viewHolder: BaseViewHolder, item: NFtsBean) {
        viewHolder.addOnClickListener(R.id.iv_pay)
        viewHolder.setText(R.id.tv_name, item.name).setText(R.id.tv_number, item.domain)
    }
}