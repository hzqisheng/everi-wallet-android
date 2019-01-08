package com.qs.modulemain.ui.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.qs.modulemain.R
import com.qs.modulemain.bean.RecordDetailBean
import com.smallcat.shenhai.mvpbase.extension.getResourceColor
import com.smallcat.shenhai.mvpbase.extension.logE
import com.smallcat.shenhai.mvpbase.extension.sharedPref

/**
 * Created by hui on 2018/7/19.
 */
class RecordItemAdapter(data: List<RecordDetailBean>?) : BaseQuickAdapter<RecordDetailBean, BaseViewHolder>(R.layout.item_record, data) {

    override fun convert(viewHolder: BaseViewHolder, item: RecordDetailBean) {
        viewHolder.addOnClickListener(R.id.content)
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
        if(item.name.equals("issuefungible")){
            viewHolder.setText(R.id.tv_code,"发行")
            viewHolder.setText(R.id.tv_time,item.timestamp)
            viewHolder.setText(R.id.textView16,"+"+item.data.number.split(" ")[0])
            viewHolder.getView<TextView>(R.id.textView16).setTextColor(mContext.getResourceColor(R.color.holo_red_light))
        }else if(item.name.equals("transferft")){
            if(mContext.sharedPref.publicKey.equals(item.data.from)){
                viewHolder.setText(R.id.tv_code,item.data.to)
                viewHolder.setText(R.id.tv_time,item.timestamp)
                viewHolder.setText(R.id.textView16,"-"+item.data.number.split(" ")[0])
                viewHolder.getView<TextView>(R.id.textView16).setTextColor(mContext.getResourceColor(R.color.black))
            }else if(mContext.sharedPref.publicKey.equals(item.data.to)){
                viewHolder.setText(R.id.tv_code,item.data.from)
                viewHolder.setText(R.id.tv_time,item.timestamp)
                viewHolder.setText(R.id.textView16,"+"+item.data.number.split(" ")[0])
                viewHolder.getView<TextView>(R.id.textView16).setTextColor(mContext.getResourceColor(R.color.holo_red_light))
            }else{
                "issuefungible".logE()
            }

        }else if(item.name.equals("everipay")){
            if(!mContext.sharedPref.publicKey.equals(item.data.payee)){
                viewHolder.setText(R.id.tv_code,item.data.payee)
                viewHolder.setText(R.id.tv_time,item.timestamp)
                viewHolder.setText(R.id.textView16,"-"+item.data.number.split(" ")[0])
                viewHolder.getView<TextView>(R.id.textView16).setTextColor(mContext.getResourceColor(R.color.black))
            }else{
                viewHolder.setText(R.id.tv_code,item.data.link.keys[0])
                viewHolder.setText(R.id.tv_time,item.timestamp)
                viewHolder.setText(R.id.textView16,"+"+item.data.number.split(" ")[0])
                viewHolder.getView<TextView>(R.id.textView16).setTextColor(mContext.getResourceColor(R.color.holo_red_light))
            }
        }else{
            "11111111111111111123".logE()
            item.name.logE()
        }
    }
}