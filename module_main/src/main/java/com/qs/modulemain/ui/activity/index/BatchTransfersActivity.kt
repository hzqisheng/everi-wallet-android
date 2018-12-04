package com.qs.modulemain.ui.activity.index

import com.qs.modulemain.R
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.start
import kotlinx.android.synthetic.main.activity_batch_transfers.*

class BatchTransfersActivity : SimpleActivity() {

    override val layoutId: Int
        get() = R.layout.activity_batch_transfers

    override fun initData() {
        tvTitle?.text = getString(R.string.batch_transfers)

        tv_choose_address.setOnClickListener { start(ChooseAddressActivity::class.java) }
    }


}
