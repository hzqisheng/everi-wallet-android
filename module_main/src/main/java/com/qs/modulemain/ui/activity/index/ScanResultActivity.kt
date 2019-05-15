package com.qs.modulemain.ui.activity.index

import com.qs.modulemain.R
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.utils.addClipboard
import kotlinx.android.synthetic.main.activity_scan_result.*

class ScanResultActivity : SimpleActivity() {

    private var scanResult: String = ""

    override val layoutId: Int
        get() = R.layout.activity_scan_result

    override fun initData() {
        tvTitle?.text = getString(R.string.scan_result)
        scanResult = intent.getStringExtra("scanResult")
        tv_result.text = scanResult
        tv_copy.setOnClickListener {
            if (scanResult == "") {
                return@setOnClickListener
            }
            addClipboard(this@ScanResultActivity, scanResult)
            getString(R.string.copy_success).toast()
        }
    }
}
