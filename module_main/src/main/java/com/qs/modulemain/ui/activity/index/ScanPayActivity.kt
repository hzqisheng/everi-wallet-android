package com.qs.modulemain.ui.activity.index

import android.app.Dialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.google.gson.Gson
import com.qs.modulemain.R
import com.qs.modulemain.bean.ChooseGetBean
import com.qs.modulemain.bean.PayRequestBean
import com.qs.modulemain.bean.PayResultBean
import com.qs.modulemain.bean.RecordDetailBean
import com.qs.modulemain.presenter.ScanPayPresenter
import com.qs.modulemain.view.ScanPayView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.extension.*
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.helper.MessageEvent
import com.smallcat.shenhai.mvpbase.model.helper.RxBus
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import com.smallcat.shenhai.mvpbase.utils.Base64Utils
import com.smallcat.shenhai.mvpbase.utils.lastPushTransaction
import com.smallcat.shenhai.mvpbase.utils.qrcode_type
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_scan_pay.*
import java.text.DecimalFormat

/**
 * scan result
 * pay money
 */
class ScanPayActivity : BaseActivity<ScanPayPresenter>(), ScanPayView {
    private var maxMoney: Float = 0f
    private var mFtsBean: ChooseGetBean? = null
    private var bean: PayRequestBean? = null

    override fun initPresenter() {
        mPresenter = ScanPayPresenter(mContext)
    }

    override val layoutId: Int = R.layout.activity_scan_pay

    override fun initData() {
        maxMoney = intent.getFloatExtra("maxMoney", 0f)

        if (intent.hasExtra("data")) {
            mFtsBean = intent.getSerializableExtra("data") as ChooseGetBean

            tv_name.text = mFtsBean!!.sym_name + "(" + mFtsBean!!.asset.split("S")[1] + ")"

            if (mFtsBean!!.metas.size > 0) {
                if ("symbol-icon".equals(mFtsBean!!.metas[0].key)) {
                    var bitmap = Base64Utils.base64ToBitmap(mFtsBean!!.metas[0].value)
                    iv_img.setImageBitmap(bitmap)
                }
            }

            tv_money.text = mFtsBean!!.asset.split(" ")[0]
        }

        if (intent.hasExtra("address")) {
            tv_address.text = intent.getStringExtra("address")
        }

      /*  addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    when (it.type) {
                        RxBusCenter.QRCODE_PAL -> onQrCodeResult(it.msg)
                    }
                })*/

        layout_choose.setOnClickListener {
            var intent = Intent(this@ScanPayActivity, CollectChooseFtsActivity::class.java)
            startActivityForResult(intent, 101)
        }

        iv_scan.setOnClickListener {
            var intent = Intent(this@ScanPayActivity, ScanActivity::class.java)
            intent.putExtra("ScanType", 1000)
            startActivityForResult(intent, 1)
        }

        textView6.setOnClickListener {
            var intent = Intent(this@ScanPayActivity, ChooseAddressActivity::class.java)
            startActivityForResult(intent, 3)
        }

        tv_sure.setOnClickListener {
            if (TextUtils.isEmpty(et_pwd.text)) {
                getString(R.string.please_input_money).toast()
                return@setOnClickListener
            }

            if (tv_address.text.isEmpty()) {
                getString(R.string.please_choose_address).toast()
                return@setOnClickListener
            }


            var count = et_pwd.text.toString().toFloat()

            var JING = ""

            for (i in 0..mFtsBean!!.sym.split(",")[0].toInt() - 1) {
                JING += "0";
            }

            val df = DecimalFormat("0." + JING)
//            df.format(count)+" "+mFtsBean!!.sym.split(",")[1]

            bean = PayRequestBean()
            bean!!.from = sharedPref.publicKey
            bean!!.to = tv_address.text.toString()
            bean!!.number = df.format(count) + " " + mFtsBean!!.sym.split(",")[1]
            bean!!.memo = et_note.text.toString()

//            var json = Gson().toJson(bean)

//            lastPushTransaction = RxBusCenter.REQUEST_PAY
//            mWebView.evaluateJavascript(WebViewApi.pushTransaction("transferft",json)){}

            var intent = Intent(this@ScanPayActivity, PayDetailActivity::class.java)
            intent.putExtra("data", bean)
            intent.putExtra("fts", mFtsBean)
            startActivity(intent)
        }
    }

    /**  **/
    private fun onQrCodeResult(msg: String) {
        "3333333333333".logE()
        msg.logE()
        val payResult = Gson().fromJson<PayResultBean?>(msg, PayResultBean::class.java)

        if (payResult == null || payResult.segments == null) return

        for (segment in payResult.segments) {
            if (segment.typeKey == 95) {
                tv_address.text = segment.value.toString()
            }
        }
    }

    private fun onDataResult(msg: String) {
        msg.logE()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101) {
            if (resultCode == 101) {
                mFtsBean = data!!.getSerializableExtra("data") as ChooseGetBean
                tv_name.text = mFtsBean!!.sym_name

                if (mFtsBean!!.metas.size > 0) {
                    if ("symbol-icon".equals(mFtsBean!!.metas[0].key)) {
                        var bitmap = Base64Utils.base64ToBitmap(mFtsBean!!.metas[0].value)
                        iv_img.setImageBitmap(bitmap)
                    }
                }

                tv_money.text = mFtsBean!!.asset.split(" ")[0]
            }
        }

        if (requestCode == 1) {
            if (resultCode == ScanActivity.resultCode) {
                "2222222222222".logE()
                val result = data!!.getStringExtra("result")
                tv_address.text = result
/*
                qrcode_type = RxBusCenter.QRCODE_PAL
                mWebView.evaluateJavascript(WebViewApi.parseEvtLink(result)) {}*/
            }
        }

        if (requestCode == 3) {
            if (data != null && data.hasExtra("data")) {
                tv_address.text = data.getStringExtra("data")
            }
        }
    }
}
