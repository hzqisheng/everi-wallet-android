package com.qs.modulemain.ui.activity.index

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.qs.modulemain.R
import com.qs.modulemain.presenter.ScanPayPresenter
import com.qs.modulemain.view.ScanPayView
import com.smallcat.shenhai.mvpbase.base.BaseActivity

/**
 * scan result
 * pay money
 */
class ScanPayActivity : BaseActivity<ScanPayPresenter>(), ScanPayView {

    override fun initPresenter() {
        mPresenter = ScanPayPresenter(mContext)
    }

    override val layoutId: Int = R.layout.activity_scan_pay

    override fun initData() {
    }

}
