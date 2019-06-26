package com.qs.modulemain.ui.fragment


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.qs.modulemain.R
import com.qs.modulemain.bean.ChooseGetBean
import com.qs.modulemain.presenter.AssetsItemPresenter
import com.qs.modulemain.ui.activity.index.ExportPrivateKeyActivity
import com.qs.modulemain.ui.activity.index.NFTsIssueActivity
import com.qs.modulemain.ui.activity.index.PayActivity
import com.qs.modulemain.ui.activity.index.RecordActivity
import com.qs.modulemain.ui.adapter.AssetsItemAdapter
import com.qs.modulemain.ui.adapter.AssetsNFtsAdapter
import com.qs.modulemain.util.DataUtils
import com.qs.modulemain.util.confirmPassword
import com.qs.modulemain.view.AssetsItemView
import com.smallcat.shenhai.mvpbase.base.BaseFragment
import com.smallcat.shenhai.mvpbase.base.FingerSuccessCallback
import com.smallcat.shenhai.mvpbase.extension.logE
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.start
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import kotlinx.android.synthetic.main.fragment_assets_item.*
import java.lang.Exception
import java.text.FieldPosition
import java.util.*


class AssetsItemFragment : BaseFragment<AssetsItemPresenter>(), AssetsItemView {

    private var type = -1
    private var ftsList = ArrayList<ChooseGetBean>()

    private lateinit var ftsAdapter: AssetsItemAdapter

    override fun initPresenter() {
        mPresenter = AssetsItemPresenter(mContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        type = 0
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && type != -1) {
            mWebView.evaluateJavascript(WebViewApi.getEVTFungibleBalanceList(mContext.sharedPref.publicKey)) {}
        }
    }

    override fun onResume() {
        super.onResume()
        mWebView.evaluateJavascript(WebViewApi.getEVTFungibleBalanceList(mContext.sharedPref.publicKey)) {}
    }

    override val layoutId: Int = R.layout.fragment_assets_item

    override fun initData() {
        ftsAdapter = AssetsItemAdapter(ftsList)

        val myAssets = mContext.sharedPref.myAssets
        if (!"".equals(myAssets)) {
            val chooseBean = Gson().fromJson<java.util.ArrayList<ChooseGetBean>>(myAssets, object : TypeToken<java.util.ArrayList<ChooseGetBean>>() {}.type)
            if (chooseBean.isNotEmpty()) {
                firstBean = chooseBean[0]
            } else {
                firstBean = ChooseGetBean()
            }
            ftsList.clear()
            ftsList.addAll(chooseBean)
        }

        rv_list.adapter = ftsAdapter

        ftsAdapter.setOnItemClickListener { _, _, position ->
            val intent = Intent(context, RecordActivity::class.java)
            intent.putExtra("data", ftsList[position])
            startActivity(intent)
        }

        ftsAdapter.emptyView = DataUtils.getEmptyView(mContext, getString(R.string.no_fts_now))

        ftsAdapter.setOnItemChildClickListener { _, _, position ->
            showFingerPrintDialog(position)
        }

        swipe_refresh.setOnRefreshListener {
            mWebView.evaluateJavascript(WebViewApi.getEVTFungibleBalanceList(mContext.sharedPref.publicKey)) {}
        }
    }

    private fun showFingerPrintDialog(position: Int) {
        confirmPassword(mContext.sharedPref.isFinger, childFragmentManager, object : FingerSuccessCallback() {
            override fun onCheckSuccess() {
                val intent = Intent(mContext, PayActivity::class.java)
                intent.putExtra("data", ftsList[position])
                mContext.startActivity(intent)
            }
        })
    }

    companion object {
        var firstBean: ChooseGetBean? = null
    }

    override fun loadFTsSuccess(msg: String) {
        swipe_refresh.isRefreshing = false
        if (msg.isEmpty()) return
        var chooseBean = ArrayList<ChooseGetBean>()
        try {
            chooseBean = Gson().fromJson<java.util.ArrayList<ChooseGetBean>>(msg, object : TypeToken<java.util.ArrayList<ChooseGetBean>>() {}.type)
            if (chooseBean.isNotEmpty()) {
                firstBean = chooseBean[0]
            } else {
                firstBean = ChooseGetBean()
            }
            mContext.sharedPref.myAssets = msg
        } catch (e: Exception) {
            e.printStackTrace()
            "数据为空".logE()
        }

        ftsList.clear()
        ftsList.addAll(chooseBean)
        ftsAdapter.notifyDataSetChanged()
    }

    override fun loadFTsError(msg: String) {
        swipe_refresh.isRefreshing = false
        val myAssets = mContext.sharedPref.myAssets
        if (!"".equals(myAssets)) {
            val chooseBean = Gson().fromJson<java.util.ArrayList<ChooseGetBean>>(myAssets, object : TypeToken<java.util.ArrayList<ChooseGetBean>>() {}.type)
            firstBean = chooseBean[0]
            ftsList.clear()
            ftsList.addAll(chooseBean)
            ftsAdapter.notifyDataSetChanged()
        } else {
            ftsList.clear()
            ftsAdapter.notifyDataSetChanged()
        }
    }

}
