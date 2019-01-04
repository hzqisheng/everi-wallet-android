package com.qs.modulemain.ui.activity.index

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.qs.modulemain.R
import com.qs.modulemain.bean.AddressBean
import com.qs.modulemain.ui.activity.my.AddressManageActivity
import com.qs.modulemain.ui.adapter.ChooseAdapter
import com.qs.modulemain.ui.adapter.ChooseAddressAdapter
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.getResourceColor
import com.smallcat.shenhai.mvpbase.extension.start
import kotlinx.android.synthetic.main.activity_choose_address.*
import org.litepal.crud.DataSupport
import java.util.ArrayList

class ChooseAddressActivity : SimpleActivity() {
    private lateinit var adapter:ChooseAddressAdapter
    private  var dataList:ArrayList<AddressBean> ?= null

    override val layoutId: Int
        get() = R.layout.activity_choose_address

    override fun initData() {
        tvTitle?.text = getString(R.string.choose_address)
        tvRight?.apply {
            text = getString(R.string.address_manage)
            setTextColor(getResourceColor(R.color.color_e4))
            setOnClickListener{
                start(AddressManageActivity::class.java)
            }
        }
        dataList = DataSupport.findAll(AddressBean::class.java) as ArrayList<AddressBean>?

        adapter = ChooseAddressAdapter(DataSupport.findAll(AddressBean::class.java) as ArrayList<AddressBean>?)
        rv_list.adapter = adapter

        adapter.onItemClickListener = object : AdapterView.OnItemClickListener, BaseQuickAdapter.OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                var intent  = Intent()
                intent.putExtra("data", dataList!![position].address)
                setResult(1,intent)
                finish()
            }

            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }
        }

    }

}

