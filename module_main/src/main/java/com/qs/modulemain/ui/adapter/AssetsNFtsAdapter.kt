package com.qs.modulemain.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.qs.modulemain.R
import com.qs.modulemain.bean.ChooseGetBean
import com.smallcat.shenhai.mvpbase.model.bean.NFtsBean

/**
 * Created by hui on 2018/7/19.
 */
class AssetsNFtsAdapter(data: MutableList<ChooseGetBean>?) : BaseQuickAdapter<ChooseGetBean, BaseViewHolder>(R.layout.item_assets_nfts_item, data) {

    override fun convert(viewHolder: BaseViewHolder, item: ChooseGetBean) {
        viewHolder.setText(R.id.tv_name, item.name)
                .setText(R.id.tv_number, item.domain)
                .addOnClickListener(R.id.v_transfer)
                .addOnClickListener(R.id.iv_pay)

    }
}