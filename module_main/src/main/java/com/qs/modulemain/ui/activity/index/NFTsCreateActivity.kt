package com.qs.modulemain.ui.activity.index

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.google.gson.Gson
import com.qs.modulemain.R
import com.qs.modulemain.bean.DomainBean
import com.qs.modulemain.ui.adapter.HelpAdapter
import com.qs.modulemain.ui.fragment.HelpDialogFragment
import com.qs.modulemain.util.DataUtils
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
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter.CREATE_DOMAIN
import com.smallcat.shenhai.mvpbase.utils.lastPushTransaction
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_nfts_create.*
import java.lang.Exception

/** 创建通证 **/
class NFTsCreateActivity : SimpleActivity() {

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
                        RxBusCenter.NEED_PRIVATE_KEY -> showFingerPrintDialog()
                    }
                })

        tvTitle?.text = getString(R.string.create_nfts)
        ivRight?.apply {
            setBackgroundResource(R.drawable.ic_question)
            setOnClickListener { showHelpDialog() }
        }

       /* //发行
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
            val intent = Intent(this@NFTsCreateActivity,NFTsEditActivity::class.java)
            startActivityForResult(intent,101)
        }

        tv_change_edit.setOnClickListener {
            val intent = Intent(this@NFTsCreateActivity,NFTsEditActivity::class.java)
            startActivityForResult(intent,102)
        }

        tv_manage_edit.setOnClickListener {
            val intent = Intent(this@NFTsCreateActivity,NFTsEditActivity::class.java)
            startActivityForResult(intent,103)
        }*/


        //start(NFTsEditActivity::class.java)
        tvSure.setOnClickListener {
            handleCompleted()
        }


    }

    private fun handleCompleted() {

        if(et_nfts.text.isEmpty()){
            getString(R.string.nft_can_not_be_empty).toast()
            return
        }

        val domainBean: DomainBean = DomainBean.build();
        domainBean.name = et_nfts.text.toString()
        domainBean.creator = sharedPref.publicKey

        if (cb_issue.isChecked) {
            val issueBean = DomainBean.IssueBean.AuthorizersBean()
            issueBean.ref = "[A] " + sharedPref.publicKey
            issueBean.weight = 1
            domainBean.addIssueAuthorizersBean(issueBean)
        }else{
            domainBean.addIssueAuthorizersBean(mIssueBean)
        }

        if(cb_change.isChecked) {
            val transBean = DomainBean.TransferBean.AuthorizersBeanX()
            transBean.ref = "[A] " + sharedPref.publicKey
            transBean.weight = 1
            domainBean.addTransferAuthorizersBean(transBean)
        }else{
            domainBean.addTransferAuthorizersBean(mTransBean)
        }

        if(cb_manage.isChecked) {
            val manageBean = DomainBean.ManageBean.AuthorizersBeanXX()
            manageBean.ref = "[A] " + sharedPref.publicKey
            manageBean.weight = 1
            domainBean.addManageAuthorizersBean(manageBean)
        }else{
            domainBean.addManageAuthorizersBean(mManageBean)
        }

        val domainJson = Gson().toJson(domainBean)
        domainJson.logE()
        lastPushTransaction = CREATE_DOMAIN
        mWebView.evaluateJavascript(WebViewApi.pushTransaction("newdomain",domainJson)){}
    }

    var mIssueBean:DomainBean.IssueBean.AuthorizersBean? = null
    var mTransBean:DomainBean.TransferBean.AuthorizersBeanX? = null
    var mManageBean:DomainBean.ManageBean.AuthorizersBeanXX? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) return

        if(requestCode == 101){
            val resultJson = data.getStringExtra("result")
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
            val resultJson = data.getStringExtra("result")
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
            val resultJson = data.getStringExtra("result")
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

    private fun showHelpDialog(){
        val contentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_help, null, false)
        val recyclerView = contentView.findViewById<RecyclerView>(R.id.rv_list)
        val adapter = HelpAdapter(DataUtils.addNFtsHelpData(mContext))
        recyclerView.adapter = adapter
        val popupWindow = PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true)
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popupWindow.isOutsideTouchable = true
        popupWindow.isTouchable = true
        popupWindow.animationStyle = R.style.ActionSheetDialogTop
        popupWindow.showAsDropDown(toolbar)
        /*val fragment = HelpDialogFragment()
        fragment.setDate(DataUtils.addNFtsHelpData(mContext), toolbar.height)
        fragment.show(supportFragmentManager, "helpDialog")*/
    }

    private fun showFingerPrintDialog() {
        confirmPassword(mContext.sharedPref.isFinger, supportFragmentManager, object : FingerSuccessCallback() {
            override fun onCheckSuccess() {
                mWebView.evaluateJavascript(WebViewApi.needPrivateKeyResponse(sharedPref.privateKey)) {}
            }
        })
    }

}
