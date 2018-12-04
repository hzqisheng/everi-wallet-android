package com.qs.modulemain.ui.activity.index

import com.qs.modulemain.R
import com.smallcat.shenhai.mvpbase.base.SimpleActivity

class IssueFtsAuthorityActivity : SimpleActivity() {

    override val layoutId: Int
        get() = R.layout.activity_issue_fts_authority

    override fun initData() {
        tvTitle?.text = getString(R.string.authority_setting)

    }

}
