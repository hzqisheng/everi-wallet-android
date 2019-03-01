package com.qs.modulemain.ui.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.qs.modulemain.R
import com.qs.modulemain.bean.RecordDetailBean
import com.smallcat.shenhai.mvpbase.extension.getResourceColor
import com.smallcat.shenhai.mvpbase.extension.logE
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import java.text.SimpleDateFormat
import java.util.*
import java.text.ParsePosition


/**
 * Created by hui on 2018/7/19.
 */
class RecordItemAdapter(data: List<RecordDetailBean>?) : BaseQuickAdapter<RecordDetailBean, BaseViewHolder>(com.qs.modulemain.R.layout.item_record, data) {

    override fun convert(viewHolder: BaseViewHolder, item: RecordDetailBean) {
        //viewHolder.addOnClickListener(R.id.content)

//        if(mContext.sharedPref.publicKey.equals(item.data.from)){
//            viewHolder.setText(R.id.tv_code,item.data.to)
//            viewHolder.setText(R.id.tv_time,item.timestamp)
//            viewHolder.setText(R.id.textView16,"-"+item.data.number.split(" ")[0])
//            viewHolder.getView<TextView>(R.id.textView16).setTextColor(mContext.getResourceColor(R.color.black))
//        }else if(mContext.sharedPref.publicKey.equals(item.data.to)){
//            viewHolder.setText(R.id.tv_code,item.data.from)
//            viewHolder.setText(R.id.tv_time,item.timestamp)
//            viewHolder.setText(R.id.textView16,"+"+item.data.number.split(" ")[0])
//            viewHolder.getView<TextView>(R.id.textView16).setTextColor(mContext.getResourceColor(R.color.holo_red_light))
//        }else{
//            viewHolder.setText(R.id.tv_code,"发行")
//            viewHolder.setText(R.id.tv_time,item.timestamp)
//            viewHolder.setText(R.id.textView16,"+"+item.data.number.split(" ")[0])
//            viewHolder.getView<TextView>(R.id.textView16).setTextColor(mContext.getResourceColor(R.color.holo_red_light))
//
//        }
        if (item.name == "issuefungible") {
            viewHolder.setText(com.qs.modulemain.R.id.tv_code, mContext.getString(com.qs.modulemain.R.string.issue))
            showZoneTime(viewHolder, item)
            viewHolder.setText(com.qs.modulemain.R.id.textView16, "+" + item.data.number.split(" ")[0])
            viewHolder.getView<TextView>(com.qs.modulemain.R.id.textView16).setTextColor(mContext.getResourceColor(com.qs.modulemain.R.color.holo_red_light))
        } else if (item.name == "transferft") {
            when {
                mContext.sharedPref.publicKey == item.data.from -> {
                    viewHolder.setText(com.qs.modulemain.R.id.tv_code, item.data.to)
                    showZoneTime(viewHolder, item)
                    viewHolder.setText(com.qs.modulemain.R.id.textView16, "-" + item.data.number.split(" ")[0])
                    viewHolder.getView<TextView>(com.qs.modulemain.R.id.textView16).setTextColor(mContext.getResourceColor(com.qs.modulemain.R.color.black))
                }
                mContext.sharedPref.publicKey == item.data.to -> {
                    viewHolder.setText(com.qs.modulemain.R.id.tv_code, item.data.from)
                    showZoneTime(viewHolder, item)
                    viewHolder.setText(com.qs.modulemain.R.id.textView16, "+" + item.data.number.split(" ")[0])
                    viewHolder.getView<TextView>(com.qs.modulemain.R.id.textView16).setTextColor(mContext.getResourceColor(com.qs.modulemain.R.color.holo_red_light))
                }
                else -> "issuefungible".logE()
            }

        } else if (item.name == "everipay") {
            if (mContext.sharedPref.publicKey != item.data.payee) {
                viewHolder.setText(com.qs.modulemain.R.id.tv_code, item.data.payee)
                showZoneTime(viewHolder, item)
                viewHolder.setText(com.qs.modulemain.R.id.textView16, "-" + item.data.number.split(" ")[0])
                viewHolder.getView<TextView>(com.qs.modulemain.R.id.textView16).setTextColor(mContext.getResourceColor(com.qs.modulemain.R.color.black))
            } else {
                viewHolder.setText(com.qs.modulemain.R.id.tv_code, item.data.link.keys[0])
                showZoneTime(viewHolder, item)
                viewHolder.setText(com.qs.modulemain.R.id.textView16, "+" + item.data.number.split(" ")[0])
                viewHolder.getView<TextView>(com.qs.modulemain.R.id.textView16).setTextColor(mContext.getResourceColor(com.qs.modulemain.R.color.holo_red_light))
            }
        } else {
            "11111111111111111123".logE()
            item.name.logE()
        }
    }

    private fun showZoneTime(viewHolder: BaseViewHolder, item: RecordDetailBean) {
        //viewHolder.setText(R.id.tv_time, item.timestamp)
        try {
            //2018-11-19T09:21:43
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            dateFormat.timeZone = TimeZone.getTimeZone("GMT")
            val date = dateFormat.parse(item.timestamp, ParsePosition(0))
            dateFormat.applyPattern("yyyy-MM-dd HH:mm:ss")
            dateFormat.timeZone = TimeZone.getDefault()
            viewHolder.setText(R.id.tv_time, dateFormat.format(date))
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }
}