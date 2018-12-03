package com.qs.modulemain.ui.activity.my

import com.qs.modulemain.R
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.getResourceString

class HelpCenterActivity : SimpleActivity() {

    override val layoutId: Int
        get() = R.layout.activity_help_center

    override fun initData() {
        tvTitle?.text = getString(R.string.help_center)
    }

}
