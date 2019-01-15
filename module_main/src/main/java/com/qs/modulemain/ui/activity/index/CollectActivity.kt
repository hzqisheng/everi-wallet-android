package com.qs.modulemain.ui.activity.index

import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.annotation.RequiresApi
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.google.gson.Gson
import com.qs.modulemain.R
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.getResourceString
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
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_pay2.*
import java.util.concurrent.TimeUnit
import io.reactivex.internal.util.NotificationLite.disposable
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.util.function.Consumer
import java.util.function.Function


class CollectActivity : SimpleActivity() {
    private var pwdDialog: Dialog? = null
//    private var mHandler:Handler = object:Handler(){
//        override fun dispatchMessage(msg: Message?) {
//            super.dispatchMessage(msg)
//            requestNet()
//        }
//    }

    override val layoutId: Int
        get() = R.layout.activity_pay2

    override fun initData() {
        tvTitle?.text = getString(R.string.collect)

        addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    when(it.type){
                        RxBusCenter.QRCODE_RECE -> onDataResult(it.msg)
                    }
                })

        tv_address.text = sharedPref.publicKey

        rb_sweep_payment.setOnClickListener {

            intent = Intent(this,ScanActivity::class.java)
            startActivity(intent)

        }

        iv_qr_code.setOnClickListener {
            addClipboard(this@CollectActivity,sharedPref.publicKey)
            getString(R.string.copy_success).toast()
        }

        requestNet()
    }

    fun requestNet(){
        var address = PayActivity.Address()
        address.address = sharedPref.publicKey
        qrcode_type = RxBusCenter.QRCODE_RECE
        mWebView.evaluateJavascript(WebViewApi.getEVTLinkQrImage("payeeCode", Gson().toJson(address),"{\"autoReload\": true}")){}

    }

    fun onDataResult(string: String){
        var collectBean = Gson().fromJson<CollectBean>(string,CollectBean::class.java)
        iv_qr_code.setImageBitmap(Base64Utils.base64ToBitmap(collectBean.dataUrl))
//        mHandler.sendEmptyMessageDelayed(0,5000)
    }

    override fun onPause() {
        super.onPause()
//        mHandler.removeMessages(0)
    }

    override fun onDestroy() {
        super.onDestroy()
        mWebView.evaluateJavascript(WebViewApi.stopEVTLinkQrImageReload()){}
    }



}


