package com.qs.modulemain.ui.activity.index

import android.content.Intent
import android.view.View
import com.google.gson.Gson
import com.qs.modulemain.R
import com.qs.modulemain.bean.ChooseGetBean
import com.qs.modulemain.util.confirmPassword
import com.smallcat.shenhai.mvpbase.base.FingerSuccessCallback
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.getResourceColor
import com.smallcat.shenhai.mvpbase.extension.logE
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.helper.MessageEvent
import com.smallcat.shenhai.mvpbase.model.helper.RxBus
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter.SET_FTS
import com.smallcat.shenhai.mvpbase.utils.lastPushTransaction
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_fts_issue_edit.*
import java.util.*

class FtsIssueEditActivity : SimpleActivity() {
    private lateinit var mFTSBean: ChooseGetBean
    private lateinit var upIssuBean: UpIssueBean

    override val layoutId: Int
        get() = R.layout.activity_fts_issue_edit

    override fun initData() {

        addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    when (it.type) {
                        RxBusCenter.SET_FTS -> onDataResult(it.msg)
                        RxBusCenter.NEED_PRIVATE_KEY -> showFingerPrintDialog()
                    }
                })

        mFTSBean = intent.getSerializableExtra("data") as ChooseGetBean
        tvTitle?.text = mFTSBean.name
        tv_name.text = mFTSBean.name
        tv_code.text = mFTSBean.creator
        tv_threshold.text = mFTSBean.issue.threshold.toString()
        tv_threshold1.text = mFTSBean.manage.threshold.toString()

        //发行地址
        var issueString: String = ""
        if (mFTSBean.issue.authorizers.size > 0) {
            for (authorizer in mFTSBean.issue.authorizers) {
                issueString += authorizer.ref + "\n"
            }
        }
        tv_address.text = issueString


        var manageString = ""
        if (mFTSBean.manage.authorizers.size > 0) {
            for (authorizer in mFTSBean.manage.authorizers) {
                manageString += authorizer.ref + "\n"
            }
        }
        tv_address1.text = manageString


        upIssuBean = UpIssueBean()
        upIssuBean.sym_id = mFTSBean.sym.split("#")[1]
        upIssuBean.issue = mFTSBean.issue
        upIssuBean.manage = mFTSBean.manage
//        upIssuBean.metas = ArrayList()

        /*tvRight?.apply {
            text = getString(R.string.authority_setting)
            setTextColor(getResourceColor(R.color.color_e4))
            setOnClickListener {
                var intent = Intent(this@FtsIssueEditActivity, IssueFtsAuthorityActivity::class.java)
                startActivityForResult(intent, 101)
            }
        }*/

        tv_save.setOnClickListener {
            var sendJson = Gson().toJson(upIssuBean)
            sendJson.logE()
            lastPushTransaction = SET_FTS
            mWebView.evaluateJavascript(WebViewApi.pushTransaction("updfungible", sendJson)) {}
        }

        iv_add_data.setOnClickListener {
            var intent = Intent(this@FtsIssueEditActivity, AddMetaActivity::class.java)
            startActivityForResult(intent, 103)
        }
        tv_add_data.setOnClickListener {
            var intent = Intent(this@FtsIssueEditActivity, AddMetaActivity::class.java)
            startActivityForResult(intent, 103)
        }
    }

    fun onDataResult(s: String) {
        if (!s.isEmpty()) {
            "设置成功".toast()
            finish()
        }
        s.logE()
    }

    class UpIssueBean {
        //sym_id
        var sym_id: String = ""
        //管理
        var manage: ChooseGetBean.ManageBean? = null
        //发型
        var issue: ChooseGetBean.IssueBean? = null

        var metas: ArrayList<ChooseGetBean.MetasBean>? = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101) {
            if (resultCode > 0 && data != null) {
                var chooseGetBean: ChooseGetBean = data!!.getSerializableExtra("result") as ChooseGetBean
                upIssuBean.issue = chooseGetBean.issue
                upIssuBean.manage = chooseGetBean.manage


                tv_threshold.text = upIssuBean.issue!!.threshold.toString()
                tv_threshold1.text = upIssuBean.manage!!.threshold.toString()

                //发行地址
                var issueString: String = ""
                if (upIssuBean.issue != null && upIssuBean.issue!!.authorizers != null && upIssuBean.issue!!.authorizers.size > 0) {
                    for (authorizer in upIssuBean.issue!!.authorizers) {
                        if (authorizer != null)
                            issueString += authorizer.ref + "\n"
                    }
                }
                tv_address.text = issueString


                var manageString: String = ""
                if (upIssuBean.manage != null && upIssuBean.manage!!.authorizers != null && upIssuBean.manage!!.authorizers.size > 0) {
                    for (authorizer in upIssuBean.manage!!.authorizers) {
                        if (authorizer != null)
                            manageString += authorizer.ref + "\n"
                    }
                }
                tv_address1.text = manageString
            }
        }
        //AddMetaActivity
        if (requestCode == 103) {
            if (resultCode > 0 && data != null) {
                var metaBean = data.getSerializableExtra("result") as ChooseGetBean.MetasBean
                if (upIssuBean.metas == null) {
                    upIssuBean.metas = ArrayList()
                }
                metaBean.toString().logE()
                upIssuBean.metas!!.add(metaBean)
            }

            if (upIssuBean.metas != null && upIssuBean.metas!!.size > 0) {
                iv_add_data.visibility = View.GONE
                var resultString = ""
                upIssuBean.metas!!.forEach { resultString += "Key:" + it.key + " Value:" + it.value + "\n" }
                tv_add_data.text = resultString
            } else {
                iv_add_data.visibility = View.VISIBLE
                tv_add_data.text = ""
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
