package com.qs.modulemain.ui.activity.index

import com.qs.modulemain.R
import com.qs.modulemain.presenter.PayDetailPresenter
import com.qs.modulemain.ui.activity.MainActivity
import com.qs.modulemain.view.PayDetailView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.extension.start
import com.smallcat.shenhai.mvpbase.utils.fitSystemAllScroll
import kotlinx.android.synthetic.main.activity_pay_detail.*

class PayDetailActivity : BaseActivity<PayDetailPresenter>(), PayDetailView {

    override fun initPresenter() {
        mPresenter = PayDetailPresenter(mContext)
    }

    override val layoutId: Int = R.layout.activity_pay_detail

    override fun fitSystem() {
        fitSystemAllScroll(this)
    }

    override fun initData() {
        iv_back.setOnClickListener { start(MainActivity::class.java) }

    }

}
