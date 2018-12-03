package com.qs.modulemain.ui.activity.index

import android.text.Html
import com.qs.modulemain.R
import com.qs.modulemain.presenter.RetrievePwdPresenter
import com.qs.modulemain.view.RetrievePwdView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import kotlinx.android.synthetic.main.activity_retrieve_pwd.*


class RetrievePwdActivity : BaseActivity<RetrievePwdPresenter>(), RetrievePwdView {

    override fun initPresenter() {
        mPresenter = RetrievePwdPresenter(mContext)
    }

    override val layoutId: Int
        get() = R.layout.activity_retrieve_pwd

    override fun initData() {
        tvTitle?.text = getString(R.string.wallet_import)

        val str = getString(R.string.retrieve_pwd_msg)
        val s = "<font color=\"#3F7DEE\">${getString(R.string.Be_careful)}</font>$str"
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            tv_msg.text = Html.fromHtml(s, Html.FROM_HTML_MODE_LEGACY)
        } else {
            tv_msg.text = Html.fromHtml(s)
        }
    }

}
