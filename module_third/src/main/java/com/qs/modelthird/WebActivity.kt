package com.qs.modelthird

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.widget.LinearLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.qs.modelthird.utils.ProgressWebView
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import kotlinx.android.synthetic.main.activity_web.*

@Route(path = "/webView/webActivity")
class WebActivity : SimpleActivity() {

    override val layoutId: Int
        get() = R.layout.activity_web

    @SuppressLint("SetTextI18n")
    override fun initData() {
        val url = intent.getStringExtra("url")
        val webView = ProgressWebView(applicationContext)
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        tvTitle?.text = "MyEvt"
        webView.layoutParams = params
        fl_web_view.addView(webView)
        webView.loadUrl(url)
    }

}
