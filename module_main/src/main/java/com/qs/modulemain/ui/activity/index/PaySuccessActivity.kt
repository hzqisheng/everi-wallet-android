package com.qs.modulemain.ui.activity.index

import android.annotation.SuppressLint
import com.qs.modulemain.R
import com.qs.modulemain.ui.activity.MainActivity
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.start
import kotlinx.android.synthetic.main.activity_pay_success.*

class PaySuccessActivity : SimpleActivity() {

    override val layoutId: Int
        get() = R.layout.activity_pay_success

    @SuppressLint("SetTextI18n")
    override fun initData() {
        val data = intent.getStringExtra("data")
        tvTitle?.text = getString(R.string.transaction_success)
        tv_pay.text = data

        tv_sure.setOnClickListener {
            start(MainActivity::class.java)
        }
    }


}
