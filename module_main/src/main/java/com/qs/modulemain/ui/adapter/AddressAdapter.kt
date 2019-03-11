package com.qs.modulemain.ui.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.qs.modulemain.R
import com.qs.modulemain.bean.AddressBean
import java.util.ArrayList


class AddressAdapter(data: ArrayList<AddressBean>?) : BaseQuickAdapter<AddressBean, BaseViewHolder>(R.layout.item_address, data) {

    override fun convert(helper: BaseViewHolder?, item: AddressBean?) {
        //地址
        helper!!.setText(R.id.tv_code, item!!.address)
        //name
        helper.setText(R.id.tv_msg, item.name)
        //删除地址
        val delete = helper.getView<TextView>(R.id.right_menu)
        delete.setOnClickListener {
            data.remove(item)
            notifyDataSetChanged()
            item.delete()
        }
    }

}