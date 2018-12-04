package com.qs.modulemain.ui.activity.manage

import com.qs.modulemain.R
import com.qs.modulemain.presenter.CreateGroupPresenter
import com.qs.modulemain.view.CreateGroupView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.extension.start
import kotlinx.android.synthetic.main.activity_create_group2.*

class CreateGroup2Activity : BaseActivity<CreateGroupPresenter>(), CreateGroupView {

    override fun initPresenter() {
        mPresenter = CreateGroupPresenter(mContext)
    }

    override val layoutId: Int = R.layout.activity_create_group2

    override fun initData() {
        tvTitle?.text = getString(R.string.create_group)
        tv_submit.setOnClickListener { start(EnterPubKeyActivity::class.java) }
    }


}
