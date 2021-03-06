package com.qs.modulemain.ui.activity.index

import android.content.Intent
import android.util.Log
import com.google.gson.Gson
import com.qs.modulemain.R
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.logE
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.bean.CollectBean
import com.smallcat.shenhai.mvpbase.model.helper.MessageEvent
import com.smallcat.shenhai.mvpbase.model.helper.RxBus
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import com.smallcat.shenhai.mvpbase.utils.Base64Utils
import com.smallcat.shenhai.mvpbase.utils.addClipboard
import com.smallcat.shenhai.mvpbase.utils.qrcode_type
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_pay2.*
import java.util.HashMap


class CollectActivity : SimpleActivity() {

//    private var mHandler:Handler = object:Handler(){
//        override fun dispatchMessage(msg: Message?) {
//            super.dispatchMessage(msg)
//            requestNet()
//        }
//    }

    // 1000 需要地址
    private var fungibleId: Int = 0

    override val layoutId: Int
        get() = R.layout.activity_pay2

    override fun initData() {
        tvTitle?.text = getString(R.string.collect)

        if (intent != null && intent.hasExtra("fungibleId")) {
            fungibleId = intent.getIntExtra("fungibleId", 0)
        }

        addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    when (it.type) {
                        RxBusCenter.QRCODE_RECE -> onDataResult(it.msg)
                    }
                })

        tv_address.text = sharedPref.publicKey

        rb_sweep_payment.setOnClickListener {

            intent = Intent(this, ScanActivity::class.java)
            intent.putExtra("ScanType", 10001)
            startActivity(intent)
        }

        iv_qr_code.setOnClickListener {
            addClipboard(this@CollectActivity, sharedPref.publicKey)
            getString(R.string.copy_success).toast()
        }

        requestNet()
    }

    override fun onResume() {
        super.onResume()
        rb_pay_code.isChecked = true
    }

    private fun requestNet() {
        var address = PayActivity.Address()
        address.address = sharedPref.publicKey
        qrcode_type = RxBusCenter.QRCODE_RECE

        if (fungibleId != 0) {
            val map = HashMap<String, Any>()
            map["address"] = address.address
            map["fungibleId"] = fungibleId
            Log.e("getEVTLinkQrImage", Gson().toJson(map))
            mWebView.evaluateJavascript(WebViewApi.getEVTLinkQrImage("payeeCode", Gson().toJson(map), "{\"autoReload\": true}")) {}
        } else
            mWebView.evaluateJavascript(WebViewApi.getEVTLinkQrImage("payeeCode", Gson().toJson(address), "{\"autoReload\": true}")) {}
    }

    fun onDataResult(string: String) {
        var collectBean = Gson().fromJson<CollectBean>(string, CollectBean::class.java)
        iv_qr_code.setImageBitmap(Base64Utils.base64ToBitmap(collectBean.dataUrl))
//        mHandler.sendEmptyMessageDelayed(0,5000)
    }

    override fun onPause() {
        super.onPause()
//        mHandler.removeMessages(0)
    }

    override fun onDestroy() {
        super.onDestroy()
        mWebView.evaluateJavascript(WebViewApi.stopEVTLinkQrImageReload()) {}
    }


}


