package com.qs.modulemain.ui.activity.index

import com.qs.modulemain.R
import com.qs.modulemain.presenter.ChangePwdPresenter
import com.qs.modulemain.view.ChangePwdView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.extension.start
import kotlinx.android.synthetic.main.activity_change_pwd.*

class ChangePwdActivity : BaseActivity<ChangePwdPresenter>(), ChangePwdView {

    override fun initPresenter() {
        mPresenter = ChangePwdPresenter(mContext)
    }

    override val layoutId: Int
        get() = R.layout.activity_change_pwd

    override fun initData() {
        tvTitle?.text = getString(R.string.change_pwd)
        tv_import.setOnClickListener { start(RetrievePwdActivity::class.java) }
        tv_sure.setOnClickListener { finish() }

    }

}
