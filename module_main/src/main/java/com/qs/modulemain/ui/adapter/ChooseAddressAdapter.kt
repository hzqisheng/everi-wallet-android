package com.qs.modulemain.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.qs.modulemain.R
import com.qs.modulemain.bean.AddressBean
import java.util.ArrayList

class ChooseAddressAdapter(data: ArrayList<AddressBean>?) : BaseQuickAdapter<AddressBean, BaseViewHolder>(R.layout.item_choose_adress, data) {

    override fun convert(helper: BaseViewHolder?, item: AddressBean?) {
        //地址
        helper!!.setText(R.id.tv_title,item!!.address)
        //name
        helper.setText(R.id.tv_type,item.name)
    }

}