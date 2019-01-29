package com.qs.modulemain.ui.activity

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import com.google.gson.Gson
import com.qs.modulemain.ui.activity.index.CreateWalletIdIndex
import com.smallcat.shenhai.mvpbase.extension.logE
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.start
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.utils.LocalManageUtil
import com.smallcat.shenhai.mvpbase.utils.fitSystemWhite
import com.smallcat.shenhai.mvpbase.utils.getWebViewInstance
import java.util.*

class SplashActivity : AppCompatActivity() {

    private lateinit var mWebView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fitSystemWhite(this)
        mWebView = getWebViewInstance()
        if (sharedPref.languages == -1) {
            if (LocalManageUtil.getSystemLocale(this) == Locale.CHINA) {
                sharedPref.languages = 0
            } else {
                sharedPref.languages = 1
            }
        }
        Handler().postDelayed({
            if (sharedPref.chooseNode != "mainnet14.everitoken.io") {
                val map = HashMap<String, Any>()
                map["host"] = sharedPref.chooseNode
                map["port"] = 443
                map["protocol"] = "https"
                WebViewApi.changeNetwork(Gson().toJson(map)).logE()
                mWebView.evaluateJavascript(WebViewApi.changeNetwork(Gson().toJson(map)), null)
            }
            mWebView.loadUrl(WebViewApi.EVTInit(), null)
            if (sharedPref.publicKey.isEmpty()) {
                start(CreateWalletIdIndex::class.java)
            } else {
                start(MainActivity::class.java)
            }
            finish()
        }, 2000)
    }
}
