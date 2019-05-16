package com.qs.modulemain.ui.activity.index

import android.content.Intent
import android.graphics.Bitmap
import android.text.TextUtils
import com.google.gson.Gson
import com.qs.modulemain.R
import com.qs.modulemain.bean.ChooseGetBean
import com.qs.modulemain.bean.PayRequestBean
import com.qs.modulemain.bean.PayResultBean
import com.qs.modulemain.presenter.ScanPayPresenter
import com.qs.modulemain.view.ScanPayView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.extension.logE
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.utils.Base64Utils
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
            var isHaveIcon = false
            for (meta in mFtsBean!!.metas) {
                if (meta.value.isNotEmpty() && meta.value.contains(",")) {
                    val decodedByte: Bitmap? = Base64Utils.base64ToBitmap(meta.value) ?: continue
                    iv_img.setImageBitmap(decodedByte)
                    isHaveIcon = true
                }
            }
            if (!isHaveIcon && (mFtsBean?.sym_name == "EVT" || mFtsBean?.sym_name == "PEVT")) {
                iv_img.setImageResource(R.drawable.icon_fukuan_evt)
            } else if (!isHaveIcon) {
                iv_img.setImageResource(0)
            }

            tv_money.text = mFtsBean!!.asset.split(" ")[0]
        }

        if (intent.hasExtra("address")) {
            //tv_address.text = intent.getStringExtra("address")
            tv_address.setText(intent.getStringExtra("address"))
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
            val intent = Intent(this@ScanPayActivity, CollectChooseFtsActivity::class.java)
            intent.putExtra("data", mFtsBean)
            startActivityForResult(intent, 101)
        }

        iv_scan.setOnClickListener {
            val intent = Intent(this@ScanPayActivity, ScanActivity::class.java)
            intent.putExtra("ScanType", 1000)
            startActivityForResult(intent, 1)
        }

        textView6.setOnClickListener {
            val intent = Intent(this@ScanPayActivity, ChooseAddressActivity::class.java)
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
                //tv_address.text = segment.value.toString()
                tv_address.setText(segment.value.toString())
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
                //iv_img.setImageResource(R.drawable.icon_fukuan_evt)
                var isHaveIcon = false
                for (meta in mFtsBean!!.metas) {
                    if (meta.value.isNotEmpty() && meta.value.contains(",")) {
                        val decodedByte: Bitmap? = Base64Utils.base64ToBitmap(meta.value)
                                ?: continue
                        iv_img.setImageBitmap(decodedByte)
                        isHaveIcon = true
                    }
                }
                if (!isHaveIcon && (mFtsBean?.sym_name == "EVT" || mFtsBean?.sym_name == "PEVT")) {
                    iv_img.setImageResource(R.drawable.icon_fukuan_evt)
                } else if (!isHaveIcon) {
                    iv_img.setImageResource(0)
                }

                tv_money.text = mFtsBean!!.asset.split(" ")[0]
            }
        }

        if (requestCode == 1) {
            if (resultCode == ScanActivity.resultCode) {
                "2222222222222".logE()
                val result = data!!.getStringExtra("result")
                //tv_address.text = result
                tv_address.setText(result)
/*
                qrcode_type = RxBusCenter.QRCODE_PAL
                mWebView.evaluateJavascript(WebViewApi.parseEvtLink(result)) {}*/
            }
        }

        if (requestCode == 3) {
            if (data != null && data.hasExtra("data")) {
                //tv_address.text = data.getStringExtra("data")
                tv_address.setText(data.getStringExtra("data"))
            }
        }
    }
}
