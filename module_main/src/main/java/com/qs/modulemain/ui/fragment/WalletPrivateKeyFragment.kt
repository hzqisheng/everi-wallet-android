package com.qs.modulemain.ui.fragment


import android.content.ContentValues
import android.content.Intent
import android.text.Html
import com.qs.modulemain.R
import com.qs.modulemain.presenter.RetrievePwdPresenter
import com.qs.modulemain.ui.activity.MainActivity
import com.qs.modulemain.view.RetrievePwdView
import com.smallcat.shenhai.mvpbase.base.BaseFragment
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.bean.BaseData
import com.smallcat.shenhai.mvpbase.model.helper.MessageEvent
import com.smallcat.shenhai.mvpbase.model.helper.RxBus
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_wallet_mnemonic.*
import org.litepal.crud.DataSupport


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

        addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    when (it.type) {
                        RxBusCenter.CHECK_PRIVATE -> checkKey(it.msg)
                    }
                })

//        et_import.setText("5JrNgyyNDqz2pikijgdJwUktV8xkS7JPPSURr2YwxkhKPzm2eRi")
        tv_sure.setOnClickListener {
            val privateKey = et_import.text.toString()
            val oldPwd = et_new_pwd.text.toString()
            val newPwd = et_new_pwd_confirm.text.toString()

            if (privateKey.isEmpty()) {
                getString(R.string.private_key_not_empty).toast()
                return@setOnClickListener
            }

            if (oldPwd.length < 8) {
                getString(R.string.Password_must_not_be_less_than_8_bits).toast()
                return@setOnClickListener
            }
            if (oldPwd != newPwd) {
                getString(R.string.password_not_equals).toast()
                return@setOnClickListener
            }
            mWebView.evaluateJavascript(WebViewApi.isValidPrivateKey(privateKey), null)
        }
    }

    override fun checkSuccess(msg: String) {
    }

    private fun checkKey(msg: String){
        if (msg == "true") {
            mWebView.evaluateJavascript(WebViewApi.privateToPublic(et_import.text.toString()), null)
        } else {
            getString(R.string.error_private_key).toast()
        }
    }


    override fun onImport(msg: String) {
        super.onImport(msg)
        if (msg.isEmpty()) return
        var baseBean = BaseData()
        baseBean.privateKey = et_import.text.toString()
        baseBean.publicKey = msg
        baseBean.password = et_new_pwd.text.toString()
        baseBean.type = "EVT"

        val values = ContentValues()
        values.put("isSelect", 0)
        DataSupport.updateAll(BaseData::class.java, values, "isSelect = ?", "1")

        baseBean.isSelect = 1
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


}
