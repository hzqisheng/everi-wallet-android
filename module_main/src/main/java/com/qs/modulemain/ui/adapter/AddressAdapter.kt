package com.qs.modulemain.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.qs.modulemain.R
import com.qs.modulemain.bean.AddressBean
import java.util.ArrayList


class AddressAdapter(data: ArrayList<AddressBean>?) : BaseQuickAdapter<AddressBean, BaseViewHolder>(R.layout.item_address, data) {

    override fun convert(helper: BaseViewHolder?, item: AddressBean?) {
        //地址
        helper!!.setText(R.id.tv_code,item!!.address)
        //name
        helper.setText(R.id.tv_msg,item.name)
    }

}