package com.qs.modulemain.ui.activity.my

import com.qs.modulemain.R
import com.qs.modulemain.bean.AddressBean
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.utils.addClipboard
import kotlinx.android.synthetic.main.activity_export.*
import org.litepal.crud.DataSupport


class ExportAddressActivity : SimpleActivity() {

    override val layoutId: Int
        get() = R.layout.activity_export

    override fun initData() {
        tvTitle?.text = getString(R.string.export)

        val dataList: List<AddressBean> = DataSupport.findAll(AddressBean::class.java)
        var result = ""
        for (addressBean in dataList) {
            result += addressBean.toString()
        }
        tvExport.text = result

        tv_save.setOnClickListener {
            if (result == ""){
                return@setOnClickListener
            }
            addClipboard(this@ExportAddressActivity,result)
            getString(R.string.copy_success).toast()
        }
    }


}
