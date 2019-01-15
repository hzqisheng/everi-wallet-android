package com.qs.modulemain.arouter

import com.alibaba.android.arouter.launcher.ARouter


/**
 * Created by hui on 2018/8/1.
 */
object ARouterCenter {

    /**
     * 跳转主页
     * mainActivity
     */
    fun goMainActivity() {
        ARouter.getInstance().build(ARouterConfig.MAIN_MAIN).navigation()
    }

    /**
     * 跳转扫一扫页面
     * ScanActivity
     */
    fun goScanActivity() {
        ARouter.getInstance().build(ARouterConfig.ASSETS_SCAN).navigation()
    }

    /**
     * 跳转钱包页面
     */
    fun goWalletActivity(type: Int) {
        ARouter.getInstance().build(ARouterConfig.MAIN_WALLET).withInt("type", type).navigation()
    }

    /**
     * 挑转地址管理
     */
    fun goAddressManageActivity() {
        ARouter.getInstance().build(ARouterConfig.MY_ADDRESS).navigation()
    }

    fun goWebViewActivity(url: String) {
        ARouter.getInstance()
                .build(ARouterConfig.WEB_ACTIVITY)
                .withString("url", url)
                .navigation()
    }
}