package com.qs.modulemain.ui.activity.index

import android.content.Intent
import android.os.Bundle
import com.qs.modulemain.R
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.start
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.model.bean.BaseData
import kotlinx.android.synthetic.main.activity_edit_id_wallet.*

class EditIdWalletActivity : SimpleActivity() {
    /** 当前传递过来的钱包 **/
    private lateinit var mWalletBean:BaseData

    override val layoutId: Int
        get() = R.layout.activity_edit_id_wallet

    override fun initData() {
        //获取数据
        mWalletBean = intent.getSerializableExtra("data") as BaseData

        if(mWalletBean == null){
            "Get data error !".toast()
        }

        tvTitle?.text = getString(R.string.erit_id_wallet)
        tv_change_pwd.setOnClickListener {
            var intent = Intent(this,ChangePwdActivity::class.java)
            intent.putExtra("data",mWalletBean)
            startActivity(intent)
        }
        tv_retrieve_pwd.setOnClickListener {
            var intent = Intent(this,RetrievePwdActivity::class.java)
            intent.putExtra("data",mWalletBean)
            startActivity(intent)
        }
        tv_export.setOnClickListener {
            var intent = Intent(this,ExportMnemonicActivity::class.java)
            intent.putExtra("data",mWalletBean)
            startActivity(intent)
        }
        tv_add.setOnClickListener { start(WalletAddIdActivity::class.java) }
    }

}
