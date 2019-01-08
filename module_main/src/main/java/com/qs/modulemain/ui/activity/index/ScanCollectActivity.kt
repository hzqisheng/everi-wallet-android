package com.qs.modulemain.ui.activity.index

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.google.gson.Gson
import com.qs.modulemain.R
import com.qs.modulemain.bean.ChooseGetBean
import com.qs.modulemain.bean.ReceScanBean
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.getResourceString
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
    /** 密码框 **/
    private var pwdDialog: Dialog? = null
    private lateinit  var mUseFts:ChooseGetBean
    private  var sybid = 0;
    private var isCollect = false;
    private var scanResult :String = ""

    override val layoutId: Int
        get() = R.layout.activity_scan_collect

    override fun initData() {

        addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    when(it.type){
                        RxBusCenter.SCAN_RECE -> RecvieSuccess(it.msg)
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

        layout.setOnClickListener {
//            var intent = Intent(this,)
            var intent = Intent(this,CollectChooseFtsActivity::class.java)
            startActivityForResult(intent,101)
        }

        if(intent.hasExtra("data")){
            mUseFts = intent.getSerializableExtra("data") as ChooseGetBean

            textView6.text =mUseFts.name

            if(mUseFts!!.metas.size >0){
                if("symbol-icon".equals(mUseFts!!.metas[0].key)){
                    var bitmap = Base64Utils.base64ToBitmap(mUseFts!!.metas[0].value)
                    iv_img.setImageBitmap(bitmap)
                }
            }
         }

        if(intent.hasExtra("scanResult")){
            isCollect = true;
            scanResult = intent.getStringExtra("scanResult")
        }



        tv_sure.setOnClickListener {

            if(mUseFts == null){
                getString(R.string.please_choose_currency_and_money).toast()
                return@setOnClickListener
            }

            if(isCollect){

                //收款
                var num = et_pwd.text.toString()

                var JING = ""
                for (i in 0..mUseFts!!.sym.split(",")[0].toInt()-1){
                    JING +="0";
                }
                val df = DecimalFormat("0."+JING)
                //linkBean!!.publicKeys[0]
                var json = Gson().toJson(ReceScanBean.createBean(scanResult,sharedPref.publicKey,df.format(num.toFloat()),mUseFts.sym.split("#")[1]))

                json.logE()

                lastPushTransaction = RxBusCenter.SCAN_RECE
                mWebView.evaluateJavascript(WebViewApi.pushTransaction("everipay",json)){}
                return@setOnClickListener
            }

            var intent = Intent(this,ScanActivity::class.java)
            intent.putExtra("data",mUseFts)
            intent.putExtra("num",et_pwd.text.toString())
            intent.putExtra("ScanType",ScanActivity.RECE)
            startActivity(intent)
        }
    }

    private fun RecvieSuccess(msg: String) {
        var num:String = et_pwd.text.toString()

        var JING = ""
        for (i in 0..mUseFts!!.sym.split(",")[0].toInt()-1){
            JING +="0";
        }
        val df = DecimalFormat("0."+JING)

        var intent = Intent(this,PaySuccessActivity::class.java)
        intent.putExtra("data","+"+df.format(num.toFloat()))
        startActivity(intent)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 101){
            if(resultCode == 101){
                mUseFts = data?.getSerializableExtra("data") as ChooseGetBean

                textView6.text =mUseFts.name

                for (meta in mUseFts.metas) {
                    if("symbol-icon".equals(meta.key)){
                        if(meta.value.isEmpty())return
                        var decodedByte: Bitmap? = Base64Utils.base64ToBitmap(meta.value)
                        if(decodedByte == null)return
                        iv_img.setImageBitmap(decodedByte)
                    }
                }
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
