package com.qs.modulemain.ui.activity.index

import com.qs.modulemain.R
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.utils.fitSystemAllScroll
import kotlinx.android.synthetic.main.activity_collect_success.*

class CollectSuccessActivity : SimpleActivity() {

    override val layoutId: Int
        get() = R.layout.activity_collect_success

    override fun initData() {
        iv_back.setOnClickListener { finish() }
    }

    override fun fitSystem() {
        fitSystemAllScroll(this)
    }

}
