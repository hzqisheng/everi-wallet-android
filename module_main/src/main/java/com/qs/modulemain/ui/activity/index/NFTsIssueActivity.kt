package com.qs.modulemain.ui.activity.index

import com.qs.modulemain.R
import com.smallcat.shenhai.mvpbase.base.SimpleActivity

class NFTsIssueActivity : SimpleActivity() {

    override val layoutId: Int
        get() = R.layout.activity_nfts_issue

    override fun initData() {
        tvTitle?.text = getString(R.string.create_nfts)
        ivRight?.apply {
            setBackgroundResource(R.drawable.ic_question)
            setOnClickListener{}
        }
    }

}
