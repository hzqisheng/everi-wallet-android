package com.qs.modulemain.ui.activity.index

import com.qs.modulemain.R
import com.qs.modulemain.presenter.SetUpSignPresenter
import com.qs.modulemain.view.SetUpsignView
import com.smallcat.shenhai.mvpbase.base.BaseActivity

class SetUpSignActivity : BaseActivity<SetUpSignPresenter>(), SetUpsignView{

    override fun initPresenter() {
        mPresenter = SetUpSignPresenter(mContext)
    }

    override val layoutId: Int = R.layout.activity_set_up_sign

    override fun initData() {
        tvTitle?.text = getString(R.string.export_key)
    }

    override fun loadSuccess(data: Any) {
    }

}
