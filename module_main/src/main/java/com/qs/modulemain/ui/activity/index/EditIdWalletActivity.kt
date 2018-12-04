package com.qs.modulemain.ui.activity.index

import com.qs.modulemain.R
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.start
import kotlinx.android.synthetic.main.activity_edit_id_wallet.*

class EditIdWalletActivity : SimpleActivity() {

    override val layoutId: Int
        get() = R.layout.activity_edit_id_wallet

    override fun initData() {
        tvTitle?.text = getString(R.string.erit_id_wallet)
        tv_change_pwd.setOnClickListener { start(ChangePwdActivity::class.java) }
        tv_retrieve_pwd.setOnClickListener { start(RetrievePwdActivity::class.java) }
        tv_export.setOnClickListener { start(ExportMnemonicActivity::class.java) }
        tv_add.setOnClickListener { start(WalletAddIdActivity::class.java) }
    }

}
