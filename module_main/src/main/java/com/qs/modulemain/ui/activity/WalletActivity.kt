package com.qs.modulemain.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.qs.modulemain.R
import com.qs.modulemain.arouter.ARouterConfig
import com.qs.modulemain.presenter.WalletPresenter
import com.qs.modulemain.ui.adapter.WalletAdapter
import com.qs.modulemain.view.WalletView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.extension.getResourceString
import kotlinx.android.synthetic.main.activity_wallet.*

@Route(path = ARouterConfig.MAIN_WALLET)
class WalletActivity : BaseActivity<WalletPresenter>(), WalletView {

    private lateinit var adapterNow: WalletAdapter
    private var nowList = ArrayList<String>()

    override fun initPresenter() {
        mPresenter = WalletPresenter(mContext)
    }

    override val layoutId: Int
        get() = R.layout.activity_wallet

    override fun initData() {
        tvTitle?.text = getResourceString(R.string.wallet)
        nowList.add("")
        adapterNow = WalletAdapter(nowList)
        adapterNow.setOnItemChildClickListener { adapter, view, position ->  }
        adapterNow.setOnItemClickListener { adapter, view, position ->  finish() }
        rv_now.adapter = adapterNow
        rv_now.isNestedScrollingEnabled = false
    }

    override fun loadSuccess(data: Any) {
    }

}
