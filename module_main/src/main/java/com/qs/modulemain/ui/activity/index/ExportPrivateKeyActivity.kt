package com.qs.modulemain.ui.activity.index

import android.text.Html
import com.qs.modulemain.R
import com.qs.modulemain.presenter.ExportPrivateKeyPresenter
import com.qs.modulemain.view.ExportprivateKeyView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.model.bean.BaseData
import com.smallcat.shenhai.mvpbase.utils.addClipboard
import kotlinx.android.synthetic.main.activity_ex.*

class ExportPrivateKeyActivity : BaseActivity<ExportPrivateKeyPresenter>(), ExportprivateKeyView {
    private var mWalletName : BaseData? = null

    override fun initPresenter() {
        mPresenter = ExportPrivateKeyPresenter(mContext)
    }

    override val layoutId: Int = R.layout.activity_ex

    override fun initData() {
        tvTitle?.text = getString(R.string.export_priate_key)

        mWalletName = intent.getSerializableExtra("data") as BaseData

        val str = getString(R.string.export_private_key_msg)
        val s = "<font color=\"#3F7DEE\">${getString(R.string.Be_careful)}</font>$str"
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            tv_msg.text = Html.fromHtml(s, Html.FROM_HTML_MODE_LEGACY)
        } else {
            tv_msg.text = Html.fromHtml(s)
        }

        tv_address.text = mWalletName!!.publicKey

        tv_key.text = mWalletName!!.privateKey

        tv_address.setOnClickListener {
            addClipboard(this,mWalletName!!.publicKey)
            getString(R.string.copy_success).toast()
            finish()
        }

        tv_key.setOnClickListener {
            addClipboard(this,mWalletName!!.privateKey)
            getString(R.string.copy_success).toast()
            finish()
        }
    }

}
