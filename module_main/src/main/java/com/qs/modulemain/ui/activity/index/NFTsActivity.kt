package com.qs.modulemain.ui.activity.index

import com.qs.modulemain.R
import com.qs.modulemain.presenter.NFTsPresenter
import com.qs.modulemain.view.NFTsView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.extension.start
import kotlinx.android.synthetic.main.activity_nfts.*

class NFTsActivity : BaseActivity<NFTsPresenter>(), NFTsView {

    override fun initPresenter() {
        mPresenter = NFTsPresenter(mContext)
    }

    override val layoutId: Int = R.layout.activity_nfts

    override fun initData() {
        tvTitle?.text = getString(R.string.my_nfts)
        tv_add.setOnClickListener { start(NFTsCreateActivity::class.java) }
    }

}
