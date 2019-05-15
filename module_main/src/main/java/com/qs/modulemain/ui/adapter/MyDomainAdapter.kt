package com.qs.modulemain.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.qs.modulemain.R
import com.qs.modulemain.bean.DomainBean
import com.smallcat.shenhai.mvpbase.model.bean.NFtsBean

/**
 * Created by hui on 2018/7/19.
 */
class MyDomainAdapter(data: MutableList<DomainBean>?) : BaseQuickAdapter<DomainBean, BaseViewHolder>(R.layout.item_assets_nfts_item, data) {

    override fun convert(viewHolder: BaseViewHolder, item: DomainBean) {
        //viewHolder.addOnClickListener(R.id.iv_pay)
        viewHolder.setText(R.id.tv_name, item.name).setText(R.id.tv_number, item.creator)
        viewHolder.getView<ImageView>(R.id.iv_pay).visibility = View.GONE
        viewHolder.getView<TextView>(R.id.tv_issue).visibility = View.VISIBLE
        viewHolder.addOnClickListener(R.id.tv_issue)
    }
}