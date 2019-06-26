package com.qs.modulemain.ui.activity.index

import android.content.ContentValues
import android.content.Intent
import com.google.gson.Gson
import com.qs.modulemain.R
import com.qs.modulemain.ui.activity.MainActivity
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.logE
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.bean.BaseData
import com.smallcat.shenhai.mvpbase.model.helper.MessageEvent
import com.smallcat.shenhai.mvpbase.model.helper.RxBus
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_recovery.*
import org.litepal.crud.DataSupport

class RecoveryActivity : SimpleActivity() {


    override val layoutId: Int
        get() = R.layout.activity_recovery

    override fun initData() {
        tvTitle!!.text = getString(R.string.recover_id)

        addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    when(it.type){
                        RxBusCenter.LOGIN -> onDataResult(it.msg)
                        RxBusCenter.CHECK_MEMO -> checkSuccess(it.msg)
                    }
                })

        tv_sure.setOnClickListener {
            val memo = et_import.text.toString()
            if (memo.isEmpty()){
                getString(R.string.input_memo).toast()
            }
            val oldPwd = et_new_pwd.text.toString()
            val newPwd = et_new_pwd_confirm.text.toString()

            if (oldPwd.length < 8){
                getString(R.string.Password_must_not_be_less_than_8_bits).toast()
                return@setOnClickListener
            }
            if(oldPwd != newPwd){
                getString(R.string.password_not_equals).toast()
                return@setOnClickListener
            }
            showLoading()
            mWebView.evaluateJavascript(WebViewApi.validateMnemonic(memo), null)
        }

    }

    private fun onDataResult(msg: String) {
        dismissLoading()
        msg.logE()
        if(msg.isEmpty())return
        var baseBean = Gson().fromJson(msg, BaseData::class.java)
        baseBean.isSelect = 1
        baseBean.isCreate = 1

        val values = ContentValues()
        values.put("isSelect", 0)
        DataSupport.updateAll(BaseData::class.java,values,"isSelect = ?", "1")


        baseBean.save()
        mContext.sharedPref.publicKey = baseBean.publicKey
        mContext.sharedPref.privateKey = baseBean.privateKey
        mContext.sharedPref.password = baseBean.password
        mContext.sharedPref.mnemoinc = baseBean.mnemoinc
        getString(R.string.recovery_success).toast()
        finish()
        var intent = Intent(mContext, MainActivity::class.java)
        startActivity(intent)
    }

    private fun checkSuccess(msg: String){
        if (msg == "true") {
            mWebView.evaluateJavascript(WebViewApi.importEVTWallet(et_import.text.toString(), et_new_pwd.text.toString()), null)
        }else {
            dismissLoading()
            getString(R.string.memo_error).toast()
        }
    }
}
