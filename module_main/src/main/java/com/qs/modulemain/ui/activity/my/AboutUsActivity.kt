package com.qs.modulemain.ui.activity.my

import com.qs.modulemain.R
import com.qs.modulemain.presenter.AboutUsPresenter
import com.qs.modulemain.view.AboutUsView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.utils.getVersionName
import kotlinx.android.synthetic.main.activity_about_us.*

class AboutUsActivity : BaseActivity<AboutUsPresenter>(), AboutUsView {

    override fun initPresenter() {
        mPresenter = AboutUsPresenter(mContext)
    }

    override val layoutId: Int
        get() = R.layout.activity_about_us

    override fun initData() {
        tvTitle?.text = getString(R.string.about_us)
        tv_version.text = getVersionName(mContext)
    }
}
