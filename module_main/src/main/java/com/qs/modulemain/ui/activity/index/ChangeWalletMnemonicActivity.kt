package com.qs.modulemain.ui.activity.index

import android.support.design.widget.TabLayout
import android.util.TypedValue
import android.widget.TextView
import com.qs.modulemain.R
import com.qs.modulemain.ui.adapter.WalletFragChangeAdapter
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.model.bean.BaseData
import kotlinx.android.synthetic.main.activity_change_wallet_mnemonic.*

class ChangeWalletMnemonicActivity : SimpleActivity() {

    private lateinit var mAdapter: WalletFragChangeAdapter
    override val layoutId: Int
        get() = R.layout.activity_change_wallet_mnemonic

    private lateinit var mWalletBean: BaseData

    override fun initData() {
        tvTitle?.text = getString(R.string.wallet_import)

        //获取数据
        mWalletBean = intent.getSerializableExtra("data") as BaseData

        mAdapter = WalletFragChangeAdapter(supportFragmentManager, mContext, mWalletBean)
        view_pager.adapter = mAdapter
        view_pager.offscreenPageLimit = 1
        tab_layout.setupWithViewPager(view_pager)

        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
                updateTabView(p0, false)
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                updateTabView(p0, true)
            }
        })
    }

    private fun updateTabView(tab: TabLayout.Tab?, isSelect: Boolean) {
        val tabView = tab?.customView?.findViewById<TextView>(R.id.tv_title)
        if (isSelect) {
            //选中后字体变大
            tabView?.isSelected = true
            tabView?.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.resources.getDimensionPixelSize(R.dimen.sp_16).toFloat())
        } else {
            //恢复为默认字体大小
            tabView?.isSelected = false
            tabView?.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.resources.getDimensionPixelSize(R.dimen.sp_14).toFloat())
        }
    }

}
