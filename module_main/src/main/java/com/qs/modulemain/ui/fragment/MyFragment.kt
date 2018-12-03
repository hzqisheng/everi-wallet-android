package com.qs.modulemain.ui.fragment


import android.view.View
import com.qs.modulemain.R
import com.qs.modulemain.arouter.ARouterCenter
import com.qs.modulemain.presenter.MyPresenter
import com.qs.modulemain.ui.activity.my.HelpCenterActivity
import com.qs.modulemain.ui.activity.my.JoinCommunitiesActivity
import com.qs.modulemain.ui.activity.my.SettingActivity
import com.qs.modulemain.view.MyView
import com.smallcat.shenhai.mvpbase.base.BaseFragment
import com.smallcat.shenhai.mvpbase.extension.start
import kotlinx.android.synthetic.main.fragment_my.*


class MyFragment : BaseFragment<MyPresenter>(), MyView, View.OnClickListener {

    override fun initPresenter() {
        mPresenter = MyPresenter(mContext)
    }

    override val layoutId: Int
        get() = R.layout.fragment_my

    override fun initData() {
        tv_wallet.setOnClickListener(this)
        tv_address.setOnClickListener(this)
        tv_add_community.setOnClickListener(this)
        tv_help.setOnClickListener(this)
        tv_setting.setOnClickListener(this)
        tv_share.setOnClickListener(this)
        tv_about_us.setOnClickListener(this)
    }

    override fun loadSuccess(data: Any) {
    }

    override fun loginOut() {
    }

    /**
     * Called when a view has been clicked.
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_wallet -> ARouterCenter.goWalletActivity(1)
            R.id.tv_address -> ARouterCenter.goAddressManageActivity()
            R.id.tv_add_community -> mContext.start(JoinCommunitiesActivity::class.java)
            R.id.tv_setting -> mContext.start(SettingActivity::class.java)
            R.id.tv_help -> mContext.start(HelpCenterActivity::class.java)
        }
    }

}
