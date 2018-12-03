package com.qs.modulemain.ui.activity.index

import android.text.Html
import com.qs.modulemain.R
import com.qs.modulemain.presenter.ExportPrivateKeyPresenter
import com.qs.modulemain.view.ExportprivateKeyView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import kotlinx.android.synthetic.main.activity_ex.*

class ExportPrivateKeyActivity : BaseActivity<ExportPrivateKeyPresenter>(), ExportprivateKeyView {

    override fun initPresenter() {
        mPresenter = ExportPrivateKeyPresenter(mContext)
    }

    override val layoutId: Int = R.layout.activity_ex

    override fun initData() {
        tvTitle?.text = getString(R.string.export_priate_key)

        val str = getString(R.string.export_private_key_msg)
        val s = "<font color=\"#3F7DEE\">${getString(R.string.Be_careful)}</font>$str"
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            tv_msg.text = Html.fromHtml(s, Html.FROM_HTML_MODE_LEGACY)
        } else {
            tv_msg.text = Html.fromHtml(s)
        }
    }

}
