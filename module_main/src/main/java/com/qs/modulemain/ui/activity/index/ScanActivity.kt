package com.qs.modulemain.ui.activity.index

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import cn.bingoogolapple.qrcode.core.QRCodeView
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.gson.Gson
import com.matisse.Matisse
import com.matisse.MimeTypeManager
import com.matisse.entity.ConstValue
import com.qs.modulemain.R
import com.qs.modulemain.arouter.ARouterConfig
import com.qs.modulemain.bean.ChooseFTSBean
import com.qs.modulemain.bean.ChooseGetBean
import com.qs.modulemain.bean.ReceScanBean
import com.qs.modulemain.bean.ScanResultLinkeBean
import com.qs.modulemain.ui.fragment.AssetsItemFragment
import com.qs.modulemain.util.confirmPassword
import com.smallcat.shenhai.mvpbase.base.FingerSuccessCallback
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.*
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.helper.MessageEvent
import com.smallcat.shenhai.mvpbase.model.helper.RxBus
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter.SCAN_QRLINKE
import com.smallcat.shenhai.mvpbase.utils.Glide4Engine
import com.smallcat.shenhai.mvpbase.utils.lastPushTransaction
import com.smallcat.shenhai.mvpbase.utils.qrcode_type
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_scan.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.DecimalFormat

@Route(path = ARouterConfig.ASSETS_SCAN)
class ScanActivity : SimpleActivity() {
    /** 密码框 **/

    companion object {
        var resultCode = 0x101000
        //付款
        const val PAY = 0x2001
        //收款
        const val RECE = 0x2002
    }

    // 1000 需要地址
    private var scanType: Int = 0

    //付款的币种
    private lateinit var mUseFts: ChooseGetBean

    //最大付款数量
    private var maxAllowedAmount = 100F
    //收款id,用于判断付款方和收款方是否是同一个
    private var sybId = 0L
    //付款id
    private var selfSybId = -1L

    override val layoutId: Int
        get() = R.layout.activity_scan

