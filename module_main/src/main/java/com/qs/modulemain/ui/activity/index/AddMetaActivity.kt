package com.qs.modulemain.ui.activity.index

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.qs.modulemain.R
import com.qs.modulemain.bean.ChooseGetBean
import com.qs.modulemain.util.confirmPassword
import com.smallcat.shenhai.mvpbase.base.FingerSuccessCallback
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.helper.MessageEvent
import com.smallcat.shenhai.mvpbase.model.helper.RxBus
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import com.smallcat.shenhai.mvpbase.utils.lastPushTransaction
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_add_fts.*
import kotlinx.android.synthetic.main.activity_add_meta.*

/** 元数据 **/
class AddMetaActivity : SimpleActivity() {
    private lateinit var mMetaBean: ChooseGetBean.MetasBean

    override val layoutId: Int
        get() = R.layout.activity_add_meta

    override fun initData() {

        addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    when (it.type) {
                        RxBusCenter.ADD_META -> {
                            dismissLoading()
                            var intent = Intent()
                            intent.putExtra("result", mMetaBean)
                            setResult(1, intent)
                            finish()
                        }
                        RxBusCenter.NEED_PRIVATE_KEY -> showPassWordDialog()
                        RxBusCenter.REQUEST_ERROR -> dismissLoading()
                    }
                })

        tvSave.setOnClickListener {

            if (edKey.text.toString().isEmpty()) {
                "Invalid Key".toast()
                return@setOnClickListener
            }

            if (edValue.text.toString().isEmpty()) {
                "Invalid Value".toast()
                return@setOnClickListener
            }

            mMetaBean = ChooseGetBean.MetasBean()
            mMetaBean.key = edKey.text.toString()
            mMetaBean.value = edValue.text.toString()
            mMetaBean.creator = "[A] " + sharedPref.publicKey
            if (intent.getBooleanExtra("GroupDetailAdd", false)) {
                lastPushTransaction = RxBusCenter.ADD_META
                val map = HashMap<String, Any>()
                map["key"] = mMetaBean.key
                map["value"] = mMetaBean.value
                map["creator"] = mMetaBean.creator
                val json = Gson().toJson(map)
                val groupName = intent.getStringExtra("groupName")
                mWebView.evaluateJavascript(WebViewApi.pushTransaction("addmeta", json, "{}", ".group", groupName), null)
            } else if (intent.getBooleanExtra("DomainDetailAdd", false)) {
                lastPushTransaction = RxBusCenter.ADD_META
                val map = HashMap<String, Any>()
                map["key"] = mMetaBean.key
                map["value"] = mMetaBean.value
                map["creator"] = mMetaBean.creator
                val json = Gson().toJson(map)
                val domainName = intent.getStringExtra("domainName")
                mWebView.evaluateJavascript(WebViewApi.pushTransaction("addmeta", json, "{}", domainName, ".meta"), null)
            } else {
                var intent = Intent()
                intent.putExtra("result", mMetaBean)
                setResult(1, intent)
                finish()
            }

        }

    }

    /** 显示输入密码 **/
    fun showPassWordDialog() {
        confirmPassword(mContext.sharedPref.isFinger, supportFragmentManager, object : FingerSuccessCallback() {
            override fun onCheckSuccess() {
                mWebView.evaluateJavascript(WebViewApi.needPrivateKeyResponse(sharedPref.privateKey)) {
                    showLoading()
                }
            }
        })
    }


}
