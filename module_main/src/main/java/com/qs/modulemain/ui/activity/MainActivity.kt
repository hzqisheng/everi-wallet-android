package com.qs.modulemain.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.google.gson.Gson
import com.qs.modulemain.R
import com.qs.modulemain.arouter.ARouterCenter
import com.qs.modulemain.arouter.ARouterConfig
import com.qs.modulemain.presenter.MainPresenter
import com.qs.modulemain.ui.fragment.AssetsFragment
import com.qs.modulemain.ui.fragment.ManageFragment
import com.qs.modulemain.ui.fragment.MarketFragment
import com.qs.modulemain.ui.fragment.MyFragment
import com.qs.modulemain.view.MainView
import com.smallcat.shenhai.mvpbase.App
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.bean.BaseData
import com.smallcat.shenhai.mvpbase.utils.LocalManageUtil
import com.smallcat.shenhai.mvpbase.utils.destroyWebView
import com.smallcat.shenhai.mvpbase.utils.fitSystemAllScroll
import kotlinx.android.synthetic.main.activity_main.*
import me.yokeyword.fragmentation.ISupportFragment
import java.util.*

@Route(path = ARouterConfig.MAIN_MAIN)
class MainActivity : BaseActivity<MainPresenter>(),MainView {

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

    override fun initPresenter() {
        mPresenter = MainPresenter(mContext)
    }

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun fitSystem() {
        fitSystemAllScroll(this)
    }

    override fun initData() {

//        if(sharedPref.publicKey.isEmpty()){
//            var bean = BaseData()
//            bean.name = "EVT-wallet"
//            bean.mnemoinc ="into grocery arrive trend alien flee trial cinnamon weird angle pen refuse"
//            bean.password = "123456"
//            bean.privateKey = "5JrNgyyNDqz2pikijgdJwUktV8xkS7JPPSURr2YwxkhKPzm2eRi"
//            bean.publicKey = "EVT5qn48E8eZKJb5yM24bgC1m8MdRFg5eBU76cQfDXBGXr3UYjLvY"
//            bean.type = "EVT"
//            bean.isSelect = 0;
//            bean.isCreate = 0;
//            bean.save()
//        }
//
//        sharedPref.publicKey = "EVT5qn48E8eZKJb5yM24bgC1m8MdRFg5eBU76cQfDXBGXr3UYjLvY"
//        sharedPref.privateKey = "5JrNgyyNDqz2pikijgdJwUktV8xkS7JPPSURr2YwxkhKPzm2eRi"
//        sharedPref.password = "123456"
//        sharedPref.mnemoinc = "into grocery arrive trend alien flee trial cinnamon weird angle pen refuse"
//        sharedPref.name= "EVT-wallet"



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
            ARouterCenter.goWalletActivity(1)
        }
        fg1 = AssetsFragment()
        fg2 = MarketFragment()
        fg3 = ManageFragment()
        fg4 = MyFragment()
        loadMultipleRootFragment(R.id.fl_contain, 0, fg1, fg2, fg3, fg4)
//        mWebView.evaluateJavascript(WebViewApi.createEVTWallet("123456")) {}

        if(LocalManageUtil.getSetLanguageLocale(this) == Locale.CHINA){
            cent_icon.background = getDrawable(R.drawable.ic_home_wallet)
        }else{
            cent_icon.background = getDrawable(R.drawable.switch_en)
        }
    }

    override fun loginSuccess(data: String) {
//        val bean = Gson().fromJson(data, BaseData::class.java)
//        bean.save()
//        sharedPref.publicKey = "EVT5qn48E8eZKJb5yM24bgC1m8MdRFg5eBU76cQfDXBGXr3UYjLvY"
//        sharedPref.privateKey = bean.privateKey
//        sharedPref.password = bean.password
//        sharedPref.mnemoinc = bean.mnemoinc
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
            destroyWebView()
            App.getInstance().exitApp()
        } else {
            lastBackTime = currentBackTime
            "再按一次退出".toast()
        }
    }
}
