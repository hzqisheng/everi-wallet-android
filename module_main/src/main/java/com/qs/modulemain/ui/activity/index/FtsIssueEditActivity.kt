package com.qs.modulemain.ui.activity.index

import com.qs.modulemain.R
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.getResourceColor
import com.smallcat.shenhai.mvpbase.extension.start

class FtsIssueEditActivity : SimpleActivity() {

    override val layoutId: Int
        get() = R.layout.activity_fts_issue_edit

    override fun initData() {
        tvTitle?.text = getString(R.string.issue)
        tvRight?.apply {
            text = getString(R.string.authority_setting)
            setTextColor(getResourceColor(R.color.color_e4))
            setOnClickListener{
                start(IssueFtsAuthorityActivity::class.java)
            }
        }
    }

}
