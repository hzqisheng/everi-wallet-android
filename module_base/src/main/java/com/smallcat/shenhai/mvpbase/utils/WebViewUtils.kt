package com.smallcat.shenhai.mvpbase.utils

import android.annotation.SuppressLint
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.LinearLayout
import com.google.gson.Gson
import com.smallcat.shenhai.mvpbase.App
import com.smallcat.shenhai.mvpbase.extension.logE
import com.smallcat.shenhai.mvpbase.extension.toResultBean
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.bean.ErrorMSG
import com.smallcat.shenhai.mvpbase.model.helper.MessageEvent
import com.smallcat.shenhai.mvpbase.model.helper.RxBus
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import com.smallcat.shenhai.mvpbase.model.widget.ProgressWebView
import java.security.PublicKey
import java.util.*

@SuppressLint("StaticFieldLeak")
private var webView: WebView? = null

fun initWebView() {
    webView = ProgressWebView(App.getInstance())
    val params = LinearLayout.LayoutParams(0, 0)
    webView?.layoutParams = params
    webView?.addJavascriptInterface(WebViewCallBack(), "test")
    webView?.loadUrl("file:///android_asset/dist/index.html")
    "WebView初始化成功".logE()
}

//最后一次调用pushTransaction的类型
var lastPushTransaction: Int = 0

var lastMY_NFTS: Int = 0

//收付款
var qrcode_type: Int = 0

fun getWebViewInstance(): WebView {
    if (webView == null) {
        initWebView()
    }
    return webView!!
}

fun destroyWebView() {
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
        "createEVTWalletCallback".logE()
        s.logE()
        RxBus.post(MessageEvent(handleResult(s), RxBusCenter.CREATE_WALL))
    }

    @JavascriptInterface
    fun getOwnedTokensCallback(s: String) {
        "getOwnedTokensCallback".logE()
        s.logE()
        RxBus.post(MessageEvent(handleResult(s), lastMY_NFTS))
    }

    @JavascriptInterface
    fun getFungibleBalanceCallback(s: String) {
        "getFungibleBalanceCallback".logE()
        s.logE()
        RxBus.post(MessageEvent(handleResult(s), RxBusCenter.MY_FTS))
    }

    @JavascriptInterface
    fun importEVTWalletCallback(s: String) {
        "importEVTWalletCallback".logE()
        s.logE()
        RxBus.post(MessageEvent(handleResult(s), RxBusCenter.LOGIN))
    }

    @JavascriptInterface
    fun needPrivateKey(s: String) {
        "needPrivateKey".logE()
        RxBus.post(MessageEvent(handleResult(s), RxBusCenter.NEED_PRIVATE_KEY))
    }

    @JavascriptInterface
    fun getCreatedFungiblesCallback(s: String) {
        "getCreatedFungiblesCallback".logE()
        s.logE()
        RxBus.post(MessageEvent(handleResult(s), RxBusCenter.CHOOSE_FTS))
    }

    @JavascriptInterface
    fun pushTransactionCallback(s: String) {
        ("pushTransactionCallback()" + s).logE()
        RxBus.post(MessageEvent(handleResult(s), lastPushTransaction))
    }

    @JavascriptInterface
    fun getEVTFungiblesListCallback(s: String) {
        ("getEVTFungiblesListCallback()  " + s).logE()
        RxBus.post(MessageEvent(handleResult(s), RxBusCenter.CHOOSE_FTS))
    }

    @JavascriptInterface
    fun getEVTDomainsListCallback(s: String) {
        ("getEVTDomainsListCallback()  " + s).logE()
        RxBus.post(MessageEvent(handleResult(s), RxBusCenter.MY_DOMAIN))
    }

    /** 收付款二维码 **/
    @JavascriptInterface
    fun getEVTLinkQrImageCallback(s: String) {
        ("getEVTLinkQrImageCallback()  " + s).logE()
        RxBus.post(MessageEvent(handleResult(s), qrcode_type))
    }

    /** 扫描二维码 **/
    @JavascriptInterface
    fun parseEvtLinkCallback(s: String) {
        ("parseEvtLinkCallback()  " + s).logE()
        RxBus.post(MessageEvent(handleResult(s), qrcode_type))
    }

    @JavascriptInterface
    fun getUniqueLinkIdCallback(s: String) {
        ("getUniqueLinkId()  " + s).logE()
        RxBus.post(MessageEvent(handleResult(s), RxBusCenter.GET_LINkID))
    }

    @JavascriptInterface
    fun getActionsCallback(s: String) {
        ("getActionsCallback()  $s").logE()
        RxBus.post(MessageEvent(handleResult(s), RxBusCenter.RECORD_TRANSACATION))
    }

    @JavascriptInterface
    fun getEVTFungibleBalanceListCallback(s: String) {
        ("getEVTFungibleBalanceListCallback$s").logE()
        RxBus.post(MessageEvent(handleResult(s), RxBusCenter.HOME_FTS))
    }

    @JavascriptInterface
    fun getFungibleActionsByAddressCallback(s: String) {
        ("getFungibleActionsByAddressCallback$s").logE()
        RxBus.post(MessageEvent(handleResult(s), RxBusCenter.RECORD_TRANSACATION))
    }

    @JavascriptInterface
    fun getEstimatedChargeForTransactionCallback(s: String) {
        ("getEstimatedChargeForTransactionCallback$s").logE()
        RxBus.post(MessageEvent(handleResult(s), RxBusCenter.SERVICE_CHARGE))
    }

    @JavascriptInterface
    fun privateToPublicCallback(s: String) {
        ("privateToPublicCallback$s").logE()
        RxBus.post(MessageEvent(handleResult(s), RxBusCenter.PRIVATE_TO_PUBLIC))
    }

    @JavascriptInterface
    fun getFungibleSymbolDetailCallback(s: String) {
        ("getFungibleSymbolDetailCallback$s").logE()
        RxBus.post(MessageEvent(handleResult(s), RxBusCenter.SYMBOL_DETAIL))
    }

    /**
     * 交易结果放回
     */
    @JavascriptInterface
    fun getStatusOfEvtLinkCallback(s: String) {
        ("getStatusOfEvtLinkCallback$s").logE()
        RxBus.post(MessageEvent(handleResult(s), RxBusCenter.PAY_RECORD))
    }

    /**
     * 验证助记词
     */
    @JavascriptInterface
    fun validateMnemonicCallback(s: String) {
        ("validateMnemonicCallback$s").logE()
        RxBus.post(MessageEvent(handleResult(s), RxBusCenter.CHECK_MEMO))
    }

    /**
     * 验证私钥
     */
    @JavascriptInterface
    fun isValidPrivateKeyCallback(s: String) {
        ("isValidPrivateKey$s").logE()
        RxBus.post(MessageEvent(handleResult(s), RxBusCenter.CHECK_PRIVATE))
    }

    /*
    @JavascriptInterface
    fun changeNetworkCallback(s: String) {
        ("changeNetworkCallback$s").logE()
        RxBus.post(MessageEvent(handleResult(s), RxBusCenter.CHANGE_NODE))
    }*/

    fun handleResult(s: String): String {
        val resultBean = s.toResultBean()
        if (resultBean.code == 0) {
            val s1 = Gson().fromJson<ErrorMSG>(resultBean.message, ErrorMSG::class.java)
            if (LocalManageUtil.getSetLanguageLocale(App.getInstance()) == Locale.CHINA) {
                s1.cn.toast()
            } else {
                s1.en.toast()
            }
            return ""
        }
        return resultBean.data.toString()
    }

}