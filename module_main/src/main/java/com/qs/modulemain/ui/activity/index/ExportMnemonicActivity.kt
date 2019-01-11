package com.qs.modulemain.ui.activity.index

import android.content.Intent
import android.text.Html
import com.qs.modulemain.R
import com.qs.modulemain.util.confirmPassword
import com.smallcat.shenhai.mvpbase.base.FingerSuccessCallback
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.model.bean.BaseData
import kotlinx.android.synthetic.main.activity_export_mnemonic.*

class ExportMnemonicActivity : SimpleActivity() {
    /** 当前传递过来的钱包 **/
    private lateinit var mWalletBean: BaseData
    private var isCreate: Boolean = false

    override val layoutId: Int
        get() = R.layout.activity_export_mnemonic

    override fun initData() {
        mWalletBean = intent.getSerializableExtra("data") as BaseData

        if (intent.hasExtra("isCreate")) {
            isCreate = intent.getBooleanExtra("isCreate", false)
        }

        tvTitle?.text = getString(R.string.export_mnemonic_code)
        tv_sure.setOnClickListener {
            if (isCreate) {
                Intent(this, ExportMnemonic2Activity::class.java).apply {
                    putExtra("data", mWalletBean.mnemoinc)
                    startActivity(this)
                    finish()
                }
            } else {
                showFingerPrintDialog()
            }

        }

        tv_exit.setOnClickListener {
            finish()
        }

        val str = getString(R.string.export_mnemonic)
        val s = "<font color=\"#3F7DEE\">${getString(R.string.import_note)}</font>$str"
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            tv_msg.text = Html.fromHtml(s, Html.FROM_HTML_MODE_LEGACY)
        } else {
            tv_msg.text = Html.fromHtml(s)
        }
    }

    private fun showFingerPrintDialog() {
        confirmPassword(mWalletBean.isFinger, supportFragmentManager, object : FingerSuccessCallback() {
            override fun onCheckSuccess() {
                Intent(this@ExportMnemonicActivity, ExportMnemonic2Activity::class.java).apply {
                    putExtra("data", mWalletBean.mnemoinc)
                    startActivity(this)
                    finish()
                }
            }
        }, mWalletBean.password)
    }

}
