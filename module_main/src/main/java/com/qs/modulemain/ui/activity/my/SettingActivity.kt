package com.qs.modulemain.ui.activity.my

import com.qs.modulemain.R
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.getResourceString
import com.smallcat.shenhai.mvpbase.extension.start
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : SimpleActivity() {

    override val layoutId: Int
        get() = R.layout.activity_setting

    override fun initData() {
        tvTitle?.text = getResourceString(R.string.system_setting)

        tv_choose_language.setOnClickListener { start(LanguagesActivity::class.java) }
        tv_currency_setting.setOnClickListener { start(LanguagesActivity::class.java) }
    }

}
