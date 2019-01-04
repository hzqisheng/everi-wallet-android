package com.qs.modulemain.ui.activity.index

import com.qs.modulemain.R
import com.qs.modulemain.ui.activity.MainActivity
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.start
import kotlinx.android.synthetic.main.activity_pay_success.*

class PaySuccessActivity : SimpleActivity() {

    override val layoutId: Int
        get() = R.layout.activity_pay_success

    override fun initData() {
      var  data = intent.getStringExtra("data")

        tv_pay.text  = data+" EVT"

        tv_sure.setOnClickListener {
            start(MainActivity::class.java)
        }
    }

}
