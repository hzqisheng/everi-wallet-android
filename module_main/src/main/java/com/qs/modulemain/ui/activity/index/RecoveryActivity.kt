package com.qs.modulemain.ui.activity.index

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
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
        tvTitle!!.text = "恢复身份"

        addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    when(it.type){
                        RxBusCenter.LOGIN -> onDataResult(it.msg)
                    }
                })

        tv_sure.setOnClickListener {
            var memo = et_import.text.toString()

            mWebView.evaluateJavascript(WebViewApi.importEVTWallet(memo,et_new_pwd.text.toString())){}
        }

    }

    private fun onDataResult(msg: String) {
        msg.logE()
        if(msg.isEmpty())return
        var baseBean = Gson().fromJson(msg, BaseData::class.java)
        baseBean.isSelect = 1
        baseBean.isCreate = 1

        val values = ContentValues()
        values.put("isSelect", 0);
        DataSupport.updateAll(BaseData::class.java,values,"isSelect = ?", "1")


        baseBean.save()
        mContext.sharedPref.publicKey = baseBean.publicKey
        mContext.sharedPref.privateKey = baseBean.privateKey
        mContext.sharedPref.password = baseBean.password
        mContext.sharedPref.mnemoinc = baseBean.mnemoinc
        getString(R.string.import_success).toast()
        finish()
        var intent = Intent(mContext, MainActivity::class.java)
        startActivity(intent)
    }


}
