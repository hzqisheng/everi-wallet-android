package com.qs.modulemain.ui.activity.index

import android.annotation.SuppressLint
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import com.google.gson.Gson
import com.qs.modulemain.R
import com.qs.modulemain.bean.DomainIssueBean
import com.qs.modulemain.util.confirmPassword
import com.smallcat.shenhai.mvpbase.base.FingerSuccessCallback
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
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_nfts_issue.*
import kotlinx.android.synthetic.main.item_add_adress.view.*
import java.util.*

//发行域
class NFTsIssueActivity : SimpleActivity() {
    private var mDomain = ""
    private var mCount:Int = 0
    private var mDoaminNameList:ArrayList<String> = java.util.ArrayList()
    private var mAddressList:LinkedList<String> = java.util.LinkedList()
    private var mViewList:LinkedList<View> = java.util.LinkedList()

    override val layoutId: Int
        get() = R.layout.activity_nfts_issue

    @SuppressLint("SetTextI18n")
    override fun initData() {
        tvTitle?.text = getString(R.string.create_nfts)
        ivRight?.apply {
            setBackgroundResource(R.drawable.ic_question)
            setOnClickListener {}
        }

        mDomain = intent.getStringExtra("domain")

        addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    when (it.type) {
                        RxBusCenter.ISSUE_DOMAIN -> onDateResult(it.msg)
                        RxBusCenter.NEED_PRIVATE_KEY -> showFingerPrintDialog()
                    }
                })

        tv_number.text = mCount.toString()

        iv_add.setOnClickListener {
            mCount++
            tv_number.text = mCount.toString()
        }

        iv_reduce.setOnClickListener {
            mCount--
            tv_number.text = mCount.toString()
        }

        tvBatchGeneration.setOnClickListener {
            mDoaminNameList.clear()
            for (item: Int in 0 until mCount) {
                mDoaminNameList.add(et_batch_name.text.toString() + item)
            }

            tvCount.text = getString(R.string.domain_number) + ":" + mCount

            var showName = ""
            for (s in mDoaminNameList) {
                showName += s + "\n"
            }
            et_name.setText(showName)
        }

        et_name.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var lst = s!!.split("\n")
                if(lst != null)countChanged(lst.size - 1)
            }

        })

        mAddressList.add(sharedPref.publicKey)
        genAddressView(sharedPref.publicKey)

        //添加地址
        iv_add_address.setOnClickListener {
            addAddress(et_address.text.toString())
        }
        //扫一扫
        iv_scan.setOnClickListener {
            val intent = Intent(this@NFTsIssueActivity, ScanActivity::class.java)
            intent.putExtra("ScanType", 1000)
            startActivityForResult(intent, 1)
        }

        //发行
        tv_save.setOnClickListener {

            if (mDoaminNameList.size == 0) {
                "Name is 0".toast()
                return@setOnClickListener
            }
            if (mAddressList.size == 0) {
                "Address is 0".toast()
                return@setOnClickListener
            }

            val domainIssue = DomainIssueBean()
            domainIssue.domain = mDomain
            domainIssue.names = mDoaminNameList
            domainIssue.owner = mAddressList
            val pushJson = Gson().toJson(domainIssue)
            pushJson.logE()

            lastPushTransaction = RxBusCenter.ISSUE_DOMAIN
            mWebView.evaluateJavascript(WebViewApi.pushTransaction("issuetoken", pushJson), null)
        }
    }

    @SuppressLint("SetTextI18n")
    fun countChanged(count : Int){
        val lst = et_name.text.toString().split("\n")
        mDoaminNameList.clear()
        for (item:Int in 0 until lst.size){
            if(!lst[item].isNullOrBlank())
            mDoaminNameList.add(lst[item])
        }
        mCount = mDoaminNameList.size
        tvCount.text = getString(R.string.domain_number)+":"+mCount
    }

    private fun addAddress(address: String) {
        if (address.isEmpty()) {
            "Address Invalid".toast()
            return
        }
        mAddressList.add(address)
        genAddressView(address)
    }

    /** 生成并添加一个View **/
    private fun genAddressView(address: String) {
        val view = LayoutInflater.from(this).inflate(R.layout.item_add_adress, null)
        view.tvAddress.text = address
        view.ivDeleted.setOnClickListener {
            addressContent.removeView(view)
            if (mAddressList.size > mViewList.indexOf(view))
                mAddressList.removeAt(mViewList.indexOf(view))
            mViewList.remove(view)
        }
        mViewList.add(view)
        addressContent.addView(view)
    }

    private fun onDateResult(string: String) {
        "issue domain " + string.logE()
        if (string.isNotEmpty()) {
            getString(R.string.issue_nfts_success).toast()
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && data != null) {
            if (requestCode > 0) {
                val result = data.getStringExtra("result")
                mAddressList.add(result)
                genAddressView(result)
            }
        }
    }

    private fun showFingerPrintDialog() {
        confirmPassword(mContext.sharedPref.isFinger, supportFragmentManager, object : FingerSuccessCallback() {
            override fun onCheckSuccess() {
                mWebView.evaluateJavascript(WebViewApi.needPrivateKeyResponse(sharedPref.privateKey), null)
            }
        })
    }

}