    @SuppressLint("CheckResult")
    override fun initData() {
        tvTitle?.text = getString(R.string.scan)
        tvRight?.apply {
            text = getString(R.string.album)
            setTextColor(getResourceColor(R.color.white))
            setOnClickListener {

                Matisse.from(this@ScanActivity)
                        .choose(MimeTypeManager.ofImage(), false)
                        .isCrop(false)
                        .setStatusIsDark(false)
                        .theme(R.style.Matisse_Dark)
                        .maxSelectable(1)
                        .thumbnailScale(0.85f)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .imageEngine(Glide4Engine())
                        .forResult(ConstValue.REQUEST_CODE_CHOOSE)

            }
        }
        val rxPermissions = RxPermissions(this)

        addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    when (it.type) {
                        RxBusCenter.SCAN_RESULT -> {
                            val intent = Intent(this@ScanActivity, ScanResultActivity::class.java)
                            intent.putExtra("scanResult", scanResult)
                            startActivity(intent)
                            finish()
                        }
                    }
                })

        if (intent != null && intent.hasExtra("ScanType")) {
            scanType = intent.getIntExtra("ScanType", 0)
            selfSybId = intent.getLongExtra("selfSybId", -1)

            if (scanType == RECE) {
                mUseFts = intent.getSerializableExtra("data") as ChooseGetBean
                addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { it ->
                            when (it.type) {
                                RxBusCenter.QRCODE_RECE -> onReceResult(it.msg)
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

                addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { it ->
                            when (it.type) {
                                RxBusCenter.SCAN_RECE -> RecvieSuccess(it.msg)
                            }
                        })
            }

            addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { it ->
                        when (it.type) {
                            RxBusCenter.SCAN_QRLINKE -> onLinkeResult(it.msg)
                        }
                    })

            addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { it ->
                        when (it.type) {
                            RxBusCenter.SYMBOL_DETAIL -> onFTSDetail(it.msg)
                        }
                    })

        }


        zxingview.setDelegate(object : QRCodeView.Delegate {
            override fun onScanQRCodeSuccess(result: String?) {
                if(result.isNullOrEmpty()){
                    getString(R.string.QR_not_found).toast()
                    return
                }
                scanResult = result!!
                when (scanType) {
                    0 -> {
                        val intent = Intent()
                        intent.putExtra("result", result)
                        setResult(resultCode, intent)
                        finish()
                    }
                    RECE -> {
                        qrcode_type = RxBusCenter.SCAN_QRLINKE
                        mWebView.evaluateJavascript(WebViewApi.parseEvtLink(result!!)) {}
                    }
                    1000 -> {
                        qrcode_type = RxBusCenter.SCAN_QRLINKE
                        mWebView.evaluateJavascript(WebViewApi.parseEvtLink(result!!)) {}
                    }
                    10001 -> {
                        qrcode_type = RxBusCenter.SCAN_QRLINKE
                        mWebView.evaluateJavascript(WebViewApi.parseEvtLink(result!!)) {}
                    }
                }
            }

            override fun onCameraAmbientBrightnessChanged(isDark: Boolean) {
            }

            override fun onScanQRCodeOpenCameraError() {

            }

        })

        rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe { granted ->
                    if (granted!!) { // Always true pre-M
//                       zxingview.startCamera()
                        "start scan".logE()
                        zxingview.startCamera()
                    } else {
                        getString(R.string.need_camerl_permission).toast()
                    }
                }

    }

    private fun onFTSDetail(msg: String) {
        "onFTSDetail".logE()
        if (msg.isNullOrBlank()) return
        var bean: ChooseGetBean = Gson().fromJson(msg, ChooseGetBean::class.java)
        msg.logE()
        intent = Intent(this, ScanCollectActivity::class.java)
        intent.putExtra("data", bean)
        intent.putExtra("scanResult", scanResult)
        val decimal = bean.sym.split(",")[0].toDouble()
        val number = Math.pow(10.0, decimal).toFloat()
        intent.putExtra("maxAllowedAmount", maxAllowedAmount / number)
        intent.putExtra("sybId", sybId)
        intent.putExtra("selfSybId", selfSybId)
        startActivity(intent)
        finish()

    }

    private fun RecvieSuccess(msg: String) {

        var num = intent.getStringExtra("num")

        var JING = ""
        for (i in 0..mUseFts!!.sym.split(",")[0].toInt() - 1) {
            JING += "0"
        }
        val df = DecimalFormat("0." + JING)

        var intent = Intent(this, PaySuccessActivity::class.java)
        intent.putExtra("data", "+" + df.format(num))
        startActivity(intent)
    }

    private var scanResult: String = ""
    private fun onLinkeResult(msg: String) {
        msg.logE()
        var address = ""
        var linkBean = Gson().fromJson(msg, ScanResultLinkeBean::class.java)
        if (linkBean.publicKeys == null) {
            return
        }
        if (linkBean.publicKeys.size > 0) {
            address = linkBean.publicKeys[0]
        } else {
            for (segment in linkBean.segments) {
                if (segment!!.typeKey == 95) {
                    address = segment!!.value.toString()
                }
            }
        }
        if (scanType == 1000) {
            val intent = Intent()
            intent.putExtra("result", address)
            setResult(resultCode, intent)
            finish()
            return
        } else if (scanType == 10001) {
            if (linkBean.flag == 5) {
                //收款
//                var intent = Intent(this,PayActivity::class.java)
//                startActivity(intent)


                var sybid: Long = 0

                var jsonObj = JSONObject(msg)
                var jsonAry = jsonObj.getJSONArray("segments")

                for (i in 0..jsonAry.length() - 1) {
                    var segment = jsonAry.getJSONObject(i)
                    if (segment.getInt("typeKey") == 43) {
                        maxAllowedAmount = segment.getLong("value").toFloat()
                    } else if (segment.getInt("typeKey") == 44) {
                        sybid = segment.getLong("value")
                        sybId = sybid
                        break
                    }
                }

                if (selfSybId == sybId) {
                    getString(R.string.payer_and_payee_is_the_same_one).toast()
                    finish()
                    return
                }

                mWebView.evaluateJavascript(WebViewApi.getFungibleSymbolDetail(sybid)) {}
                return

            } else if (linkBean.flag == 17) {
                var intent = Intent(mContext, ScanPayActivity::class.java)
                intent.putExtra("maxMoney", AssetsItemFragment.firstBean!!.sym.split(" ")[0])
                intent.putExtra("data", AssetsItemFragment.firstBean!!)
                intent.putExtra("address", address)
                startActivity(intent)


            } else if (linkBean.flag == 3) {
                //通证校验
                getString(R.string.operation_success).toast()
            }
            finish()
            return
        }

        //收款
        var num = intent.getStringExtra("num")

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
    }

    private fun onReceResult(msg: String) {
        "rece".logE()
        msg.logE()
    }

    override fun onStart() {
        super.onStart()
        zxingview.startCamera()
        zxingview.showScanRect()
        zxingview.startSpot()
    }

    override fun onStop() {
        super.onStop()
        zxingview.stopCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        zxingview.onDestroy()
    }

    private fun showFingerPrintDialog() {
        confirmPassword(sharedPref.isFinger, supportFragmentManager, object : FingerSuccessCallback() {
            override fun onCheckSuccess() {
                mWebView.evaluateJavascript(WebViewApi.needPrivateKeyResponse(sharedPref.privateKey)) {}
            }
        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        if (requestCode == ConstValue.REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            //裁剪成功后只返回裁剪后图片的绝对路径，不返回Uri，需自行转换
            val pathList: List<String> = Matisse.obtainPathResult(data)
            if (pathList.isEmpty()) {
                return
            }
            zxingview.decodeQRCode(pathList[0])
        }
    }

}