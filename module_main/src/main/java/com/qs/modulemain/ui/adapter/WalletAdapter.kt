package com.qs.modulemain.ui.adapter

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.qs.modulemain.R
import com.qs.modulemain.R.id.iv_bg
import com.qs.modulemain.ui.activity.index.WalletDetailActivity
import com.smallcat.shenhai.mvpbase.extension.getResourceColor
import com.smallcat.shenhai.mvpbase.extension.getResourceString
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.model.bean.BaseData
import com.smallcat.shenhai.mvpbase.utils.addClipboard

/**
 * Created by hui on 2018/7/19.
 */
class WalletAdapter(data: List<BaseData>?) : BaseQuickAdapter<BaseData, BaseViewHolder>(R.layout.item_wallet, data) {

    override fun convert(viewHolder: BaseViewHolder, item: BaseData) {
        viewHolder.addOnClickListener(R.id.iv_more)
        if (item.name != null && !item.name.isEmpty())
            viewHolder.setText(R.id.tv_name, item.name)
        if (item.type != null && !item.type.isEmpty())
            viewHolder.setText(R.id.tv_type, item.type)
        viewHolder.setText(R.id.public_key, item.publicKey)

//        if(item.isSelect == 0) {
//            viewHolder.getView<View>(R.id.iv_bg).setBackgroundColor(mContext.getColor(R.color.white))
//        }else{
//            //ic_wallet_yellow_bg
//            viewHolder.getView<View>(R.id.iv_bg).background = mContext.getDrawable(R.drawable.ic_wallet_yellow_bg)
//        }
        val imageView = viewHolder.getView<ImageView>(R.id.iv_bg)

        if (item.isSelect == 1) {
            imageView.setImageResource(mContext.getResourceColor(R.color.transparent))
            imageView.setBackgroundResource(R.drawable.ic_wallet_yellow_bg)
        } else {
            imageView.setBackgroundColor(mContext.getResourceColor(R.color.transparent))
            imageView.setImageResource(R.drawable.shape_round_wallet_bg)
        }

        viewHolder.getView<ImageView>(R.id.iv_more).setOnClickListener {
            Intent(mContext, WalletDetailActivity::class.java).apply {
                putExtra("data", item.id)
                mContext.startActivity(this)
            }
        }

        viewHolder.getView<TextView>(R.id.public_key).setOnClickListener {
            addClipboard(mContext, item.publicKey)
            mContext.getString(R.string.copy_success).toast()
        }
    }
}