package com.qs.modulemain.ui.activity.index

import com.qs.modulemain.R
import com.smallcat.shenhai.mvpbase.base.SimpleActivity

class IssueChooseAddressActivity : SimpleActivity() {

    override val layoutId: Int
        get() = R.layout.activity_issue_choose_address

    override fun initData() {
        tvTitle?.text = getString(R.string.choose_address)
    }


}
