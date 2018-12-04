package com.qs.modulemain.ui.activity.index

import com.qs.modulemain.R
import com.qs.modulemain.presenter.ChooseFTsPresenter
import com.qs.modulemain.view.ChooseFTsView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.extension.start
import kotlinx.android.synthetic.main.activity_choose_fts.*

class ChooseFTsActivity : BaseActivity<ChooseFTsPresenter>(), ChooseFTsView{

    override fun initPresenter() {
        mPresenter = ChooseFTsPresenter(mContext)
    }

    override val layoutId: Int = R.layout.activity_choose_fts

    override fun initData() {
        tvTitle?.text = getString(R.string.choose_fts)
        tv_sure.setOnClickListener { start(AddFtsActivity::class.java) }
    }

}
