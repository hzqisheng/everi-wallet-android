package com.qs.modulemain.ui.activity.index

import android.content.Intent
import com.alibaba.android.arouter.facade.annotation.Route
import com.qs.modulemain.R
import com.qs.modulemain.arouter.ARouterConfig
import com.qs.modulemain.presenter.WalletPresenter
import com.qs.modulemain.ui.adapter.WalletAdapter
import com.qs.modulemain.view.WalletView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.extension.start
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
        val type = intent.getIntExtra("type", 0)
        tvTitle?.text = getString(R.string.wallet)
        iv_add_now.setOnClickListener { start(EditIdWalletActivity::class.java) }
        iv_add_import.setOnClickListener { start(ImportWalletActivity::class.java) }
        nowList.add("")
        adapterNow = WalletAdapter(nowList)
        adapterNow.setOnItemChildClickListener { adapter, view, position ->
            Intent(this, WalletDetailActivity::class.java).apply {
                putExtra("name", "evellt")
                startActivity(this)
            }
        }
        adapterNow.setOnItemClickListener { adapter, view, position ->
            if (type == 1){
                Intent(this, WalletDetailActivity::class.java).apply {
                    putExtra("name", "evellt")
                    startActivity(this)
                }
            }else{
                finish()
            }
        }
        rv_now.adapter = adapterNow
        rv_now.isNestedScrollingEnabled = false
    }

    override fun loadSuccess(data: Any) {
    }

}
