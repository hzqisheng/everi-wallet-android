package com.smallcat.shenhai.mvpbase.utils

import android.annotation.SuppressLint
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.LinearLayout
import com.smallcat.shenhai.mvpbase.App
import com.smallcat.shenhai.mvpbase.extension.logE
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.helper.MessageEvent
import com.smallcat.shenhai.mvpbase.model.helper.RxBus
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import com.smallcat.shenhai.mvpbase.model.widget.ProgressWebView

@SuppressLint("StaticFieldLeak")
private var webView:WebView? = null

fun initWebView(){
    webView = ProgressWebView(App.getInstance())
    val params = LinearLayout.LayoutParams(0, 0)
    webView?.layoutParams = params
    webView?.addJavascriptInterface(WebViewCallBack(), "test")
    webView?.loadUrl("file:///android_asset/dist/index.html")
    "WebView初始化成功".logE()
}

fun getWebViewInstance(): WebView{
    if (webView == null){
        initWebView()
    }
    return webView!!
}

fun destroyWebView(){
    webView?.let {
        it.loadUrl("")
        it.clearCache(true)
        it.clearHistory()
    }
    webView = null
}

private class WebViewCallBack : Any() {

    /**
     * create EVT wallet callback
     * @param s return
     */
    @JavascriptInterface
    fun createEVTWalletCallback(s: String) {
        s.logE()
        RxBus.post(MessageEvent(s, RxBusCenter.LOGIN))
    }

    @JavascriptInterface
    fun getOwnedTokensCallback(s: String) {
        s.logE()
        RxBus.post(MessageEvent(s, RxBusCenter.MY_NFTS))
    }

    @JavascriptInterface
    fun getFungibleBalanceCallback(s: String) {
        "222".logE()
        s.logE()
        RxBus.post(MessageEvent(s, RxBusCenter.MY_FTS))
    }

    @JavascriptInterface
    fun importEVTWalletCallback(s: String) {
        s.logE()
        RxBus.post(MessageEvent(s, RxBusCenter.LOGIN))
    }

    @JavascriptInterface
    fun needPrivateKey() {

    }
}