package com.qs.modulemain.ui.activity.my

import com.qs.modulemain.R
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.getResourceString

class ExportAddressActivity : SimpleActivity() {

    override val layoutId: Int
        get() = R.layout.activity_export

    override fun initData() {
        tvTitle?.text = getString(R.string.export)
    }


}
