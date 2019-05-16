package com.qs.modulemain.ui.adapter

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.qs.modulemain.R
import com.qs.modulemain.bean.ChooseGetBean
import com.qs.modulemain.ui.activity.index.FtsIssueEditActivity
import com.smallcat.shenhai.mvpbase.utils.Base64Utils
import java.text.DecimalFormat
import java.util.ArrayList

/**
 * Created by hui on 2018/7/19.
 */
class ChooseFTSAdapter(data: ArrayList<ChooseGetBean>?) : BaseQuickAdapter<ChooseGetBean, BaseViewHolder>(R.layout.item_fts, data) {
    override fun convert(viewHolder: BaseViewHolder, item: ChooseGetBean?) {
        viewHolder.setText(R.id.tv_name, item!!.sym_name + "(#" + item.sym.split("#")[1] + ")")
        //total_supply、current_supply两个字段返回值不确定，加个异常捕获防止闪退
        try {
            var diff = item.total_supply.split(" ")[0].toFloat() - item.current_supply.split(" ")[0].toFloat()

            var JING = ""
            for (i in 0 until item.sym.split(",")[0].toInt()) {
                JING += "0"
            }

            viewHolder.setText(R.id.tv_address, mContext.getString(R.string.count) + " : " + item.total_supply.split(" ")[0] + "," + mContext.getString(R.string.surplus) + " : " + DecimalFormat("0.$JING").format(diff))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val bg = viewHolder.getView<ImageView>(R.id.iv_img)
        //bg.setImageResource(R.drawable.icon_fukuan_evt)
        var isHaveIcon = false
        for (meta in item.metas) {
            if (meta.value.isEmpty()) continue
            if (!meta.value.contains(",")) continue
            val decodedByte: Bitmap? = Base64Utils.base64ToBitmap(meta.value) ?: continue
            bg.setImageBitmap(decodedByte)
            isHaveIcon = true
        }
        if (!isHaveIcon && (item?.sym_name == "EVT" || item?.sym_name == "PEVT")) {
            bg.setImageResource(R.drawable.icon_fukuan_evt)
        } else if (!isHaveIcon) {
            bg.setImageResource(0)
        }

        viewHolder.getView<ImageView>(R.id.iv_more).setOnClickListener {
            val intent = Intent(mContext, FtsIssueEditActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("data", item)
            intent.putExtras(bundle)
            mContext.startActivity(intent)
        }
    }

}