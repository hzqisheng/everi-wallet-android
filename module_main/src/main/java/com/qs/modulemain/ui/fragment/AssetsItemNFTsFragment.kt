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
import com.qs.modulemain.presenter.AssetsItemNFTsPresenter
import com.qs.modulemain.ui.activity.index.RecordActivity
import com.qs.modulemain.ui.adapter.AssetsNFtsAdapter
import com.qs.modulemain.view.AssetsItemNFTsView
import com.smallcat.shenhai.mvpbase.base.BaseFragment
import com.smallcat.shenhai.mvpbase.extension.logE
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.bean.NFtsBean
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import com.smallcat.shenhai.mvpbase.utils.lastMY_NFTS
import kotlinx.android.synthetic.main.fragment_assets_item.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList


class AssetsItemNFTsFragment : BaseFragment<AssetsItemNFTsPresenter>(), AssetsItemNFTsView {

    private var type = -1
    private var nFTsList = ArrayList<ChooseGetBean>()

    private lateinit var nFTsAdapter: AssetsNFtsAdapter

    override fun initPresenter() {
        mPresenter = AssetsItemNFTsPresenter(mContext)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && type != -1) {
            lastMY_NFTS = RxBusCenter.MY_NFTS
            mWebView.evaluateJavascript(WebViewApi.getOwnedTokens(mContext.sharedPref.publicKey)) {}
        }
    }

    override val layoutId: Int = R.layout.fragment_assets_item

    override fun onResume() {
        super.onResume()
        lastMY_NFTS = RxBusCenter.MY_NFTS
        mWebView.evaluateJavascript(WebViewApi.getOwnedTokens(mContext.sharedPref.publicKey)) {
        }
    }

    override fun initData() {
        type = 0
        lastMY_NFTS = RxBusCenter.MY_NFTS
        nFTsAdapter = AssetsNFtsAdapter(nFTsList)
        rv_list.adapter = nFTsAdapter

    }

    override fun loadNFTsSuccess(msg: String) {
        var chooseBean = ArrayList<ChooseGetBean>()
        try {
            chooseBean = Gson().fromJson<java.util.ArrayList<ChooseGetBean>>(msg,object : TypeToken<java.util.ArrayList<ChooseGetBean>>() {}.type)

        }catch (e:Exception){
            "异常".logE()
        }

        nFTsList.clear()
        nFTsList.addAll(chooseBean)
//        nFTsAdapter.notifyDataSetChanged()
    }

}
