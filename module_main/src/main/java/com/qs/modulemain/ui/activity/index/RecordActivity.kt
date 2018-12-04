package com.qs.modulemain.ui.activity.index

import com.qs.modulemain.R
import com.qs.modulemain.presenter.RecordPresenter
import com.qs.modulemain.view.RecordView
import com.smallcat.shenhai.mvpbase.base.BaseActivity

class RecordActivity : BaseActivity<RecordPresenter>(), RecordView {

    override fun initPresenter() {
        mPresenter = RecordPresenter(mContext)
    }

    override val layoutId: Int = R.layout.activity_record

    override fun initData() {

    }
}
