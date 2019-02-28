package com.qs.modulemain.ui.adapter

import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.qs.modulemain.R
import com.qs.modulemain.bean.AddressBean
import com.qs.modulemain.bean.NodeBean
import java.util.ArrayList

class NodeListAdapter(data: ArrayList<NodeBean>?) : BaseQuickAdapter<NodeBean, BaseViewHolder>(R.layout.item_node_list, data) {

    override fun convert(helper: BaseViewHolder, item: NodeBean) {
        helper.setText(R.id.tv_node_address, /*"https://" + */item.nodeAddress)
                .setText(R.id.tv_node_name, item.nodeName)
        val ivChoose = helper.getView<ImageView>(R.id.iv_choose)
        if (item.isChoose) {
            ivChoose.visibility = View.VISIBLE
        } else {
            ivChoose.visibility = View.GONE
        }
    }

}