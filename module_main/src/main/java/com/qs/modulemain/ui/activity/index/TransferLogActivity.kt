package com.qs.modulemain.ui.activity.index

import android.app.Activity
import android.content.Intent
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.qs.modulemain.R
import com.qs.modulemain.bean.TransferLogBean
import com.qs.modulemain.ui.adapter.TransferLogItemAdapter
import com.qs.modulemain.util.DataUtils
import com.qs.modulemain.util.confirmPassword
import com.smallcat.shenhai.mvpbase.base.FingerSuccessCallback
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.logE
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.helper.MessageEvent
import com.smallcat.shenhai.mvpbase.model.helper.RxBus
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import com.smallcat.shenhai.mvpbase.utils.lastPushTransaction
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_transfer_log.*
import java.util.ArrayList

class TransferLogActivity : SimpleActivity() {

    private var tokenName: String = ""
    private var domainName: String = ""
    private var resultBean: ArrayList<TransferLogBean> = ArrayList()
    private lateinit var mAdapter: TransferLogItemAdapter
    private var mStateEnable = false

    override val layoutId: Int
        get() = R.layout.activity_transfer_log

    override fun initData() {
        tvTitle?.text = getString(R.string.transfer_log)
        domainName = intent.getStringExtra("domain")
        tokenName = intent.getStringExtra("token")
        addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    when (it.type) {
                        RxBusCenter.RECORD_TRANSACATION -> {
                            onDataResult(it.msg)
                        }
                        RxBusCenter.DESTROY_NFT -> {
                            dismissLoading()
                            getString(R.string.destroy_success).toast()
                            finish()
                        }
                        RxBusCenter.NEED_PRIVATE_KEY -> {
                            if (mStateEnable) {
                                showFingerPrintDialog()
                            }
                        }
                    }
                })
        mAdapter = TransferLogItemAdapter(resultBean)
        mAdapter.emptyView = DataUtils.getEmptyView(mContext, getString(R.string.no_record))
        rv_list.adapter = mAdapter
        swipe_refresh.setOnRefreshListener {
            getActions()
        }
        tv_transfer.setOnClickListener {
            Intent(this@TransferLogActivity, TransferNftActivity::class.java).apply {
                putExtra("domain", domainName)
                putExtra("token", tokenName)
                startActivity(this)
            }
        }
        tv_destroy.setOnClickListener {
            lastPushTransaction = RxBusCenter.DESTROY_NFT
            val map = java.util.HashMap<String, Any>()
            map["domain"] = domainName
            map["name"] = tokenName
            val json = Gson().toJson(map)
            mWebView.evaluateJavascript(WebViewApi.pushTransaction("destroytoken", json, "{}", domainName, tokenName), null)
        }
    }

    override fun onResume() {
        super.onResume()
        mStateEnable = true
        getActions()
    }

    override fun onStop() {
        super.onStop()
        mStateEnable = false
    }

    private fun getActions() {
        val map = HashMap<String, Any>()
        map["domain"] = domainName
        map["names"] = arrayOf("transfer")
        map["key"] = tokenName
        map["skip"] = 0
        map["take"] = 10
        val jsonData = Gson().toJson(map)
        jsonData.logE()
        mWebView.evaluateJavascript(WebViewApi.getActions(jsonData), null)
    }

    private fun onDataResult(msg: String) {
        swipe_refresh.isRefreshing = false
        if (msg.isEmpty()) return
        val result = Gson().fromJson<java.util.ArrayList<TransferLogBean>>(msg, object : TypeToken<ArrayList<TransferLogBean>>() {}.type)
        resultBean.clear()
        resultBean.addAll(result)
        mAdapter.notifyDataSetChanged()
    }

    private fun showFingerPrintDialog() {
        confirmPassword(mContext.sharedPref.isFinger, supportFragmentManager, object : FingerSuccessCallback() {
            override fun onCheckSuccess() {
                mWebView.evaluateJavascript(WebViewApi.needPrivateKeyResponse(sharedPref.privateKey)) {
                    showLoading()
                }
            }
        })
    }

}
