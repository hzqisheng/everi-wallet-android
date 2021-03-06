package com.qs.modulemain.ui.activity.index

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import com.google.gson.Gson
import com.qs.modulemain.R
import com.qs.modulemain.bean.ChooseGetBean
import com.qs.modulemain.bean.PayBean
import com.qs.modulemain.bean.ScanResultLinkeBean
import com.qs.modulemain.bean.TransactionResult
import com.qs.modulemain.presenter.PayPresenter
import com.qs.modulemain.ui.fragment.AssetsItemFragment
import com.qs.modulemain.view.PayView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.extension.logE
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.bean.CollectBean
import com.smallcat.shenhai.mvpbase.model.bean.LinkBean
import com.smallcat.shenhai.mvpbase.model.helper.MessageEvent
import com.smallcat.shenhai.mvpbase.model.helper.RxBus
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import com.smallcat.shenhai.mvpbase.utils.Base64Utils
import com.smallcat.shenhai.mvpbase.utils.bitmapToBase64
import com.smallcat.shenhai.mvpbase.utils.qrcode_type
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_pay.*
import org.json.JSONObject

/** 付款 **/
class PayActivity : BaseActivity<PayPresenter>(), PayView {

    private var mLinkId: String = ""
    private var pwdDialog: Dialog? = null
    private var maxMoney: Float = 0f
    private var bean: ChooseGetBean? = null
    private var isChooseSymbolResult = false

    //自己的付款id
    private var selfSybId = -1L

    override fun initPresenter() {
        mPresenter = PayPresenter(mContext)
    }

    override val layoutId: Int
        get() = R.layout.activity_pay

