package com.qs.modulemain.ui.activity.index

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.gson.Gson
import com.qs.modulemain.R
import com.qs.modulemain.bean.VersionBean
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.start
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.helper.MessageEvent
import com.smallcat.shenhai.mvpbase.model.helper.RxBus
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import com.smallcat.shenhai.mvpbase.model.http.ApiConfig
import com.smallcat.shenhai.mvpbase.utils.LocalManageUtil
import com.smallcat.shenhai.mvpbase.utils.getVersionName
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_create_wallet_id_index.*
import java.util.*

class CreateWalletIdIndex : SimpleActivity() {

    private var dialog: Dialog? = null

    override val layoutId: Int
        get() = R.layout.activity_create_wallet_id_index

    override fun initData() {

        sharedPref.publicKey = ""
        sharedPref.privateKey = ""
        sharedPref.password = ""
        sharedPref.name = ""
        sharedPref.mnemoinc = ""

        addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    when (it.type) {
                        RxBusCenter.CHECK_VERSION -> checkSuccess(it.msg)
                    }
                })

        tv_create_id.setOnClickListener {
            start(WalletAddIdActivity::class.java)
        }

        tv_recovery_id.setOnClickListener {
            start(RecoveryActivity::class.java)
        }

        mWebView.evaluateJavascript(WebViewApi.getAPPVersion(), null)
    }

    @SuppressLint("SetTextI18n")
    private fun checkSuccess(msg: String) {
        val bean = Gson().fromJson(msg, VersionBean::class.java)
        if (getVersionName(mContext) != bean.androidVersion) {
            if (dialog == null) {
                dialog = Dialog(mContext, R.style.CustomDialog)
            }
            val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_update, null)
            val tvMsg = view.findViewById<TextView>(R.id.tv_msg)
            val tvSure = view.findViewById<TextView>(R.id.tv_download)
            val tvCancel = view.findViewById<TextView>(R.id.tv_cancel)
            if (LocalManageUtil.getSetLanguageLocale(this) == Locale.CHINA) {
                tvMsg.text = "系统检测到当前的最新版本为${bean.androidVersion}，是否下载更新?"
            } else {
                tvMsg.text = "The latest version is ${bean.androidVersion} and whether to download the update or not？"
            }
            tvSure.setOnClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_VIEW
                intent.data = Uri.parse(bean.androidUploadUrl/*ApiConfig.SHARE_URL*/)
                startActivity(Intent.createChooser(intent, getString(R.string.choose_browser)))
            }
            if (bean.isAndroidForceUpdate) {
                tvCancel.visibility = View.GONE
            }
            tvCancel.setOnClickListener { dialog!!.dismiss() }
            dialog!!.setContentView(view)
            dialog!!.setCanceledOnTouchOutside(false)
            dialog!!.setCancelable(false)
            dialog!!.show()
        }
    }

}
