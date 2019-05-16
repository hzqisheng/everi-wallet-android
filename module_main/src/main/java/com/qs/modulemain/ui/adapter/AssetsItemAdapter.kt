package com.qs.modulemain.ui.adapter

import android.graphics.Bitmap
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.qs.modulemain.R
import com.qs.modulemain.bean.ChooseGetBean
import com.smallcat.shenhai.mvpbase.utils.Base64Utils

/**
 * Created by hui on 2018/7/19.
 */
class AssetsItemAdapter(data: MutableList<ChooseGetBean>?) : BaseQuickAdapter<ChooseGetBean, BaseViewHolder>(R.layout.item_assets_item, data) {

    override fun convert(viewHolder: BaseViewHolder, item: ChooseGetBean) {
        viewHolder.setText(R.id.tv_name, item.sym_name + "(" + item.asset.split("S")[1] + ")")

        viewHolder.setText(R.id.tv_number, item.asset.split(" ")[0]).addOnClickListener(R.id.iv_pay)


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
    }

}