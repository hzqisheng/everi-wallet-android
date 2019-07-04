package com.qs.modulemain.ui.fragment


import android.content.Intent
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.qs.modulemain.R
import com.qs.modulemain.bean.ChooseGetBean
import com.qs.modulemain.presenter.AssetsItemNFTsPresenter
import com.qs.modulemain.ui.activity.index.NFTsDetailActivity
import com.qs.modulemain.ui.activity.index.NFTsPayActivity
import com.qs.modulemain.ui.adapter.AssetsNFtsAdapter
import com.qs.modulemain.util.DataUtils
import com.qs.modulemain.util.confirmPassword
import com.qs.modulemain.view.AssetsItemNFTsView
import com.smallcat.shenhai.mvpbase.base.BaseFragment
import com.smallcat.shenhai.mvpbase.base.FingerSuccessCallback
import com.smallcat.shenhai.mvpbase.extension.logE
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import com.smallcat.shenhai.mvpbase.utils.lastMY_NFTS
import kotlinx.android.synthetic.main.fragment_assets_item.*
import java.lang.Exception


class AssetsItemNFTsFragment : BaseFragment<AssetsItemNFTsPresenter>(), AssetsItemNFTsView {

    private var nFTsList = ArrayList<ChooseGetBean>()

    private var nFTsAdapter: AssetsNFtsAdapter? = null

    override fun initPresenter() {
        mPresenter = AssetsItemNFTsPresenter(mContext)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser /*&& nFTsAdapter != null*/) {
            lastMY_NFTS = RxBusCenter.MY_NFTS
            mWebView.evaluateJavascript(WebViewApi.getOwnedTokens(mContext.sharedPref.publicKey), null)
        }
    }

    override val layoutId: Int = R.layout.fragment_assets_item

    override fun onResume() {
        super.onResume()
        mWebView.evaluateJavascript(WebViewApi.getOwnedTokens(mContext.sharedPref.publicKey), null)
    }

    override fun initData() {
        lastMY_NFTS = RxBusCenter.MY_NFTS
        nFTsAdapter = AssetsNFtsAdapter(nFTsList)
        nFTsAdapter?.setOnItemClickListener { _, _, position ->
            Intent(mActivity, NFTsDetailActivity::class.java).apply {
                putExtra("domain", nFTsList[position].domain)
                putExtra("token", nFTsList[position].name)
                startActivity(this)
            }
        }
        nFTsAdapter?.setOnItemChildClickListener { _, _, position ->
            showFingerPrintDialog(position)
        }
        nFTsAdapter?.emptyView = DataUtils.getEmptyView(mContext, getString(R.string.no_nfts_now))
        rv_list.adapter = nFTsAdapter
        swipe_refresh.setOnRefreshListener {
            mWebView.evaluateJavascript(WebViewApi.getOwnedTokens(mContext.sharedPref.publicKey), null)
        }
    }

    override fun loadNFTsSuccess(msg: String) {
        swipe_refresh.isRefreshing = false
        var chooseBean = ArrayList<ChooseGetBean>()
        try {
            chooseBean = Gson().fromJson<java.util.ArrayList<ChooseGetBean>>(msg, object : TypeToken<java.util.ArrayList<ChooseGetBean>>() {}.type)
        } catch (e: Exception) {
            "数据为空".logE()
        }

        nFTsList.clear()
        nFTsList.addAll(chooseBean)
        nFTsAdapter?.setNewData(nFTsList)
    }

    private fun showFingerPrintDialog(position: Int) {
        confirmPassword(mContext.sharedPref.isFinger, childFragmentManager, object : FingerSuccessCallback() {
            override fun onCheckSuccess() {
                Intent(mActivity, NFTsPayActivity::class.java).apply {
                    putExtra("domain", nFTsList[position].domain)
                    putExtra("token", nFTsList[position].name)
                    startActivity(this)
                }
            }
        })
    }
}
