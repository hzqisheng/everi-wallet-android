package com.qs.modulemain.ui.fragment


import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.text.Html
import com.google.gson.Gson
import com.qs.modulemain.R
import com.qs.modulemain.presenter.RetrievePwdPresenter
import com.qs.modulemain.ui.activity.MainActivity
import com.qs.modulemain.view.RetrievePwdView
import com.smallcat.shenhai.mvpbase.base.BaseFragment
import com.smallcat.shenhai.mvpbase.extension.logE
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.start
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.bean.BaseData
import kotlinx.android.synthetic.main.fragment_wallet_mnemonic.*
import org.litepal.crud.DataSupport
import org.litepal.crud.callback.FindCallback
import org.litepal.crud.callback.FindMultiCallback

private const val ARG_PARAM1 = "param1"

class WalletMnemonicChangeFragment : BaseFragment<RetrievePwdPresenter>(), RetrievePwdView {

    private lateinit var mWalletBean: BaseData

    override fun initPresenter() {
        mPresenter = RetrievePwdPresenter(mContext)
    }

    override val layoutId: Int = R.layout.fragment_wallet_mnemonic

    override fun initData() {

        et_new_pwd.hint = getString(R.string.new_pwd)
        et_new_pwd_confirm.hint = getString(R.string.confirm_new_pwd)

        mWalletBean = arguments?.getSerializable(ARG_PARAM1) as BaseData

        val str = getString(R.string.retrieve_pwd_msg)
        val s = "<font color=\"#3F7DEE\">${getString(R.string.Be_careful)}</font>$str"
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            tv_msg.text = Html.fromHtml(s, Html.FROM_HTML_MODE_LEGACY)
        } else {
            tv_msg.text = Html.fromHtml(s)
        }

        tv_sure.setOnClickListener {
            val memo = et_import.text.toString()
            if (memo.isEmpty()) {
                getString(R.string.input_memo).toast()
            }
            val oldPwd = et_new_pwd.text.toString()
            val newPwd = et_new_pwd_confirm.text.toString()

            if (oldPwd.length < 8) {
                getString(R.string.Password_must_not_be_less_than_8_bits).toast()
                return@setOnClickListener
            }
            if (oldPwd != newPwd) {
                getString(R.string.password_not_equals).toast()
                return@setOnClickListener
            }
            if (mWalletBean.mnemoinc == memo) {
                mWalletBean.password = oldPwd
                if (mWalletBean.update(mWalletBean.id.toLong()) > 0) {
                    "Success".toast()
                    if (mContext.sharedPref.publicKey == mWalletBean.publicKey) {
                        mContext.sharedPref.password = mWalletBean.password
                    }
                    mActivity.finish()
                } else {
                    "Falied".toast()
                }
            }
        }

    }

    override fun checkSuccess(msg: String) {
        if (msg == "true") {
            mWebView.evaluateJavascript(WebViewApi.importEVTWallet(et_import.text.toString(), et_new_pwd.text.toString()), null)
        } else {
            getString(R.string.memo_error).toast()
        }
    }

    override fun onDataResult(msg: String) {
        super.onDataResult(msg)
        msg.logE()
        if (msg.isEmpty()) return
        var baseBean = Gson().fromJson(msg, BaseData::class.java)
        baseBean.save()
        mContext.sharedPref.publicKey = baseBean.publicKey
        mContext.sharedPref.privateKey = baseBean.privateKey
        mContext.sharedPref.password = baseBean.password
        mContext.sharedPref.mnemoinc = baseBean.mnemoinc
        mContext.sharedPref.isFinger = 0
        getString(R.string.import_address_success).toast()
        mActivity.finish()
        var intent = Intent(mContext, MainActivity::class.java)
        startActivity(intent)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: BaseData) =
                WalletMnemonicChangeFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(ARG_PARAM1, param1)
                    }
                }
    }

}
