package com.qs.modulemain.ui.activity.my

import com.qs.modulemain.R
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.getResourceString

class CurrencySettingActivity : SimpleActivity() {

    override val layoutId: Int
        get() = R.layout.activity_currency_setting

    override fun initData() {
        tvTitle?.text = getResourceString(R.string.currency_setting)
    }


}
