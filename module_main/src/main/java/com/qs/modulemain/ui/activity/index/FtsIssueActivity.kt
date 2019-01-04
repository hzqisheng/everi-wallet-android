package com.qs.modulemain.ui.activity.index

import android.app.Dialog
import android.content.Intent
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.google.gson.Gson
import com.qs.modulemain.R
import com.qs.modulemain.bean.IssueFtsBean
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.getResourceString
import com.smallcat.shenhai.mvpbase.extension.logE
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.helper.MessageEvent
import com.smallcat.shenhai.mvpbase.model.helper.RxBus
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import com.smallcat.shenhai.mvpbase.utils.lastPushTransaction
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_fts_issue.*
import java.text.DecimalFormat

/** 发行Fts **/
class FtsIssueActivity : SimpleActivity() {
    /** 密码框 **/
    private var pwdDialog: Dialog? = null

    override val layoutId: Int
        get() = R.layout.activity_fts_issue

    override fun initData() {

        addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    when(it.type){
                        RxBusCenter.ISSUE_FTS -> {
                            "发行成功".toast()
                            finish()
                        }
                    }
                })

        addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    when(it.type){
                        RxBusCenter.NEED_PRIVATE_KEY -> showSetUpDialog()
                    }
                })

        tvTitle?.text = getString(R.string.issue)
        tv_address.setText(sharedPref.publicKey)
        iv_scan.setOnClickListener {
            var intent = Intent(this@FtsIssueActivity,ScanActivity::class.java)
            startActivityForResult(intent,1)

        }

        textView6.setOnClickListener {
            var intent = Intent(this@FtsIssueActivity,ChooseAddressActivity::class.java)
            startActivityForResult(intent,2)
        }


        tv_issue.setOnClickListener {
           //发行量
            var issueCount:Int = et_number.text.toString().toInt();
            //发行地址
            var issueAddress:String = tv_address.text.toString();
            //备注
            var memo:String =et_pwd.text.toString()

            var ftsBean:IssueFtsBean = IssueFtsBean()
            var count = issueCount.toFloat();
            var jingdu = intent.extras.getString("jingdu").toInt()

            var JING = ""
            for (i in 0..jingdu-1){
                JING +="0";
            }
            val df = DecimalFormat("0."+JING)


            ftsBean.number =df.format(count).toString()+" S#"+getIntent().extras.getString("data")
            ftsBean.address = issueAddress
            ftsBean.memo = memo

            var ftsBeasJson = Gson().toJson(ftsBean)

            lastPushTransaction = RxBusCenter.ISSUE_FTS
            mWebView.evaluateJavascript(WebViewApi.pushTransaction("issuefungible",ftsBeasJson)){}
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == ScanActivity.resultCode){
            var result = data!!.getStringExtra("result")
            tv_address.setText(result.toString())
        }

        if(requestCode == 2){
            if(data != null && data.hasExtra("data")){
                tv_address.text = data.getStringExtra("data")
            }
        }
    }

    private fun showSetUpDialog(){
        if (pwdDialog == null) {
            pwdDialog = Dialog(mContext, R.style.CustomDialog)
        }
        val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_set_up_sign, null)
        val etNumber = view.findViewById<EditText>(R.id.et_number)
        val tvSure = view.findViewById<TextView>(R.id.tv_sure)
        val cbCheck = view.findViewById<CheckBox>(R.id.cb_check)
        val tvCancel = view.findViewById<TextView>(R.id.tv_cancel)
        tvSure.setOnClickListener {
            if(sharedPref.password.equals(etNumber.text.toString())){
                mWebView.evaluateJavascript(WebViewApi.needPrivateKeyResponse(sharedPref.privateKey)){}
            }else{
                getResourceString(R.string.password_error).toast()
            }
            pwdDialog!!.dismiss()
        }
        tvCancel.setOnClickListener{ pwdDialog!!.dismiss() }
        pwdDialog!!.setContentView(view)
        pwdDialog!!.setCanceledOnTouchOutside(false)
        pwdDialog!!.setCancelable(true)
        pwdDialog!!.show()
    }





}
