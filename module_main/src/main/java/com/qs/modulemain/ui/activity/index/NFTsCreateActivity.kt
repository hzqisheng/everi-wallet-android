package com.qs.modulemain.ui.activity.index

import android.app.Dialog
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.widget.*
import com.google.gson.Gson
import com.qs.modulemain.R
import com.qs.modulemain.bean.DomainBean
import com.qs.modulemain.ui.activity.manage.AddGroupActivity
import com.qs.modulemain.ui.activity.manage.DomainDetailActivity
import com.qs.modulemain.ui.adapter.*
import com.qs.modulemain.ui.fragment.HelpDialogFragment
import com.qs.modulemain.util.DataUtils
import com.qs.modulemain.util.confirmPassword
import com.smallcat.shenhai.mvpbase.base.FingerSuccessCallback
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.logE
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.bean.ChooseBean
import com.smallcat.shenhai.mvpbase.model.helper.MessageEvent
import com.smallcat.shenhai.mvpbase.model.helper.RxBus
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import com.smallcat.shenhai.mvpbase.utils.lastPushTransaction
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_nfts_create.*
import java.lang.Exception

/** 创建通证 **/
class NFTsCreateActivity : SimpleActivity() {

    override val layoutId: Int
        get() = R.layout.activity_nfts_create

    private lateinit var mDomainBean: DomainBean

    private var addType: Int = 0 //addType:0（发行）、1（转移）、2（管理）

    //是否是修改阈
    private var isDomainDetailUpdate = false

    private lateinit var issueAdapter: AuthorizerIssueAdapter
    private lateinit var transferAdapter: AuthorizerTransferAdapter
    private lateinit var manageAdapter: AuthorizerManageAdapter

    private lateinit var issueList: ArrayList<DomainBean.IssueBean.AuthorizersBean>
    private lateinit var transferList: ArrayList<DomainBean.TransferBean.AuthorizersBeanX>
    private lateinit var manageList: ArrayList<DomainBean.ManageBean.AuthorizersBeanXX>

