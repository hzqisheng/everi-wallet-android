package com.qs.modulemain.ui.activity.index

import com.qs.modulemain.R
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.start
import kotlinx.android.synthetic.main.activity_import_wallet.*

class ImportWalletActivity : SimpleActivity() {


    override val layoutId: Int
        get() = R.layout.activity_import_wallet

    override fun initData() {
        tvTitle?.text = getString(R.string.wallet_import)
        tv_evt.setOnClickListener {
            start(ImportWalletMnemonicActivity::class.java)
        }
    }

}
