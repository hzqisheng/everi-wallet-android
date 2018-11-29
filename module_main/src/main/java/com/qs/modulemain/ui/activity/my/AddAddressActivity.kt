package com.qs.modulemain.ui.activity.my

import com.qs.modulemain.R
import com.qs.modulemain.presenter.AddAddressPresenter
import com.qs.modulemain.view.AddAddressView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.extension.getResourceString

class AddAddressActivity : BaseActivity<AddAddressPresenter>(), AddAddressView {

    override fun initPresenter() {
        mPresenter = AddAddressPresenter(mContext)
    }

    override val layoutId: Int
        get() = R.layout.activity_add_address

    override fun initData() {
        tvTitle?.text = getResourceString(R.string.add_address)
    }

    override fun loadSuccess(data: Any) {
    }

}
