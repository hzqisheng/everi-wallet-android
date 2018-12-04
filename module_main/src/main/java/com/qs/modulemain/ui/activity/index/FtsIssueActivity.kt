package com.qs.modulemain.ui.activity.index

import com.qs.modulemain.R
import com.smallcat.shenhai.mvpbase.base.SimpleActivity

class FtsIssueActivity : SimpleActivity() {

    override val layoutId: Int
        get() = R.layout.activity_fts_issue

    override fun initData() {
        tvTitle?.text = getString(R.string.issue)
    }


}
