package com.qs.modulemain.ui.activity.my

import com.qs.modulemain.R
import com.qs.modulemain.presenter.ImportAddressPresenter
import com.qs.modulemain.view.ImportAddressView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.extension.getResourceString

class ImportAddressActivity : BaseActivity<ImportAddressPresenter>(), ImportAddressView {

    override fun initPresenter() {
        mPresenter = ImportAddressPresenter(mContext)
    }

    override val layoutId: Int
        get() = R.layout.activity_import_address

    override fun initData() {
        tvTitle?.text = getString(R.string.import_address)
    }


}
