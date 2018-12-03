package com.qs.modulemain.ui.activity.index

import com.qs.modulemain.R
import com.qs.modulemain.presenter.ExportMnemonicPresenter
import com.qs.modulemain.view.ExportMenmonicView
import com.smallcat.shenhai.mvpbase.base.BaseActivity

class ExportMnemonic2Activity : BaseActivity<ExportMnemonicPresenter>(), ExportMenmonicView {

    override fun initPresenter() {
        mPresenter = ExportMnemonicPresenter(mContext)
    }

    override val layoutId: Int = R.layout.activity_export_mnemonic2

    override fun initData() {
        tvTitle?.text = getString(R.string.export_mnemonic_code)
    }


}
