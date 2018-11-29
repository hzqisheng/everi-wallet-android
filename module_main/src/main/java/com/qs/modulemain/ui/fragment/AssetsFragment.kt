package com.qs.modulemain.ui.fragment


import android.support.design.widget.TabLayout
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import com.qs.modulemain.R
import com.qs.modulemain.arouter.ARouterCenter
import com.qs.modulemain.ui.activity.my.CollectActivity
import com.qs.modulemain.ui.activity.PayActivity
import com.qs.modulemain.ui.adapter.AssetsFragAdapter
import com.smallcat.shenhai.mvpbase.base.SimpleFragment
import com.smallcat.shenhai.mvpbase.extension.start
import kotlinx.android.synthetic.main.fragment_assets.*


class AssetsFragment : SimpleFragment(), View.OnClickListener {

    private lateinit var adapter: AssetsFragAdapter

    override val layoutId: Int
        get() = R.layout.fragment_assets

    override fun initData() {
        initListener()
        adapter = AssetsFragAdapter(fragmentManager, mContext)
        view_pager.adapter = adapter
        view_pager.offscreenPageLimit = 1
        tab_layout.setupWithViewPager(view_pager)

        for (i in 0..1) {
            val tabItem = tab_layout.getTabAt(i)
            tabItem!!.customView = adapter.getTabView(i, tab_layout, mContext)
        }

        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
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

    private fun initListener(){
        tv_scan.setOnClickListener(this)
        tv_pay.setOnClickListener(this)
        tv_publish.setOnClickListener(this)
        tv_receive.setOnClickListener(this)
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

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_scan -> ARouterCenter.goScanActivity()
            R.id.tv_pay -> mContext.start(PayActivity::class.java)
            R.id.tv_receive -> mContext.start(CollectActivity::class.java)
        }
    }
}
