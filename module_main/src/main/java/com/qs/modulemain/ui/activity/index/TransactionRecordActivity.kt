package com.qs.modulemain.ui.activity.index

import com.qs.modulemain.R
import com.qs.modulemain.ui.activity.MainActivity
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.start
import com.smallcat.shenhai.mvpbase.utils.fitSystemAllScroll
import kotlinx.android.synthetic.main.activity_transaction_record.*

class TransactionRecordActivity : SimpleActivity() {

    override val layoutId: Int
        get() = R.layout.activity_transaction_record

    override fun initData() {
        tv_sure.setOnClickListener { start(MainActivity::class.java) }
    }

    override fun fitSystem() {
        fitSystemAllScroll(this)
    }

}
