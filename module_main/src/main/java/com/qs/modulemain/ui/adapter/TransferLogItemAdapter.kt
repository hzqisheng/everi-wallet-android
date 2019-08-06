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
class TransferLogItemAdapter(data: List<TransferLogBean>?) : BaseQuickAdapter<TransferLogBean, BaseViewHolder>(R.layout.item_transfer_log, data) {

    override fun convert(viewHolder: BaseViewHolder, item: TransferLogBean) {
        if (item.data.to.isNotEmpty()) {
            viewHolder.setText(R.id.tv_code, item.data.to[0])
        }
        showZoneTime(viewHolder, item)
    }

    private fun showZoneTime(viewHolder: BaseViewHolder, item: TransferLogBean) {
        try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            dateFormat.timeZone = TimeZone.getTimeZone("GMT")
            val date = dateFormat.parse(item.timestamp, ParsePosition(0))
            dateFormat.applyPattern("yyyy-MM-dd HH:mm:ss")
            dateFormat.timeZone = TimeZone.getDefault()
            viewHolder.setText(R.id.tv_time, "${mContext.getString(R.string.transfer_time)}${dateFormat.format(date)}")
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }
}