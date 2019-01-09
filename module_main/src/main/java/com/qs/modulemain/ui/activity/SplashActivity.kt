package com.qs.modulemain.ui.activity

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import com.qs.modulemain.R
import com.qs.modulemain.ui.activity.index.CreateWalletIdIndex
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.start
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.utils.fitSystemWhite
import com.smallcat.shenhai.mvpbase.utils.getWebViewInstance

class SplashActivity : AppCompatActivity() {

    private lateinit var mWebView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fitSystemWhite(this)
        Handler().postDelayed({
            mWebView = getWebViewInstance()
            mWebView.evaluateJavascript(WebViewApi.EVTInit()) {}
            if(sharedPref.publicKey.isEmpty()){
                start(CreateWalletIdIndex::class.java)
            }else{
                start(MainActivity::class.java)
            }
            finish()
        }, 2000)
    }
}
