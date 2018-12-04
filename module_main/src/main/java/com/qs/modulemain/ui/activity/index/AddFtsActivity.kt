package com.qs.modulemain.ui.activity.index

import com.qs.modulemain.R
import com.qs.modulemain.presenter.AddFTsPresenter
import com.qs.modulemain.view.AddFTsView
import com.smallcat.shenhai.mvpbase.base.BaseActivity

class AddFtsActivity : BaseActivity<AddFTsPresenter>(), AddFTsView{

    override fun initPresenter() {
        mPresenter = AddFTsPresenter(mContext)
    }

    override val layoutId: Int = R.layout.activity_add_fts

    override fun initData() {
        tvTitle?.text = getString(R.string.CreateFts)
    }

}
