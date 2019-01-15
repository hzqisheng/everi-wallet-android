package com.qs.modulemain.ui.activity

import android.view.ViewGroup
import android.webkit.WebView
import android.widget.LinearLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.qs.modulemain.R
import com.qs.modulemain.arouter.ARouterConfig
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import kotlinx.android.synthetic.main.activity_web.*

@Route(path = ARouterConfig.WEB_ACTIVITY)
class WebActivity : SimpleActivity() {

    override val layoutId: Int
        get() = R.layout.activity_web

    override fun initData() {
        val url = intent.getStringExtra("url")
        val webView = WebView(applicationContext)
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        webView.layoutParams = params
        fl_web_view.addView(webView)
        webView.loadUrl(url)
    }

}
