package com.qs.modulemain.ui.activity.index

import android.content.Intent
import com.google.gson.Gson
import com.qs.modulemain.R
import com.qs.modulemain.bean.IssueFtsBean
import com.qs.modulemain.util.confirmPassword
import com.smallcat.shenhai.mvpbase.base.FingerSuccessCallback
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.helper.MessageEvent
import com.smallcat.shenhai.mvpbase.model.helper.RxBus
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import com.smallcat.shenhai.mvpbase.utils.lastPushTransaction
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_fts_issue.*
import java.text.DecimalFormat

/** 发行Fts **/
class FtsIssueActivity : SimpleActivity() {

    override val layoutId: Int
        get() = R.layout.activity_fts_issue

    override fun initData() {

        addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    when (it.type) {
                        RxBusCenter.ISSUE_FTS -> {
                            getString(R.string.issue_success).toast()
                            finish()
                        }
                        RxBusCenter.NEED_PRIVATE_KEY -> showFingerPrintDialog()
                    }
                })

        tvTitle?.text = getString(R.string.issue)
        tv_address.text = sharedPref.publicKey
        iv_scan.setOnClickListener {
            val intent = Intent(this@FtsIssueActivity, ScanActivity::class.java)
            intent.putExtra("ScanType", 1000)
            startActivityForResult(intent, 1)
        }

        textView6.setOnClickListener {
            val intent = Intent(this@FtsIssueActivity, ChooseAddressActivity::class.java)
            startActivityForResult(intent, 2)
        }

        tv_issue.setOnClickListener {
            //发行量
            val issueCount: Int = et_number.text.toString().toInt()
            //发行地址
            val issueAddress: String = tv_address.text.toString()
            //备注
            val memo: String = et_pwd.text.toString()

            val ftsBean: IssueFtsBean = IssueFtsBean()
            val count = issueCount.toFloat()
            val jingdu = intent.extras?.getString("jingdu")!!.toInt()

            var JING = ""
            for (i in 0 until jingdu) {
                JING += "0"
            }
            val df = DecimalFormat("0.$JING")

            ftsBean.number = df.format(count).toString() + " S#" + intent.extras?.getString("data")
            ftsBean.address = issueAddress
            ftsBean.memo = memo

            val ftsBeasJson = Gson().toJson(ftsBean)

            lastPushTransaction = RxBusCenter.ISSUE_FTS
            mWebView.evaluateJavascript(WebViewApi.pushTransaction("issuefungible", ftsBeasJson), null)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) return

        if (resultCode == ScanActivity.resultCode) {
            val result = data.getStringExtra("result")
            tv_address.text = result.toString()
        }

        if (requestCode == 2) {
            if (data.hasExtra("data")) {
                tv_address.text = data.getStringExtra("data")
            }
        }
    }

    private fun showFingerPrintDialog() {
        confirmPassword(mContext.sharedPref.isFinger, supportFragmentManager, object : FingerSuccessCallback() {
            override fun onCheckSuccess() {
                mWebView.evaluateJavascript(WebViewApi.needPrivateKeyResponse(sharedPref.privateKey)) {}
            }
        })
    }

}
