package com.qs.modulemain.ui.activity.my

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
import com.qs.modulemain.presenter.AboutUsPresenter
import com.qs.modulemain.view.AboutUsView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.http.ApiConfig
import com.smallcat.shenhai.mvpbase.utils.LocalManageUtil
import com.smallcat.shenhai.mvpbase.utils.getVersionName
import kotlinx.android.synthetic.main.activity_about_us.*
import java.util.*

class AboutUsActivity : BaseActivity<AboutUsPresenter>(), AboutUsView {

    private var dialog: Dialog? = null

    override fun initPresenter() {
        mPresenter = AboutUsPresenter(mContext)
    }

    override val layoutId: Int
        get() = R.layout.activity_about_us

    override fun initData() {
        tvTitle?.text = getString(R.string.about_us)
        tv_version.text = getVersionName(mContext)

        tv_check_version.setOnClickListener {
            mWebView.evaluateJavascript(WebViewApi.getAPPVersion(), null)
        }


    }

    @SuppressLint("SetTextI18n")
    override fun checkSuccess(msg: String) {
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
        } else {
            getString(R.string.latest_version).toast()
        }
    }
}
