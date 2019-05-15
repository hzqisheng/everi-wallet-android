package com.qs.modulemain.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.qs.modulemain.R
import com.qs.modulemain.bean.ChooseGetBean
import com.qs.modulemain.bean.DomainBean

/**
 * Created by hui on 2018/7/19.
 */
class AuthorizerTransferAdapter(data: MutableList<DomainBean.TransferBean.AuthorizersBeanX>?) : BaseQuickAdapter<DomainBean.TransferBean.AuthorizersBeanX, BaseViewHolder>(R.layout.item_authorizer, data) {

    override fun convert(viewHolder: BaseViewHolder, item: DomainBean.TransferBean.AuthorizersBeanX) {
        viewHolder.setText(R.id.node_name_view, "${item.ref},${item.weight}")
                .addOnClickListener(R.id.iv_delete)

    }
}