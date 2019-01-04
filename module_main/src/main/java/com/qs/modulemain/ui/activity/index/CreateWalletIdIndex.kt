package com.qs.modulemain.ui.activity.index

import android.app.Activity
import android.os.Bundle
import com.qs.modulemain.R
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.start
import kotlinx.android.synthetic.main.activity_create_wallet_id_index.*

class CreateWalletIdIndex : SimpleActivity() {

    override val layoutId: Int
        get() = R.layout.activity_create_wallet_id_index

    override fun initData() {

        sharedPref.publicKey = ""
        sharedPref.privateKey = ""
        sharedPref.password = ""
        sharedPref.name = ""
        sharedPref.mnemoinc = ""

        tv_create_id.setOnClickListener {
            start(WalletAddIdActivity::class.java)
        }

        tv_recovery_id.setOnClickListener {
            start(RecoveryActivity::class.java)
        }
    }

}
