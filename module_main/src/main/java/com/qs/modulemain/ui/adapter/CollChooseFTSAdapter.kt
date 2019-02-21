package com.qs.modulemain.ui.adapter

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.qs.modulemain.R
import com.qs.modulemain.bean.ChooseGetBean
import com.smallcat.shenhai.mvpbase.utils.Base64Utils
import java.util.*

/**
 * Created by hui on 2018/7/19.
 */
class CollChooseFTSAdapter(data: ArrayList<ChooseGetBean>?) : BaseQuickAdapter<ChooseGetBean, BaseViewHolder>(R.layout.item_collect_fts, data) {
    override fun convert(viewHolder: BaseViewHolder, item: ChooseGetBean?) {
        viewHolder.setText(R.id.tv_name, item!!.sym_name/*+"(#"+item.sym.split("#")[1]+")"*/)
        //val diff = item.total_supply.split(" ")[0].toFloat() - item.current_supply.split(" ")[0].toFloat()

        /*var JING = ""
        for (i in 0 until item.sym.split(",")[0].toInt()){
            JING +="0";
        }*/

        viewHolder.setText(R.id.tv_address, mContext.getString(R.string.count) + " : " + item.total_supply.split(" ")[0]/*+","+mContext.getString(R.string.surplus)+" : "+ DecimalFormat("0."+JING).format(diff)*/)

        val bg = viewHolder.getView<ImageView>(R.id.iv_img)
        bg.setImageResource(R.drawable.icon_fukuan_evt)
        for (meta in item.metas) {
            if (meta.value.isEmpty()) return
            if (!meta.value.contains(",")) return
            val decodedByte: Bitmap? = Base64Utils.base64ToBitmap(meta.value) ?: return
            bg.setImageBitmap(decodedByte)
        }

        val ivChoose = viewHolder.getView<ImageView>(R.id.iv_choose)

        if (item.isChoose) {
            ivChoose.visibility = View.VISIBLE
        } else {
            ivChoose.visibility = View.GONE
        }
    }

}