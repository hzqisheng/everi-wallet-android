package com.qs.modulemain.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.qs.modulemain.R
import com.qs.modulemain.arouter.ARouterCenter
import com.qs.modulemain.arouter.ARouterConfig
import com.qs.modulemain.ui.fragment.AssetsFragment
import com.qs.modulemain.ui.fragment.ManageFragment
import com.qs.modulemain.ui.fragment.MarketFragment
import com.qs.modulemain.ui.fragment.MyFragment
import com.smallcat.shenhai.mvpbase.App
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.utils.fitSystemAllScroll
import kotlinx.android.synthetic.main.activity_main.*
import me.yokeyword.fragmentation.ISupportFragment

@Route(path = ARouterConfig.MAIN_MAIN)
class MainActivity : SimpleActivity() {

    companion object {
        private const val ASSETS = 1
        private const val MARKET = 2
        private const val MANAGE = 3
        private const val MY = 4
    }

    private var show = ASSETS
    private var hide = ASSETS
    private var lastBackTime = 0L

    private lateinit var fg1: AssetsFragment
    private lateinit var fg2: MarketFragment
    private lateinit var fg3: ManageFragment
    private lateinit var fg4: MyFragment

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun fitSystem() {
        fitSystemAllScroll(this)
    }

    override fun initData() {
        rb_assets.isChecked = true
        rg_bottom.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.rb_assets -> show = ASSETS
                R.id.rb_market -> show = MARKET
                R.id.rb_manage -> show = MANAGE
                R.id.rb_my -> show = MY
            }
            showHideFragment(getFragment(show), getFragment(hide))
            hide = show
        }
        rl_wallet.setOnClickListener {
            ARouterCenter.goWalletActivity(0)
        }
        fg1 = AssetsFragment()
        fg2 = MarketFragment()
        fg3 = ManageFragment()
        fg4 = MyFragment()
        loadMultipleRootFragment(R.id.fl_contain, 0, fg1, fg2, fg3, fg4)
    }

    private fun getFragment(position: Int): ISupportFragment {
        when (position) {
            ASSETS -> return fg1
            MARKET -> return fg2
            MANAGE -> return fg3
            MY -> return fg4
        }
        return fg1
    }

    override fun onBackPressedSupport() {
        val currentBackTime = System.currentTimeMillis()
        if (currentBackTime - lastBackTime < 2000) {
            super.onBackPressed()
            App.getInstance().exitApp()
        } else {
            lastBackTime = currentBackTime
            "再按一次退出".toast()
        }
    }
}
