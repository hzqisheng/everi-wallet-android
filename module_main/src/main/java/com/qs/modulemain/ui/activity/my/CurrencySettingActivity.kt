package com.qs.modulemain.ui.activity.my

import android.view.View
import com.qs.modulemain.R
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import kotlinx.android.synthetic.main.activity_currency_setting.*

class CurrencySettingActivity : SimpleActivity() {

    override val layoutId: Int
        get() = R.layout.activity_currency_setting

    override fun initData() {
        tvTitle?.text = getString(R.string.currency_setting)
        if(sharedPref.currency == 0){
            iv_choose1.visibility = View.VISIBLE
        }else{
            iv_choose2.visibility = View.VISIBLE
        }
        ll_cny.setOnClickListener {
            sharedPref.currency = 0
            iv_choose2.visibility = View.GONE
            iv_choose1.visibility = View.VISIBLE
        }
        ll_usd.setOnClickListener {
            sharedPref.currency = 1
            iv_choose2.visibility = View.VISIBLE
            iv_choose1.visibility = View.GONE
        }
    }

}
