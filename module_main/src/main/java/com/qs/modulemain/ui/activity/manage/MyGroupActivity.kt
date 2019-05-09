package com.qs.modulemain.ui.activity.manage

import com.qs.modulemain.R
import com.qs.modulemain.presenter.MyGroupPresenter
import com.qs.modulemain.view.MyGroupView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.extension.start
import kotlinx.android.synthetic.main.activity_my_group.*

class MyGroupActivity : BaseActivity<MyGroupPresenter>(), MyGroupView {

    override fun initPresenter() {
        mPresenter = MyGroupPresenter(mContext)
    }

    override val layoutId: Int = R.layout.activity_my_group

    override fun initData() {
        tvTitle?.text = getString(R.string.my_groups)
        tv_add.setOnClickListener { start(CreateGroupActivity::class.java) }
    }

}
