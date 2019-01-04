package com.qs.modulemain.ui.activity.index

import android.app.Dialog
import android.content.Intent
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.google.gson.Gson
import com.qs.modulemain.R
import com.qs.modulemain.bean.DomainBean
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.getResourceString
import com.smallcat.shenhai.mvpbase.extension.logE
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.helper.MessageEvent
import com.smallcat.shenhai.mvpbase.model.helper.RxBus
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter.CREATE_DOMAIN
import com.smallcat.shenhai.mvpbase.utils.lastPushTransaction
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_add_fts.*
import kotlinx.android.synthetic.main.activity_nfts_create.*
import java.lang.Exception

/** 创建通证 **/
class NFTsCreateActivity : SimpleActivity() {
    /** 密码框 **/
    private var pwdDialog: Dialog? = null
    override val layoutId: Int
        get() = R.layout.activity_nfts_create

    override fun initData() {

        addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    when(it.type){
                        RxBusCenter.CREATE_DOMAIN -> {
                            it.msg.logE()
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

        tvTitle?.text = getString(R.string.create_nfts)
        ivRight?.apply {
            setBackgroundResource(R.drawable.ic_question)
            setOnClickListener{}
        }

        //发行
        cb_issue.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                if(cb_edit.isChecked){
                    cb_edit.isChecked = false
                }
            }
        }
        cb_edit.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                if(cb_issue.isChecked){
                    cb_issue.isChecked = false
                }
            }
        }

        //转移
        cb_change.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                if(cb_edit_change.isChecked){
                    cb_edit_change.isChecked = false
                }
            }
        }
        cb_edit_change.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                if(cb_change.isChecked){
                    cb_change.isChecked = false
                }
            }
        }
        //管理
        cb_manage.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                if(cb_edit_manage.isChecked){
                    cb_edit_manage.isChecked = false
                }
            }
        }
        cb_edit_manage.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                if(cb_manage.isChecked){
                    cb_manage.isChecked = false
                }
            }
        }

        tv_issue_edit.setOnClickListener {
            var intent = Intent(this@NFTsCreateActivity,NFTsEditActivity::class.java)
            startActivityForResult(intent,101)
        }

        tv_change_edit.setOnClickListener {
            var intent = Intent(this@NFTsCreateActivity,NFTsEditActivity::class.java)
            startActivityForResult(intent,102)
        }

        tv_manage_edit.setOnClickListener {
            var intent = Intent(this@NFTsCreateActivity,NFTsEditActivity::class.java)
            startActivityForResult(intent,103)
        }


        //start(NFTsEditActivity::class.java)
        tvSure.setOnClickListener {
            handleCompeleted()
        }


    }

    private fun handleCompeleted() {

        var domainBean: DomainBean = DomainBean.build();
        domainBean.name = et_nfts.text.toString()
        domainBean.creator = sharedPref.publicKey

        if (cb_issue.isChecked) {
            var issueBean = DomainBean.IssueBean.AuthorizersBean()
            issueBean.ref = "[A] " + sharedPref.publicKey
            issueBean.weight = 1
            domainBean.addIssueAuthorizersBean(issueBean)
        }else{
            domainBean.addIssueAuthorizersBean(mIssueBean)
        }

        if(cb_change.isChecked) {
            var transBean = DomainBean.TransferBean.AuthorizersBeanX()
            transBean.ref = "[A] " + sharedPref.publicKey
            transBean.weight = 1
            domainBean.addTransferAuthorizersBean(transBean)
        }else{
            domainBean.addTransferAuthorizersBean(mTransBean)
        }

        if(cb_manage.isChecked) {
            var manageBean = DomainBean.ManageBean.AuthorizersBeanXX()
            manageBean.ref = "[A] " + sharedPref.publicKey
            manageBean.weight = 1
            domainBean.addManageAuthorizersBean(manageBean)
        }else{
            domainBean.addManageAuthorizersBean(mManageBean)
        }

        var domainJson = Gson().toJson(domainBean)
        domainJson.logE()
        lastPushTransaction = CREATE_DOMAIN
        mWebView.evaluateJavascript(WebViewApi.pushTransaction("newdomain",domainJson)){}
    }

    var mIssueBean:DomainBean.IssueBean.AuthorizersBean? = null
    var mTransBean:DomainBean.TransferBean.AuthorizersBeanX? = null
    var mManageBean:DomainBean.ManageBean.AuthorizersBeanXX? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 101){
            var resultJson = data!!.getStringExtra("result")
            if(resultJson.toString().isEmpty()){
                "invalid code !".toast()
                return
            }
            try {
                mIssueBean = Gson().fromJson(resultJson,DomainBean.IssueBean.AuthorizersBean::class.java)
            }catch (e:Exception){
                "invalid code !".toast()
            }

        }

        if(requestCode == 102){
            var resultJson = data!!.getStringExtra("result")
            if(resultJson.toString().isEmpty()){
                "invalid code !".toast()
                return
            }
            try {
                mTransBean = Gson().fromJson(resultJson,DomainBean.TransferBean.AuthorizersBeanX::class.java)
            }catch (e:Exception){
                "invalid code !".toast()
            }

        }

        if(requestCode == 103){
            var resultJson = data!!.getStringExtra("result")
            if(resultJson.toString().isEmpty()){
                "invalid code !".toast()
                return
            }
            try {
                mManageBean = Gson().fromJson(resultJson, DomainBean.ManageBean.AuthorizersBeanXX::class.java)
            }catch (e:Exception){
                "invalid code !".toast()
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
