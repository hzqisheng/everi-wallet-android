package com.qs.modulemain.ui.activity.manage

import com.qs.modulemain.R
import com.smallcat.shenhai.mvpbase.base.SimpleActivity

class EnterPubKeyActivity : SimpleActivity() {

    override val layoutId: Int
        get() = R.layout.activity_enter_pub_key

    override fun initData() {
        tvTitle?.text = getString(R.string.input_pub_key)
    }

}
