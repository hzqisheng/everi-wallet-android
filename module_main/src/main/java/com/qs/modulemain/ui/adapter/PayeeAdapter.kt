package com.qs.modulemain.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.qs.modulemain.R
import com.qs.modulemain.bean.TransferLogBean
import java.text.SimpleDateFormat
import java.util.*
import java.text.ParsePosition


/**
 * Created by hui on 2018/7/19.
 */
class PayeeAdapter(data: List<String>?) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_payee, data) {

    override fun convert(viewHolder: BaseViewHolder, item: String) {
        viewHolder.setText(R.id.tv_payee_name, item)
    }
}