package com.qs.modulemain.ui.activity.index

import android.text.Html
import com.qs.modulemain.R
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import kotlinx.android.synthetic.main.activity_wallet_add_id.*

class WalletAddIdActivity : SimpleActivity() {

    override val layoutId: Int
        get() = R.layout.activity_wallet_add_id

    override fun initData() {
        tvTitle?.text = getString(R.string.add_id_wallet)

        val str = getString(R.string.export_mnemonic)
        val s = "<font color=\"#3F7DEE\">${getString(R.string.Be_careful)}</font>$str"
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            tv_msg.text = Html.fromHtml(s, Html.FROM_HTML_MODE_LEGACY)
        } else {
            tv_msg.text = Html.fromHtml(s)
        }
    }

}
