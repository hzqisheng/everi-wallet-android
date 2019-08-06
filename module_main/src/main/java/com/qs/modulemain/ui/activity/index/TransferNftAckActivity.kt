package com.qs.modulemain.ui.activity.index

import com.google.gson.Gson
import com.qs.modulemain.R
import com.qs.modulemain.bean.ServerCharge
import com.qs.modulemain.ui.adapter.PayeeAdapter
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
import kotlinx.android.synthetic.main.activity_transfer_nft_ack.*
import org.json.JSONObject
import java.math.BigDecimal
import java.util.ArrayList
import java.util.HashMap

class TransferNftAckActivity : SimpleActivity() {

    private var tokenName: String = ""
    private var domainName: String = ""
    private var mAddressList: ArrayList<String> = ArrayList()

    override val layoutId: Int
        get() = R.layout.activity_transfer_nft_ack

    override fun initData() {
        tvTitle?.text = getString(R.string.confirm_transform)
        domainName = intent.getStringExtra("domain")
        tokenName = intent.getStringExtra("token")
        mAddressList = intent.getStringArrayListExtra("payees")
        tv_name.text = tokenName
        addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    when (it.type) {
                        RxBusCenter.SERVICE_CHARGE -> onDataResult(it.msg)
                        RxBusCenter.TRANSFER_NFT -> {
                            dismissLoading()
                            getString(R.string.operation_success).toast()
                            finish()
                        }
                        RxBusCenter.NEED_PRIVATE_KEY -> showFingerPrintDialog()
                    }
                })
        tv_pay.text = sharedPref.publicKey
        rv_payee.adapter = PayeeAdapter(mAddressList)
        tv_ack.setOnClickListener {
            lastPushTransaction = RxBusCenter.TRANSFER_NFT
            val map = HashMap<String, Any>()
            map["domain"] = domainName
            map["name"] = tokenName
            map["to"] = mAddressList
            map["memo"] = ""
            val json = Gson().toJson(map)
            mWebView.evaluateJavascript(WebViewApi.pushTransaction("transfer", json, "{}", domainName, tokenName), null)
        }
        //获取交易手续费
        lastPushTransaction = RxBusCenter.REQUEST_PAY
        val map = HashMap<String, Any>()
        map["domain"] = domainName
        map["name"] = tokenName
        map["to"] = mAddressList
        map["memo"] = ""
        val json = Gson().toJson(map)
        var charge = ServerCharge()
        charge.availablePublicKeys = arrayListOf(sharedPref.publicKey)
        var jsss = Gson().toJson(charge)
        mWebView.evaluateJavascript(WebViewApi.getEstimatedChargeForTransaction("transfer", json, jsss), null)
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

    private fun onDataResult(msg: String) {
        msg.logE()
        val jsonObj = JSONObject(msg)
        tv_fees.text = BigDecimal((jsonObj.get("charge").toString().toFloat() * 0.00001f).toString()).toString()
    }

}
