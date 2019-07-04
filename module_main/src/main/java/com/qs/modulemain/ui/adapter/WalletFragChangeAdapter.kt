package com.qs.modulemain.ui.adapter

import android.content.Context
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.qs.modulemain.R
import com.qs.modulemain.ui.fragment.*
import com.smallcat.shenhai.mvpbase.model.bean.BaseData

/**
 * Created by hui on 2018/5/9.
 */
class WalletFragChangeAdapter(fm: FragmentManager?, mContext: Context, private val mWalletBean: BaseData) : FragmentPagerAdapter(fm) {

    private val tabTitles = listOf(mContext.getString(R.string.mnemonic), mContext.getString(R.string.private_key))
    private val img = listOf(R.drawable.selector_token, R.drawable.selector_pass_code)

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return WalletMnemonicChangeFragment.newInstance(mWalletBean)
            1 -> return WalletPrivateKeyChangeFragment.newInstance(mWalletBean)
        }
        return WalletMnemonicFragment()
    }

    override fun getCount(): Int {
        return tabTitles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }


    fun getTabView(position: Int, tab: TabLayout, mContext: Context): View {
        val v = LayoutInflater.from(mContext).inflate(R.layout.main_tab, null)
        val tvTitle = v.findViewById<TextView>(R.id.tv_title)
        val ivTab = v.findViewById<ImageView>(R.id.iv_icon)
        tvTitle.text = tabTitles[position]
        tvTitle.setTextColor(tab.tabTextColors)
        ivTab.setImageResource(img[position])
        return v
    }
}