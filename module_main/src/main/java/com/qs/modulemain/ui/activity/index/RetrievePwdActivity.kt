package com.qs.modulemain.ui.activity.index

import android.text.Html
import com.qs.modulemain.R
import com.qs.modulemain.presenter.RetrievePwdPresenter
import com.qs.modulemain.view.RetrievePwdView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.extension.getResourceString
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.bean.BaseData
import kotlinx.android.synthetic.main.activity_retrieve_pwd.*


class RetrievePwdActivity : BaseActivity<RetrievePwdPresenter>(), RetrievePwdView {
    /** 当前传递过来的钱包 **/
    private lateinit var mWalletBean: BaseData

    override fun initPresenter() {
        mPresenter = RetrievePwdPresenter(mContext)
    }

    override val layoutId: Int
        get() = R.layout.activity_retrieve_pwd

    override fun initData() {
        mWalletBean = intent.getSerializableExtra("data") as BaseData

        tvTitle?.text = getString(R.string.wallet_import)
        val str = getString(R.string.retrieve_pwd_msg)
        val s = "<font color=\"#3F7DEE\">${getString(R.string.Be_careful)}</font>$str"
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            tv_msg.text = Html.fromHtml(s, Html.FROM_HTML_MODE_LEGACY)
        } else {
            tv_msg.text = Html.fromHtml(s)
        }


        var memo = et_import.text.toString()

        tvNext.setOnClickListener {
            if (mWalletBean.mnemoinc == memo) {
                if (et_new_pwd.text.toString() != et_new_pwd_confirm.text.toString()) {
                    getResourceString(R.string.password_not_equals).toast()
                    return@setOnClickListener
                }
                mWalletBean.password = et_new_pwd.text.toString()
                if (mWalletBean.update(mWalletBean.id.toLong()) > 0) {
                    "Success".toast()
                    sharedPref.password = mWalletBean.password
                    finish()
                } else {
                    "Falied".toast()
                }
            }
        }

    }

    override fun checkSuccess(msg: String) {

    }

}