    @SuppressLint("SetTextI18n")
    override fun initData() {
        tvTitle?.text = getString(R.string.everi_pay)

        mWebView.evaluateJavascript(WebViewApi.stopEVTLinkQrImageReload()) {}

        addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    when (it.type) {
                        RxBusCenter.GET_LINkID -> onLinkResult(it.msg)
                        RxBusCenter.QRCODE_PAL -> onQrData(it.msg)
                        RxBusCenter.PAY_RECORD -> onPayRecord(it.msg)
                    }
                })

        mWebView.evaluateJavascript(WebViewApi.getUniqueLinkId()) {}

        if (intent.hasExtra("data")) {
            bean = intent.getSerializableExtra("data") as ChooseGetBean
        } else {
            bean = AssetsItemFragment.firstBean!!
        }

        tv_address.text = sharedPref.publicKey

        tv_money.setOnClickListener {
            showSetUpDialog()
        }

        rb_sweep_payment.setOnClickListener {
            val intent = Intent(this@PayActivity, ScanActivity::class.java)
            intent.putExtra("ScanType", 10001)
            intent.putExtra("selfSybId", selfSybId)
            startActivity(intent)
        }

        layout_choose.setOnClickListener {
            val intent = Intent(this, CollectChooseFtsActivity::class.java)
            intent.putExtra("data", bean)
            startActivityForResult(intent, 101)
        }

        if (bean != null) {
            tv_name.text = bean!!.sym_name + "(" + bean!!.asset.split("S")[1] + ")"
            selfSybId = bean!!.asset.split("S")[1].substring(1).toLong()

            var isHaveIcon = false
            for (meta in bean!!.metas) {
                if (meta.value.isNotEmpty() && meta.value.contains(",")) {
                    val decodedByte: Bitmap? = Base64Utils.base64ToBitmap(meta.value) ?: continue
                    iv_img.setImageBitmap(decodedByte)
                    isHaveIcon = true
                }
            }
            if (!isHaveIcon && (bean?.sym_name == "EVT" || bean?.sym_name == "PEVT")) {
                iv_img.setImageResource(R.drawable.icon_fukuan_evt)
            } else if (!isHaveIcon) {
                iv_img.setImageResource(0)
            }
        }

    }

    private fun onQrData(msg: String) {
        val collectBean = Gson().fromJson<CollectBean>(msg, CollectBean::class.java)
        iv_qr_code.setImageBitmap(Base64Utils.base64ToBitmap(collectBean.dataUrl))
    }

    private fun onPayRecord(msg: String) {
        val transactionRecord = Gson().fromJson(msg, TransactionResult::class.java) ?: return
        if (transactionRecord.transactionId != null) {
            Intent(this, TransactionRecordActivity::class.java).apply {
                putExtra("id", transactionRecord.transactionId)
                startActivity(this)
            }
            finish()
        } else {
            val str = Gson().toJson(LinkBean(mLinkId), LinkBean::class.java)
            mWebView.evaluateJavascript(WebViewApi.getStatusOfEvtLink(str), null)
        }
    }

    private fun onLinkResult(msg: String) {
        msg.logE()
        mLinkId = msg

        val payBean = PayBean()
        payBean.keyProvider = listOf(sharedPref.privateKey)
        payBean.linkId = mLinkId
        payBean.symbol = bean!!.sym.split("#")[1].toInt()
        val decimal = bean!!.sym.split(",")[0].toDouble()
        val number = Math.pow(10.0, decimal).toFloat()
        payBean.maxAmount = tv_money.text.toString().toFloat() * number
        payBean.maxAmount.toString().logE()
        qrcode_type = RxBusCenter.QRCODE_PAL
        mWebView.evaluateJavascript(WebViewApi.getEVTLinkQrImage("everiPay", Gson().toJson(payBean), "{\"autoReload\": true}")) {}

        val str = Gson().toJson(LinkBean(mLinkId), LinkBean::class.java)
        mWebView.evaluateJavascript(WebViewApi.getStatusOfEvtLink(str), null)
    }

    override fun onResume() {
        super.onResume()
        rb_pay_code.isChecked = true
        if (mLinkId == "") return
        if (isChooseSymbolResult) {
            isChooseSymbolResult = false
            return
        }
        val payBean = PayBean()
        payBean.keyProvider = listOf(sharedPref.privateKey)
        payBean.linkId = mLinkId
        val decimal = bean!!.sym.split(",")[0].toDouble()
        val number = Math.pow(10.0, decimal).toFloat()
        payBean.maxAmount = tv_money.text.toString().toFloat() * number
        payBean.symbol = bean!!.sym.split("#")[1].toInt()
        qrcode_type = RxBusCenter.QRCODE_PAL
        mWebView.evaluateJavascript(WebViewApi.getEVTLinkQrImage("everiPay", Gson().toJson(payBean), "{\"autoReload\": true}")) {}

    }

    class Address {
        var address: String = ""
    }

    private fun showSetUpDialog() {
        if (pwdDialog == null) {
            pwdDialog = Dialog(mContext, R.style.CustomDialog)
        }
        val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_max_service_fee, null)
        view.findViewById<TextView>(R.id.tv_title).text = getString(R.string.set_max_money)
        val etNumber = view.findViewById<EditText>(R.id.et_number)
        etNumber.hint = getString(R.string.please_input)
        val tvSure = view.findViewById<TextView>(R.id.tv_sure)
        val tvCancel = view.findViewById<TextView>(R.id.tv_cancel)
        tvSure.setOnClickListener {
            maxMoney = etNumber.text.toString().toFloat()
            tv_money.text = maxMoney.toString()
            mWebView.evaluateJavascript(WebViewApi.stopEVTLinkQrImageReload(), null)
            mWebView.evaluateJavascript(WebViewApi.getUniqueLinkId(), null)
            pwdDialog!!.dismiss()
        }
        tvCancel.setOnClickListener { pwdDialog!!.dismiss() }
        pwdDialog!!.setContentView(view)
        pwdDialog!!.setCanceledOnTouchOutside(false)
        pwdDialog!!.setCancelable(true)
        pwdDialog!!.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101) {
            if (resultCode == 101) {
                if (data == null) return

                bean = data.getSerializableExtra("data") as ChooseGetBean

//                iv_img.setImageResource(R.drawable.icon_fukuan_evt)
                var isHaveIcon = false
                if (bean != null) {
                    tv_name.text = bean!!.sym_name + "(" + bean!!.asset.split("S")[1] + ")"
                    selfSybId = bean!!.asset.split("S")[1].substring(1).toLong()
                    for (meta in bean!!.metas) {
                        if (meta.value.isNotEmpty() && meta.value.contains(",")) {
                            val decodedByte: Bitmap? = Base64Utils.base64ToBitmap(meta.value)
                                    ?: continue
                            iv_img.setImageBitmap(decodedByte)
                            isHaveIcon = true
                        }
                    }
                }
                if (!isHaveIcon && (bean?.sym_name == "EVT" || bean?.sym_name == "PEVT")) {
                    iv_img.setImageResource(R.drawable.icon_fukuan_evt)
                } else if (!isHaveIcon) {
                    iv_img.setImageResource(0)
                }

                isChooseSymbolResult = true
                mWebView.evaluateJavascript(WebViewApi.getUniqueLinkId()) {}
            }
        }
    }

    override fun onPause() {
        super.onPause()
        mWebView.evaluateJavascript(WebViewApi.stopEVTLinkQrImageReload()) {}
        qrcode_type = -1
    }
}
