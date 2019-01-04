package com.qs.modulemain.ui.activity.index

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.qs.modulemain.R
import com.qs.modulemain.bean.ChooseGetBean
import com.qs.modulemain.bean.PayRequestBean
import com.qs.modulemain.bean.ServerCharge
import com.qs.modulemain.presenter.PayDetailPresenter
import com.qs.modulemain.ui.activity.MainActivity
import com.qs.modulemain.view.PayDetailView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.extension.*
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.helper.MessageEvent
import com.smallcat.shenhai.mvpbase.model.helper.RxBus
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import com.smallcat.shenhai.mvpbase.utils.Base64Utils
import com.smallcat.shenhai.mvpbase.utils.fitSystemAllScroll
import com.smallcat.shenhai.mvpbase.utils.lastPushTransaction
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_pay_detail.*
import org.json.JSONObject

class PayDetailActivity : BaseActivity<PayDetailPresenter>(), PayDetailView {
    private var bean: PayRequestBean?= null
    private var pwdDialog:Dialog? = null
    private var data: ChooseGetBean? = null
    private var jingdu:String ?= null

    override fun initPresenter() {
        mPresenter = PayDetailPresenter(mContext)
    }

    override val layoutId: Int = R.layout.activity_pay_detail

    override fun fitSystem() {
        fitSystemAllScroll(this)
    }

    override fun initData() {
        iv_back.setOnClickListener { start(MainActivity::class.java) }


        addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    when(it.type){
                        RxBusCenter.SERVICE_CHARGE ->onDataResult(it.msg)
                    }
                })

        addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    when(it.type){
                        RxBusCenter.REQUEST_PAY ->onDataResult1(it.msg)
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

        //SERVICE_CHARGE
        bean = intent.getSerializableExtra("data") as PayRequestBean
        data = intent.getSerializableExtra("fts") as ChooseGetBean

        tv_currency.text = data!!.sym_name+"("+ data!!.asset.split("S")[1] +")"
        tv_money.text = data!!.asset.split(" ")[0] +" "+data!!.sym_name

        for (meta in data!!.metas) {
            if("symbol-icon".equals(meta.key)){
                if(meta.value.isEmpty())return
                var decodedByte: Bitmap? = Base64Utils.base64ToBitmap(meta.value)
                if(decodedByte == null)return
                iv_img.setImageBitmap(decodedByte)
            }
        }

        tv_pay_address.text = bean!!.from
        tv_collect_address.text = bean!!.to
        tv_note.text = bean!!.memo

        var serverJson = Gson().toJson(bean)

        var charge = ServerCharge()
        charge.availablePublicKeys = arrayListOf(sharedPref.publicKey)

        var jsss = Gson().toJson(charge)

        mWebView.evaluateJavascript(WebViewApi.getEstimatedChargeForTransaction("transferft",serverJson,jsss)){}

        tvSure.setOnClickListener {


         var json = Gson().toJson(bean)

         lastPushTransaction = RxBusCenter.REQUEST_PAY
         mWebView.evaluateJavascript(WebViewApi.pushTransaction("transferft",json,jsss)){} }

    }

    private fun onDataResult1(msg: String) {
        "onDataResult1".logE()
        msg.logE()
        var intent = Intent(this,PaySuccessActivity::class.java)
        intent.putExtra("data","-"+bean!!.number.split(" ")[0])
        startActivity(intent)
    }

    private fun onDataResult(msg: String) {
        msg.logE()
        var jsonObj = JSONObject(msg)
        tv_fee.text = (jsonObj.get("charge").toString().toFloat() * 0.00001f).toString()
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
