package com.qs.modulemain.ui.activity.index

import android.annotation.SuppressLint
import com.google.gson.Gson
import com.qs.modulemain.R
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.logE
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.bean.CollectBean
import com.smallcat.shenhai.mvpbase.model.helper.MessageEvent
import com.smallcat.shenhai.mvpbase.model.helper.RxBus
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import com.smallcat.shenhai.mvpbase.utils.Base64Utils
import com.smallcat.shenhai.mvpbase.utils.qrcode_type
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_nfts_pay.*
import kotlin.collections.HashMap

class NFTsPayActivity : SimpleActivity() {

    private var domainName = ""
    private var tokenName = ""
    private var jsonData = ""

    override val layoutId: Int = R.layout.activity_nfts_pay

    @SuppressLint("SetTextI18n")
    override fun initData() {
        tvTitle?.text = "everiPass"
        domainName = intent.getStringExtra("domain")
        tokenName = intent.getStringExtra("token")
        tv_nfts.text = tokenName
        tv_domain.text = domainName

        addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    when (it.type) {
                        RxBusCenter.PAY_NFTS -> onQrData(it.msg)
                    }
                })

        val map = HashMap<String, Any>()
        map["autoDestroying"] = false
        map["domainName"] = domainName
        map["tokenName"] = tokenName
        jsonData = Gson().toJson(map)
        WebViewApi.getEVTLinkQrImage("everiPass", jsonData, "{\"autoReload\": true}").logE()
    }

    override fun onResume() {
        super.onResume()
        if (jsonData != "") {
            qrcode_type = RxBusCenter.PAY_NFTS
            mWebView.evaluateJavascript(WebViewApi.getEVTLinkQrImage("everiPass", jsonData, "{\"autoReload\": true}")) {}
        }
    }

    override fun onPause() {
        super.onPause()
        mWebView.evaluateJavascript(WebViewApi.stopEVTLinkQrImageReload()) {}
        qrcode_type = -1
    }

    private fun onQrData(msg: String) {
        val collectBean = Gson().fromJson<CollectBean>(msg, CollectBean::class.java)
        iv_qr_code.setImageBitmap(Base64Utils.base64ToBitmap(collectBean.dataUrl))
    }
}