    override fun initData() {
        isDomainDetailUpdate = intent.getBooleanExtra("domainDetailUpdate", false)
        mDomainBean = DomainBean.build()
        val issueBean = DomainBean.IssueBean()
        issueBean.authorizers = ArrayList<DomainBean.IssueBean.AuthorizersBean>()
        val transferBean = DomainBean.TransferBean()
        transferBean.authorizers = ArrayList<DomainBean.TransferBean.AuthorizersBeanX>()
        val manageBean = DomainBean.ManageBean()
        manageBean.authorizers = ArrayList<DomainBean.ManageBean.AuthorizersBeanXX>()
        if (isDomainDetailUpdate) {
            et_nfts.setText(intent.getStringExtra("domainName"))
            //设置不可编辑
            et_nfts.keyListener = null
            et_number_domain0.setText(DomainDetailActivity.mDomainDetail.issue.threshold.toString())
            et_number_domain1.setText(DomainDetailActivity.mDomainDetail.transfer.threshold.toString())
            et_number_domain2.setText(DomainDetailActivity.mDomainDetail.manage.threshold.toString())
            mDomainBean.creator = DomainDetailActivity.mDomainDetail.creator
            mDomainBean.name = intent.getStringExtra("domainName")
            issueBean.threshold = DomainDetailActivity.mDomainDetail.issue.threshold
            transferBean.threshold = DomainDetailActivity.mDomainDetail.transfer.threshold
            manageBean.threshold = DomainDetailActivity.mDomainDetail.manage.threshold
            DomainDetailActivity.mDomainDetail.issue.authorizers.forEach {
                issueBean.authorizers.add(DomainBean.IssueBean.AuthorizersBean(it.ref, it.weight))
            }
            DomainDetailActivity.mDomainDetail.transfer.authorizers.forEach {
                transferBean.authorizers.add(DomainBean.TransferBean.AuthorizersBeanX(it.ref, it.weight))
            }
            DomainDetailActivity.mDomainDetail.manage.authorizers.forEach {
                manageBean.authorizers.add(DomainBean.ManageBean.AuthorizersBeanXX(it.ref, it.weight))
            }
        } else {
            issueBean.authorizers.add(DomainBean.IssueBean.AuthorizersBean("[A] ${sharedPref.publicKey}", 1))
            transferBean.authorizers.add(DomainBean.TransferBean.AuthorizersBeanX("[G] .OWNER", 1))
            manageBean.authorizers.add(DomainBean.ManageBean.AuthorizersBeanXX("[A] ${sharedPref.publicKey}", 1))
        }
        mDomainBean.issue = issueBean
        mDomainBean.transfer = transferBean
        mDomainBean.manage = manageBean
        issueList = mDomainBean.issue.authorizers
        transferList = mDomainBean.transfer.authorizers
        manageList = mDomainBean.manage.authorizers
        issueAdapter = AuthorizerIssueAdapter(issueList)
        transferAdapter = AuthorizerTransferAdapter(transferList)
        manageAdapter = AuthorizerManageAdapter(manageList)
        rv_issue.adapter = issueAdapter
        rv_transfer.adapter = transferAdapter
        rv_manage.adapter = manageAdapter

        addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    when (it.type) {
                        RxBusCenter.CREATE_DOMAIN -> {
                            dismissLoading()
                            if (isDomainDetailUpdate) {
                                setResult(1, null)
                            }
                            getString(R.string.create_success).toast()
                            finish()
                        }
                        RxBusCenter.NEED_PRIVATE_KEY -> showFingerPrintDialog()
                        RxBusCenter.CHECK_ADDRESS -> checkSuccess(it.msg)
                        RxBusCenter.REQUEST_ERROR -> dismissLoading()
                    }
                })

        tvTitle?.text = if (isDomainDetailUpdate) getString(R.string.authority_setting) else getString(R.string.new_domain)
        ivRight?.apply {
            setBackgroundResource(R.drawable.ic_question)
            setOnClickListener { showHelpDialog() }
        }

        tvSure.setOnClickListener {
            handleCompleted()
        }

        ll_add_issue.setOnClickListener {
            addType = 0
            showAddTypeDialog()
        }
        ll_add_transfer.setOnClickListener {
            addType = 1
            showAddTypeDialog()
        }
        ll_add_manage.setOnClickListener {
            addType = 2
            showAddTypeDialog()
        }

        list = arrayListOf(ChooseBean(getString(R.string.add_address1)),
                ChooseBean(getString(R.string.add_group)))


        issueAdapter.setOnItemChildClickListener { adapter, view, position ->
            adapter.remove(position)
        }
        transferAdapter.setOnItemChildClickListener { adapter, view, position ->
            adapter.remove(position)
        }
        manageAdapter.setOnItemChildClickListener { adapter, view, position ->
            adapter.remove(position)
        }
        initDomainView()
    }

    private fun initDomainView() {
        iv_reduce_domain0.setOnClickListener {
            try {
                if (et_number_domain0.text.toString().toInt() <= 1) {
                    return@setOnClickListener
                }
                et_number_domain0.setText(((et_number_domain0.text.toString().toInt()) - 1).toString())
            } catch (e: Exception) {
                e.printStackTrace()
                et_number_domain0.setText("1")
            }
        }
        iv_add_domain0.setOnClickListener {
            try {
                et_number_domain0.setText(((et_number_domain0.text.toString().toInt()) + 1).toString())
            } catch (e: Exception) {
                e.printStackTrace()
                et_number_domain0.setText("1")
            }
        }
        iv_reduce_domain1.setOnClickListener {
            try {
                if (et_number_domain1.text.toString().toInt() <= 1) {
                    return@setOnClickListener
                }
                et_number_domain1.setText(((et_number_domain1.text.toString().toInt()) - 1).toString())
            } catch (e: Exception) {
                e.printStackTrace()
                et_number_domain1.setText("1")
            }
        }
        iv_add_domain1.setOnClickListener {
            try {
                et_number_domain1.setText(((et_number_domain1.text.toString().toInt()) + 1).toString())
            } catch (e: Exception) {
                e.printStackTrace()
                et_number_domain1.setText("1")
            }
        }
        iv_reduce_domain2.setOnClickListener {
            try {
                if (et_number_domain2.text.toString().toInt() <= 1) {
                    return@setOnClickListener
                }
                et_number_domain2.setText(((et_number_domain2.text.toString().toInt()) - 1).toString())
            } catch (e: Exception) {
                e.printStackTrace()
                et_number_domain2.setText("1")
            }
        }
        iv_add_domain2.setOnClickListener {
            try {
                et_number_domain2.setText(((et_number_domain2.text.toString().toInt()) + 1).toString())
            } catch (e: Exception) {
                e.printStackTrace()
                et_number_domain2.setText("1")
            }
        }
    }

    private fun getDomainValue(type: Int): Int {
        return try {
            var result = when (type) {
                0 -> et_number_domain0.text.toString().toInt()
                1 -> et_number_domain1.text.toString().toInt()
                2 -> et_number_domain2.text.toString().toInt()
                else -> 1
            }
            if (result < 1) {
                result = 1
            }
            result
        } catch (e: Exception) {
            e.printStackTrace()
            1
        }
    }

    private fun handleCompleted() {
        if (isDomainDetailUpdate) {
            mDomainBean.issue.threshold = getDomainValue(0)
            mDomainBean.transfer.threshold = getDomainValue(1)
            mDomainBean.manage.threshold = getDomainValue(2)
        } else {
            if (et_nfts.text.isEmpty()) {
                getString(R.string.please_input_domain_name).toast()
                return
            }
            mDomainBean.name = et_nfts.text.toString()
            mDomainBean.creator = sharedPref.publicKey
            mDomainBean.issue.threshold = getDomainValue(0)
            mDomainBean.transfer.threshold = getDomainValue(1)
            mDomainBean.manage.threshold = getDomainValue(2)
        }
        if (issueList.size < 1 || transferList.size < 1 || manageList.size < 1) {
            getString(R.string.invalid_authority_settings).toast()
            return
        }
        val domainJson = Gson().toJson(mDomainBean)
        domainJson.logE()
        Log.e("Lody", domainJson)
        lastPushTransaction = RxBusCenter.CREATE_DOMAIN
        if (isDomainDetailUpdate) {
            mWebView.evaluateJavascript(WebViewApi.pushTransaction("updatedomain", domainJson)) {}
        } else {
            mWebView.evaluateJavascript(WebViewApi.pushTransaction("newdomain", domainJson)) {}
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) return
        //扫码添加公钥
        if (resultCode == ScanActivity.resultCode) {
            if (etPublicKey != null) {
                val result = data!!.getStringExtra("result")
                etPublicKey.setText(result.toString())
            }
        } else if (resultCode == AddGroupActivity.resultCode) { //添加组
            val groupName = data!!.getStringExtra("groupName")
            val weight = data!!.getIntExtra("weight", 1)
            when (addType) {
                0 -> {
                    issueList.add(DomainBean.IssueBean.AuthorizersBean("[G] $groupName", weight))
                    issueAdapter.notifyDataSetChanged()
                }
                1 -> {
                    transferList.add(DomainBean.TransferBean.AuthorizersBeanX("[G] $groupName", weight))
                    transferAdapter.notifyDataSetChanged()
                }
                2 -> {
                    manageList.add(DomainBean.ManageBean.AuthorizersBeanXX("[G] $groupName", weight))
                    manageAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun showHelpDialog() {
        /*val contentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_help, null, false)
        val recyclerView = contentView.findViewById<RecyclerView>(R.id.rv_list)
        val adapter = HelpAdapter(DataUtils.addNFtsHelpData(mContext))
        recyclerView.adapter = adapter
        val popupWindow = PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true)
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popupWindow.isOutsideTouchable = true
        popupWindow.isTouchable = true
        popupWindow.animationStyle = R.style.ActionSheetDialogTop
        popupWindow.showAsDropDown(toolbar)*/
        val fragment = HelpDialogFragment()
        fragment.setDate(DataUtils.addNFtsHelpData(mContext), toolbar.height)
        fragment.show(supportFragmentManager, "helpDialog")
    }

    private fun showFingerPrintDialog() {
        confirmPassword(mContext.sharedPref.isFinger, supportFragmentManager, object : FingerSuccessCallback() {
            override fun onCheckSuccess() {
                mWebView.evaluateJavascript(WebViewApi.needPrivateKeyResponse(sharedPref.privateKey)) {
                    showLoading()
                }
            }
        })
    }

    private var dialog: Dialog? = null
    private lateinit var list: ArrayList<ChooseBean>
    private fun showAddTypeDialog() {
        if (dialog == null) {
            dialog = Dialog(mContext, R.style.CustomDialog)
        }
        val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_group_create, null)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_list)
        val adapter = ChooseAdapter(list)
        adapter.setOnItemClickListener { _, _, position ->
            dialog!!.dismiss()
            if (position == 0) { //添加地址
                showAddAddressDialog()
            } else if (position == 1) { //添加组
                val intent = Intent(this@NFTsCreateActivity, AddGroupActivity::class.java)
                startActivityForResult(intent, 2)
            }
        }
        recyclerView.adapter = adapter
        dialog!!.setContentView(view)
        dialog!!.setCanceledOnTouchOutside(true)
        dialog!!.setCancelable(true)
        dialog!!.show()
    }

    private var addressDialog: Dialog? = null
    lateinit var etPublicKey: EditText
    lateinit var etWeight: EditText
    private fun showAddAddressDialog() {
        if (addressDialog == null) {
            addressDialog = Dialog(mContext, R.style.CustomDialog)
        }
        val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_add_address, null)
        val ivScan = view.findViewById<ImageView>(R.id.iv_scan)
        etPublicKey = view.findViewById<EditText>(R.id.et_public_key)
        etWeight = view.findViewById<EditText>(R.id.et_weight)
        val btnCommit = view.findViewById<Button>(R.id.btn_commit)
        ivScan.setOnClickListener {
            val intent = Intent(mContext, ScanActivity::class.java)
            intent.putExtra("ScanType", 1000)
            startActivityForResult(intent, 1)
        }
        btnCommit.setOnClickListener {
            addressDialog!!.dismiss()
            if (etPublicKey.text.isNullOrEmpty()) {
                getString(R.string.please_input_key).toast()
            } else {
                mWebView.evaluateJavascript(WebViewApi.isValidPublicKey(etPublicKey.text.toString()), null)
            }
        }
        addressDialog!!.setContentView(view)
        addressDialog!!.setCanceledOnTouchOutside(false)
        addressDialog!!.setCancelable(true)
        addressDialog!!.show()
    }

    private fun checkSuccess(string: String) {
        if (string == "true") {
            when (addType) {
                0 -> {
                    issueList.add(DomainBean.IssueBean.AuthorizersBean("[A] ${etPublicKey.text.toString()}", getWeight()))
                    issueAdapter.notifyDataSetChanged()
                }
                1 -> {
                    transferList.add(DomainBean.TransferBean.AuthorizersBeanX("[A] ${etPublicKey.text.toString()}", getWeight()))
                    transferAdapter.notifyDataSetChanged()
                }
                2 -> {
                    manageList.add(DomainBean.ManageBean.AuthorizersBeanXX("[A] ${etPublicKey.text.toString()}", getWeight()))
                    manageAdapter.notifyDataSetChanged()
                }
            }
        } else {
            getString(R.string.invalid_key).toast()
        }
    }

    override fun onDestroy() {
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
        }
        if (addressDialog != null && addressDialog!!.isShowing) {
            addressDialog!!.dismiss()
        }
        super.onDestroy()
    }

    private fun getWeight(): Int {
        return try {
            etWeight.text.toString().toInt()
        } catch (e: Exception) {
            e.printStackTrace()
            1
        }
    }

}
