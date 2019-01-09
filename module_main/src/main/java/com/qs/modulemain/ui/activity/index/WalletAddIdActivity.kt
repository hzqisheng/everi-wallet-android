package com.qs.modulemain.ui.activity.index

import android.content.Intent
import android.os.Build
import android.text.Html
import com.google.gson.Gson
import com.qs.modulemain.R
import com.qs.modulemain.ui.activity.MainActivity
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.bean.BaseData
import com.smallcat.shenhai.mvpbase.model.helper.MessageEvent
import com.smallcat.shenhai.mvpbase.model.helper.RxBus
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_wallet_add_id.*

class WalletAddIdActivity : SimpleActivity() {

    override val layoutId: Int
        get() = R.layout.activity_wallet_add_id

    override fun initData() {
        tvTitle?.text = getString(R.string.add_id_wallet)

        addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    when (it.type) {
                        RxBusCenter.CREATE_WALL -> {
                            onDateResult(it.msg)
                        }
                    }
                })

        val str = getString(R.string.export_mnemonic)
        val s = "<font color=\"#3F7DEE\">${getString(R.string.Be_careful)}</font>$str"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tv_msg.text = Html.fromHtml(s, Html.FROM_HTML_MODE_LEGACY)
        } else {
            tv_msg.text = Html.fromHtml(s)
        }


        tv_create.setOnClickListener {

            val oldPwd = et_new_pwd.text.toString()
            val newPwd = et_new_pwd_confirm.text.toString()

            if(oldPwd != newPwd){
                getString(R.string.password_not_equals).toast()
                return@setOnClickListener
            }

            mWebView.evaluateJavascript(WebViewApi.createEVTWallet(et_new_pwd.text.toString()), null)
        }
    }

    private fun onDateResult(msg: String) {
        if (msg.isEmpty()) return
        val baseBean = Gson().fromJson(msg, BaseData::class.java)
        baseBean.isCreate = 1
        baseBean.isSelect = 1
        baseBean.save()
        mContext.sharedPref.publicKey = baseBean.publicKey
        mContext.sharedPref.privateKey = baseBean.privateKey
        mContext.sharedPref.password = baseBean.password
        mContext.sharedPref.mnemoinc = baseBean.mnemoinc
        getString(R.string.import_success).toast()
        finish()

        val intent = Intent(mContext, MainActivity::class.java)
        intent.putExtra("data",baseBean)
        intent.putExtra("isCreate",true)
        startActivity(intent)

        val intent1 = Intent(mContext, ExportMnemonicActivity::class.java)
        intent1.putExtra("data",baseBean)
        intent1.putExtra("isCreate",true)
        startActivity(intent1)
    }

}
