package com.qs.modulemain.ui.activity.index

import com.qs.modulemain.R
import com.smallcat.shenhai.mvpbase.base.SimpleActivity

class ImportWalletActivity : SimpleActivity() {


    override val layoutId: Int
        get() = R.layout.activity_import_wallet

    override fun initData() {
        tvTitle?.text = getString(R.string.wallet_import)
    }

}
