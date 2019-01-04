package com.qs.modulemain.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.qs.modulemain.R
import com.qs.modulemain.bean.AddressBean
import java.util.ArrayList

/**
 * 作者： MirsFang on 2018/12/15 16:24
 * 邮箱： mirsfang@163.com
 * 类描述：
 */
class AddressAdapter(data: ArrayList<AddressBean>?) : BaseQuickAdapter<AddressBean, BaseViewHolder>(R.layout.item_address, data) {

    override fun convert(helper: BaseViewHolder?, item: AddressBean?) {
        //地址
        helper!!.setText(R.id.tv_code,item!!.address)
        //name
        helper.setText(R.id.tv_msg,item.name)
    }

}