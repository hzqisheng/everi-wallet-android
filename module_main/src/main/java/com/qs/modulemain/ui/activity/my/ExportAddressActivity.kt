package com.qs.modulemain.ui.activity.my

import com.qs.modulemain.R
import com.qs.modulemain.bean.AddressBean
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.getResourceString
import kotlinx.android.synthetic.main.activity_export.*
import org.litepal.crud.DataSupport
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.utils.addClipboard


class ExportAddressActivity : SimpleActivity() {

    override val layoutId: Int
        get() = R.layout.activity_export

    override fun initData() {
        tvTitle?.text = getString(R.string.export)

        var dataList: List<AddressBean> = DataSupport.findAll(AddressBean::class.java)
        var result: String = ""
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
