package com.qs.modulemain.ui.activity.index

import com.qs.modulemain.R
import com.qs.modulemain.ui.activity.my.AddressManageActivity
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.getResourceColor
import com.smallcat.shenhai.mvpbase.extension.start

class ChooseAddressActivity : SimpleActivity() {

    override val layoutId: Int
        get() = R.layout.activity_choose_address

    override fun initData() {
        tvTitle?.text = getString(R.string.choose_address)
        tvRight?.apply {
            text = getString(R.string.address_manage)
            setTextColor(getResourceColor(R.color.color_e4))
            setOnClickListener{
                start(AddressManageActivity::class.java)
            }
        }
    }
}

