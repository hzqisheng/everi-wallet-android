package com.qs.modulemain.ui.activity.index

import com.qs.modulemain.R
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.getResourceString

class CollectActivity : SimpleActivity() {

    override val layoutId: Int
        get() = R.layout.activity_pay2

    override fun initData() {
        tvTitle?.text = getString(R.string.collect)
    }


}
