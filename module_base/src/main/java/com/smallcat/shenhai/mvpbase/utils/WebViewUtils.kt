package com.smallcat.shenhai.mvpbase.utils

import android.annotation.SuppressLint
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.google.gson.Gson
import com.smallcat.shenhai.mvpbase.App
import com.smallcat.shenhai.mvpbase.extension.logE
import com.smallcat.shenhai.mvpbase.extension.toResultBean
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.model.bean.ErrorMSG
import com.smallcat.shenhai.mvpbase.model.helper.MessageEvent
import com.smallcat.shenhai.mvpbase.model.helper.RxBus
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import com.smallcat.shenhai.mvpbase.model.widget.ProgressWebView
import java.util.*

@SuppressLint("StaticFieldLeak")
private var webView: WebView? = null

fun initWebView() {
    webView = ProgressWebView(App.getInstance())
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
        val s1 = handleResult(s) ?: return
        RxBus.post(MessageEvent(s1, RxBusCenter.CREATE_WALL))
    }

    @JavascriptInterface
    fun getOwnedTokensCallback(s: String) {
        "getOwnedTokensCallback".logE()
        s.logE()
        val s1 = handleResult(s) ?: return
        RxBus.post(MessageEvent(s1, lastMY_NFTS))
    }

    @JavascriptInterface
    fun getFungibleBalanceCallback(s: String) {
        "getFungibleBalanceCallback".logE()
        s.logE()
        val s1 = handleResult(s) ?: return
        RxBus.post(MessageEvent(s1, RxBusCenter.MY_FTS))
    }

    @JavascriptInterface
    fun importEVTWalletCallback(s: String) {
        "importEVTWalletCallback".logE()
        s.logE()
        val s1 = handleResult(s) ?: return
        RxBus.post(MessageEvent(s1, RxBusCenter.LOGIN))
    }

    @JavascriptInterface
    fun needPrivateKey(s: String) {
        "needPrivateKey".logE()
        val s1 = handleResult(s) ?: return
        RxBus.post(MessageEvent(s1, RxBusCenter.NEED_PRIVATE_KEY))
    }

    @JavascriptInterface
    fun getCreatedFungiblesCallback(s: String) {
        "getCreatedFungiblesCallback".logE()
        s.logE()
        val s1 = handleResult(s) ?: return
        RxBus.post(MessageEvent(s1, RxBusCenter.CHOOSE_FTS))
    }

    @JavascriptInterface
    fun pushTransactionCallback(s: String) {
        ("pushTransactionCallback()$s").logE()
        val s1 = handleResult(s) ?: return
        RxBus.post(MessageEvent(s1, lastPushTransaction))
    }

    @JavascriptInterface
    fun getEVTFungiblesListCallback(s: String) {
        ("getEVTFungiblesListCallback()  $s").logE()
        val s1 = handleResult(s) ?: return
        RxBus.post(MessageEvent(s1, RxBusCenter.CHOOSE_FTS))
    }

    @JavascriptInterface
    fun getEVTDomainsListCallback(s: String) {
        ("getEVTDomainsListCallback()  $s").logE()
        val s1 = handleResult(s) ?: return
        RxBus.post(MessageEvent(s1, RxBusCenter.MY_DOMAIN))
    }

    /** 收付款二维码 **/
    @JavascriptInterface
    fun getEVTLinkQrImageCallback(s: String) {
        ("getEVTLinkQrImageCallback()  $s").logE()
        val s1 = handleResult(s) ?: return
        RxBus.post(MessageEvent(s1, qrcode_type))
    }

    /** 扫描二维码 **/
    @JavascriptInterface
    fun parseEvtLinkCallback(s: String) {
        ("parseEvtLinkCallback()  $s").logE()
        val s1 = handleResult(s) ?: return
        RxBus.post(MessageEvent(s1, qrcode_type))
    }

    @JavascriptInterface
    fun getUniqueLinkIdCallback(s: String) {
        ("getUniqueLinkId()  $s").logE()
        val s1 = handleResult(s) ?: return
        RxBus.post(MessageEvent(s1, RxBusCenter.GET_LINkID))
    }

    @JavascriptInterface
    fun getActionsCallback(s: String) {
        ("getActionsCallback()  $s").logE()
        val s1 = handleResult(s) ?: return
        RxBus.post(MessageEvent(s1, RxBusCenter.RECORD_TRANSACATION))
    }

    @JavascriptInterface
    fun getEVTFungibleBalanceListCallback(s: String) {
        ("getEVTFungibleBalanceListCallback$s").logE()
        val s1 = handleResult(s) ?: return
        RxBus.post(MessageEvent(s1, RxBusCenter.HOME_FTS))
    }

    @JavascriptInterface
    fun getFungibleActionsByAddressCallback(s: String) {
        ("getFungibleActionsByAddressCallback$s").logE()
        val s1 = handleResult(s) ?: return
        RxBus.post(MessageEvent(s1, RxBusCenter.RECORD_TRANSACATION))
    }

    @JavascriptInterface
    fun getEstimatedChargeForTransactionCallback(s: String) {
        ("getEstimatedChargeForTransactionCallback$s").logE()
        val s1 = handleResult(s) ?: return
        RxBus.post(MessageEvent(s1, RxBusCenter.SERVICE_CHARGE))
    }

    @JavascriptInterface
    fun privateToPublicCallback(s: String) {
        ("privateToPublicCallback$s").logE()
        val s1 = handleResult(s) ?: return
        RxBus.post(MessageEvent(s1, RxBusCenter.PRIVATE_TO_PUBLIC))
    }

    @JavascriptInterface
    fun getFungibleSymbolDetailCallback(s: String) {
        ("getFungibleSymbolDetailCallback$s").logE()
        val s1 = handleResult(s) ?: return
        RxBus.post(MessageEvent(s1, RxBusCenter.SYMBOL_DETAIL))
    }

    /**
     * 交易结果放回
     */
    @JavascriptInterface
    fun getStatusOfEvtLinkCallback(s: String) {
        ("getStatusOfEvtLinkCallback$s").logE()
        val s1 = handleResult(s) ?: return
        RxBus.post(MessageEvent(s1, RxBusCenter.PAY_RECORD))
    }

    /**
     * 验证助记词
     */
    @JavascriptInterface
    fun validateMnemonicCallback(s: String) {
        ("validateMnemonicCallback$s").logE()
        val s1 = handleResult(s) ?: return
        RxBus.post(MessageEvent(s1, RxBusCenter.CHECK_MEMO))
    }

    /**
     * 验证私钥
     */
    @JavascriptInterface
    fun isValidPrivateKeyCallback(s: String) {
        ("isValidPrivateKey$s").logE()
        val s1 = handleResult(s) ?: return
        RxBus.post(MessageEvent(s1, RxBusCenter.CHECK_PRIVATE))
    }

    /**
     * 验证地址
     */
    @JavascriptInterface
    fun isValidPublicKeyCallback(s: String) {
        ("isValidPublicKey$s").logE()
        val s1 = handleResult(s) ?: return
        RxBus.post(MessageEvent(s1, RxBusCenter.CHECK_ADDRESS))
    }

    @JavascriptInterface
    fun getTransactionDetailByIdCallback(s: String) {
        ("getTransactionDetailByIdCallback$s").logE()
        val s1 = handleResult(s) ?: return
        RxBus.post(MessageEvent(s1, RxBusCenter.TRAN_MSG))
    }

    @JavascriptInterface
    fun getAPPVersionCallback(s: String) {
        ("getAPPVersionCallback$s").logE()
        val s1 = handleResult(s) ?: return
        RxBus.post(MessageEvent(s1, RxBusCenter.CHECK_VERSION))
    }

    fun handleResult(s: String): String? {
        val resultBean = s.toResultBean()
        if (resultBean.code == 0) {
            val s1 = Gson().fromJson<ErrorMSG>(resultBean.message, ErrorMSG::class.java)
            if (LocalManageUtil.getSetLanguageLocale(App.getInstance()) == Locale.CHINA) {
                s1.cn.toast()
            } else {
                s1.en.toast()
            }
            return null
        }
        return resultBean.data.toString()
    }

    //获取资产编号
    @JavascriptInterface
    fun randomValidSymbolIdCallback(s: String) {
        ("randomValidSymbolId$s").logE()
        val s1 = handleResult(s) ?: return
        RxBus.post(MessageEvent(s1, RxBusCenter.RANDOM_VALID_SYMID))
    }

}