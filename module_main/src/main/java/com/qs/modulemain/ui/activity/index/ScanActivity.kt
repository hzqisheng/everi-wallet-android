package com.qs.modulemain.ui.activity.index

import android.Manifest
import android.annotation.SuppressLint
import com.alibaba.android.arouter.facade.annotation.Route
import com.qs.modulemain.R
import com.qs.modulemain.arouter.ARouterConfig
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.getResourceString
import com.smallcat.shenhai.mvpbase.extension.toast
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_scan.*

@Route(path = ARouterConfig.ASSETS_SCAN)
class ScanActivity : SimpleActivity() {

    override val layoutId: Int
        get() = R.layout.activity_scan

    @SuppressLint("CheckResult")
    override fun initData() {
        tvTitle?.text = getString(R.string.scan)
        val rxPermissions = RxPermissions(this)
        rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe { granted ->
                    if (granted!!) { // Always true pre-M
                       zxingview.startCamera()
                    } else {
                        getString(R.string.need_camerl_permission).toast()
                    }
                }
    }

}