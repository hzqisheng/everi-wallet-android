package com.qs.modulemain.ui.activity.index

import android.content.Intent
import android.graphics.Bitmap
import com.google.gson.Gson
import com.qs.modulemain.R
import com.qs.modulemain.bean.ChooseGetBean
import com.qs.modulemain.bean.ReceScanBean
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
import com.smallcat.shenhai.mvpbase.utils.Base64Utils
import com.smallcat.shenhai.mvpbase.utils.lastPushTransaction
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_scan_collect.*
import java.text.DecimalFormat

class ScanCollectActivity : SimpleActivity() {

    private lateinit var mUseFts: ChooseGetBean
    //    private var sybid = 0
    private var isCollect = false
    private var scanResult: String = ""

    //最大付款数量
    private var maxAllowedAmount = 100F
    //收款id,用于判断付款方和收款方是否相同情况
    private var sybId = 0L
    //付款id
    private var selfSybId = -1L

    override val layoutId: Int
        get() = R.layout.activity_scan_collect

    override fun initData() {

        addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    when (it.type) {
                        RxBusCenter.SCAN_RECE -> RecvieSuccess(it.msg)
                    }
                })

        addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    when (it.type) {
                        RxBusCenter.NEED_PRIVATE_KEY -> showFingerPrintDialog()
                    }
                })


        if (intent.hasExtra("data")) {
            mUseFts = intent.getSerializableExtra("data") as ChooseGetBean

            textView6.text = mUseFts.sym_name

            var isHaveIcon = false
            for (meta in mUseFts.metas) {
                if (meta.value.isNotEmpty() && meta.value.contains(",")) {
                    val decodedByte: Bitmap? = Base64Utils.base64ToBitmap(meta.value) ?: continue
                    iv_img.setImageBitmap(decodedByte)
                    isHaveIcon = true
                }
            }
            if (!isHaveIcon && (mUseFts?.sym_name == "EVT" || mUseFts?.sym_name == "PEVT")) {
                iv_img.setImageResource(R.drawable.icon_fukuan_evt)
            } else if (!isHaveIcon) {
                iv_img.setImageResource(0)
            }
        }

        if (intent.hasExtra("scanResult")) {
            isCollect = true
            scanResult = intent.getStringExtra("scanResult")
        }

        maxAllowedAmount = intent.getFloatExtra("maxAllowedAmount", 100F)
        sybId = intent.getLongExtra("sybId", 0L)
        selfSybId = intent.getLongExtra("selfSybId", -1L)

        tv_sure.setOnClickListener {

            if (mUseFts == null) {
                getString(R.string.please_choose_currency_and_money).toast()
                return@setOnClickListener
            }

            if (et_pwd.text.toString().isEmpty()) {
                getString(R.string.please_choose_currency_and_money).toast()
                return@setOnClickListener
            }

            if (isCollect) {

                //收款
                var num = et_pwd.text.toString()

                if (num.toFloat() > maxAllowedAmount) {
                    getString(R.string.exceed_max_allowd_paid_amount).toast()
                    return@setOnClickListener
                }
                if (selfSybId == sybId) {
                    getString(R.string.payer_and_payee_is_the_same_one).toast()
                    return@setOnClickListener
                }

                var JING = ""
                for (i in 0..mUseFts!!.sym.split(",")[0].toInt() - 1) {
                    JING += "0";
                }
                val df = DecimalFormat("0." + JING)
                //linkBean!!.publicKeys[0]
                var json = Gson().toJson(ReceScanBean.createBean(scanResult, sharedPref.publicKey, df.format(num.toFloat()), mUseFts.sym.split("#")[1]))

                json.logE()

                lastPushTransaction = RxBusCenter.SCAN_RECE
                mWebView.evaluateJavascript(WebViewApi.pushTransaction("everipay", json)) {}
                return@setOnClickListener
            }

            val intent = Intent(this, ScanActivity::class.java)
            intent.putExtra("data", mUseFts)
            intent.putExtra("num", et_pwd.text.toString())
            intent.putExtra("ScanType", ScanActivity.RECE)
            startActivity(intent)
        }
    }

    private fun RecvieSuccess(msg: String) {
        var num: String = et_pwd.text.toString()

        var JING = ""
        for (i in 0..mUseFts!!.sym.split(",")[0].toInt() - 1) {
            JING += "0";
        }
        val df = DecimalFormat("0." + JING)

        var intent = Intent(this, PaySuccessActivity::class.java)
        intent.putExtra("data", "+" + df.format(num.toFloat()) + " " + textView6.text)
        startActivity(intent)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101) {
            if (resultCode == 101) {
                mUseFts = data?.getSerializableExtra("data") as ChooseGetBean

                textView6.text = mUseFts.name
//                iv_img.setImageResource(R.drawable.icon_fukuan_evt)
                var isHaveIcon = false
                for (meta in mUseFts.metas) {
                    if (meta.value.isNotEmpty() && meta.value.contains(",")) {
                        val decodedByte: Bitmap? = Base64Utils.base64ToBitmap(meta.value)
                                ?: continue
                        iv_img.setImageBitmap(decodedByte)
                        isHaveIcon = true
                    }
                }
                if (!isHaveIcon && (mUseFts?.sym_name == "EVT" || mUseFts?.sym_name == "PEVT")) {
                    iv_img.setImageResource(R.drawable.icon_fukuan_evt)
                } else if (!isHaveIcon) {
                    iv_img.setImageResource(0)
                }
            }
        }
    }

    private fun showFingerPrintDialog() {
        confirmPassword(sharedPref.isFinger, supportFragmentManager, object : FingerSuccessCallback() {
            override fun onCheckSuccess() {
                mWebView.evaluateJavascript(WebViewApi.needPrivateKeyResponse(sharedPref.privateKey)) {}
            }
        })
    }

}
