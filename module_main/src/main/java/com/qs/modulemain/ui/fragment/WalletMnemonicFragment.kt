package com.qs.modulemain.ui.fragment


import android.content.Intent
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


class WalletMnemonicFragment : BaseFragment<RetrievePwdPresenter>(), RetrievePwdView {

    override fun initPresenter() {
        mPresenter = RetrievePwdPresenter(mContext)
    }

    override val layoutId: Int = R.layout.fragment_wallet_mnemonic

    override fun initData() {
        val str = getString(R.string.retrieve_pwd_msg)
        val s = "<font color=\"#3F7DEE\">${getString(R.string.Be_careful)}</font>$str"
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            tv_msg.text = Html.fromHtml(s, Html.FROM_HTML_MODE_LEGACY)
        } else {
            tv_msg.text = Html.fromHtml(s)
        }

        tv_sure.setOnClickListener {
            mWebView.evaluateJavascript(WebViewApi.importEVTWallet(et_import.text.toString(),et_new_pwd.text.toString())){}
        }


    }

    override fun onDataResult(msg: String) {
        super.onDataResult(msg)
        msg.logE()
        if(msg.isEmpty())return
        var baseBean = Gson().fromJson(msg,BaseData::class.java)
        baseBean.save()
        mContext.sharedPref.publicKey = baseBean.publicKey
        mContext.sharedPref.privateKey = baseBean.privateKey
        mContext.sharedPref.password = baseBean.password
        mContext.sharedPref.mnemoinc = baseBean.mnemoinc
        getString(R.string.import_success).toast()
        mActivity.finish()
        var intent = Intent(mContext,MainActivity::class.java)
        startActivity(intent)
    }





}
