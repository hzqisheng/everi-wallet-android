package com.qs.modulemain.ui.activity.my

import android.view.ViewGroup
import android.widget.LinearLayout
import com.qs.modulemain.R
import com.qs.modulemain.presenter.AboutUsPresenter
import com.qs.modulemain.ui.widget.ProgressWebView
import com.qs.modulemain.view.AboutUsView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.extension.toast
import kotlinx.android.synthetic.main.activity_about_us.*

class AboutUsActivity : BaseActivity<AboutUsPresenter>(), AboutUsView {

    override fun initPresenter() {
        mPresenter = AboutUsPresenter(mContext)
    }

    override val layoutId: Int
        get() = R.layout.activity_about_us

    override fun initData() {
        tvTitle?.text = getString(R.string.about_us)

        val params = LinearLayout.LayoutParams(0, 0)
        val webView = ProgressWebView(applicationContext)
        webView.layoutParams = params
        findViewById<ViewGroup>(android.R.id.content).addView(webView)
        webView.loadUrl("file:///android_asset/dist/index.html")

        iv_head.setOnClickListener { it ->
        }

    }
}
