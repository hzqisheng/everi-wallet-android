package com.qs.modulemain.ui.fragment


import android.text.Html
import com.qs.modulemain.R
import com.qs.modulemain.presenter.RetrievePwdPresenter
import com.qs.modulemain.view.RetrievePwdView
import com.smallcat.shenhai.mvpbase.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_wallet_mnemonic.*


class WalletPrivateKeyFragment : BaseFragment<RetrievePwdPresenter>(), RetrievePwdView {

    override fun initPresenter() {
        mPresenter = RetrievePwdPresenter(mContext)
    }

    override val layoutId: Int = R.layout.fragment_wallet_mnemonic

    override fun initData() {
        et_import.hint = getString(R.string.please_input_private_key_content)

        val str = getString(R.string.retrieve_pwd_msg)
        val s = "<font color=\"#3F7DEE\">${getString(R.string.Be_careful)}</font>$str"
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            tv_msg.text = Html.fromHtml(s, Html.FROM_HTML_MODE_LEGACY)
        } else {
            tv_msg.text = Html.fromHtml(s)
        }
    }




}
