package com.qs.modulemain.ui.adapter

import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.qs.modulemain.R
import com.smallcat.shenhai.mvpbase.model.bean.ChooseBean

/**
 * Created by hui on 2018/7/19.
 */
class ChooseAdapter(data: List<ChooseBean>?) : BaseQuickAdapter<ChooseBean, BaseViewHolder>(R.layout.item_group_choose, data) {

    override fun convert(viewHolder: BaseViewHolder, item: ChooseBean) {
        viewHolder.setText(R.id.tv_name, item.title)

        val bg = viewHolder.getView<ImageView>(R.id.iv_choose)
        if (item.isSelected) {
            bg.visibility = View.VISIBLE
        } else {
            bg.visibility = View.GONE
        }
        if ((data.size - 1) == viewHolder.adapterPosition) {
            viewHolder.setVisible(R.id.view12, false)
        } else {
            viewHolder.setVisible(R.id.view12, true)
        }
    }
}