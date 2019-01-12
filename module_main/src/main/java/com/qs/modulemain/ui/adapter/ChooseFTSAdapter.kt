package com.qs.modulemain.ui.adapter

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.qs.modulemain.R
import com.qs.modulemain.R.id.view
import com.qs.modulemain.bean.ChooseFTSBean
import com.qs.modulemain.bean.ChooseGetBean
import com.qs.modulemain.ui.activity.index.FtsIssueActivity
import com.qs.modulemain.ui.activity.index.FtsIssueEditActivity
import com.smallcat.shenhai.mvpbase.extension.logE
import com.smallcat.shenhai.mvpbase.model.bean.ChooseBean
import com.smallcat.shenhai.mvpbase.utils.Base64Utils
import com.smallcat.shenhai.mvpbase.utils.GlideImageLoader
import com.smallcat.shenhai.mvpbase.utils.base64ToBitmap
import java.text.DecimalFormat
import java.util.ArrayList

/**
 * Created by hui on 2018/7/19.
 */
class ChooseFTSAdapter(data: ArrayList<ChooseGetBean>?) : BaseQuickAdapter<ChooseGetBean, BaseViewHolder>(R.layout.item_fts, data) {
    override fun convert(viewHolder: BaseViewHolder, item: ChooseGetBean?) {
        viewHolder.setText(R.id.tv_name, item!!.sym_name + "(#" + item.sym.split("#")[1] + ")")
        var diff = item.total_supply.split(" ")[0].toFloat() - item.current_supply.split(" ")[0].toFloat()

        var JING = ""
        for (i in 0 until item.sym.split(",")[0].toInt()) {
            JING += "0"
        }

        viewHolder.setText(R.id.tv_address, mContext.getString(R.string.count) + " : " + item.total_supply.split(" ")[0] + "," + mContext.getString(R.string.surplus) + " : " + DecimalFormat("0.$JING").format(diff))

        val bg = viewHolder.getView<ImageView>(R.id.iv_img)
        bg.setImageResource(R.drawable.icon_fukuan_evt)
        for (meta in item.metas) {
            if (meta.value.isEmpty()) return
            if (!meta.value.contains(",")) return
            val decodedByte: Bitmap? = Base64Utils.base64ToBitmap(meta.value) ?: return
            bg.setImageBitmap(decodedByte)
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