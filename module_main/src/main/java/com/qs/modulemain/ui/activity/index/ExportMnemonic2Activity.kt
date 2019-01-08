package com.qs.modulemain.ui.activity.index

import com.qs.modulemain.R
import com.qs.modulemain.presenter.ExportMnemonicPresenter
import com.qs.modulemain.ui.activity.MainActivity
import com.qs.modulemain.view.ExportMenmonicView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.start
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.utils.addClipboard
import kotlinx.android.synthetic.main.activity_export_mnemonic2.*

class ExportMnemonic2Activity : BaseActivity<ExportMnemonicPresenter>(), ExportMenmonicView {

    override fun initPresenter() {
        mPresenter = ExportMnemonicPresenter(mContext)
    }

    override val layoutId: Int = R.layout.activity_export_mnemonic2

    override fun initData() {
        tvTitle?.text = getString(R.string.export_mnemonic_code)

        tv_mnemonic_code.text = intent.getStringExtra("data");

        tv_sure.setOnClickListener {

            addClipboard(this,intent.getStringExtra("data"))
            getString(R.string.copy_success).toast()

            start(MainActivity::class.java)

        }
    }


}
