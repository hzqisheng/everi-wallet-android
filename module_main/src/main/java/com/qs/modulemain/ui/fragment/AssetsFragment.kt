package com.qs.modulemain.ui.fragment


import android.app.Dialog
import android.content.Intent
import android.support.design.widget.TabLayout
import android.text.TextUtils
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.qs.modulemain.R
import com.qs.modulemain.arouter.ARouterCenter
import com.qs.modulemain.bean.ChooseGetBean
import com.qs.modulemain.ui.activity.index.*
import com.qs.modulemain.ui.adapter.AssetsFragAdapter
import com.qs.modulemain.util.confirmPassword
import com.smallcat.shenhai.mvpbase.base.FingerSuccessCallback
import com.smallcat.shenhai.mvpbase.base.SimpleFragment
import com.smallcat.shenhai.mvpbase.extension.getResourceString
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.start
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.utils.addClipboard
import kotlinx.android.synthetic.main.activity_scan_collect.*
import kotlinx.android.synthetic.main.fragment_assets.*


class AssetsFragment : SimpleFragment(), View.OnClickListener {

    private lateinit var adapter: AssetsFragAdapter

    private var dialog:Dialog ?= null

    override val layoutId: Int
        get() = R.layout.fragment_assets

    override fun initData() {
        initListener()
        adapter = AssetsFragAdapter(fragmentManager, mContext)
        view_pager.adapter = adapter
        view_pager.offscreenPageLimit = 1
        tab_layout.setupWithViewPager(view_pager)

        public_asset_key.setOnClickListener {
            addClipboard(mContext,mContext.sharedPref.publicKey)
        }

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

    override fun onResume() {
        super.onResume()

        if(!TextUtils.isEmpty(mContext.sharedPref.name)) {
            tv_asset_name.text = mContext.sharedPref.name
        }

        public_asset_key.text = mContext.sharedPref.publicKey
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

    private fun showIssueDialog(){
        if (dialog == null) {
            dialog = Dialog(mContext, R.style.CustomDialog)
        }
        val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_issue, null)
        val ivFTs = view.findViewById<ImageView>(R.id.iv_issue_fts)
        val ivNFTs = view.findViewById<ImageView>(R.id.iv_issue_nfts)
        ivFTs.setOnClickListener {
            mContext.start(ChooseFTsActivity::class.java)
            dialog!!.dismiss()
        }
        ivNFTs.setOnClickListener{
            dialog!!.dismiss()
            mContext.start(NFTsActivity::class.java)
        }
        dialog!!.setContentView(view)
        dialog!!.setCanceledOnTouchOutside(false)
        dialog!!.setCancelable(true)
        dialog!!.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (dialog != null && dialog!!.isShowing){
            dialog!!.dismiss()
        }
    }

    private fun showFingerPrintDialog(item: ChooseGetBean?) {
        confirmPassword(mContext.sharedPref.isFinger, childFragmentManager, object : FingerSuccessCallback() {
            override fun onCheckSuccess() {
                if (item == null){
                    getString(R.string.no_fts).toast()
                    return
                }
                val intent = Intent(mContext, PayActivity::class.java)
                intent.putExtra("data",item)
                mContext.startActivity(intent)
            }
        })
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_scan ->{
                val intent = Intent(mContext, ScanActivity::class.java)
                intent.putExtra("ScanType", 10001)
                startActivity(intent)
            }
            R.id.tv_pay -> showFingerPrintDialog(AssetsItemFragment.firstBean)
            R.id.tv_receive -> mContext.start(CollectActivity::class.java)
            R.id.tv_publish -> showIssueDialog()
        }
    }
}
