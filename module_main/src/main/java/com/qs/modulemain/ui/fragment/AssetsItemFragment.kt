package com.qs.modulemain.ui.fragment


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.qs.modulemain.R
import com.qs.modulemain.bean.ChooseGetBean
import com.qs.modulemain.presenter.AssetsItemPresenter
import com.qs.modulemain.ui.activity.index.NFTsIssueActivity
import com.qs.modulemain.ui.activity.index.RecordActivity
import com.qs.modulemain.ui.adapter.AssetsItemAdapter
import com.qs.modulemain.ui.adapter.AssetsNFtsAdapter
import com.qs.modulemain.view.AssetsItemView
import com.smallcat.shenhai.mvpbase.base.BaseFragment
import com.smallcat.shenhai.mvpbase.extension.logE
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import kotlinx.android.synthetic.main.fragment_assets_item.*
import java.lang.Exception
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
        rv_list.adapter = ftsAdapter

        ftsAdapter.onItemChildClickListener = object : AdapterView.OnItemClickListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {

            }

            override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                val intent = Intent(context,RecordActivity::class.java)
                intent.putExtra("data",ftsList[position])
                startActivity(intent)
            }

            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }

        }

    }
    companion object {
        var firstBean:ChooseGetBean ?= null
    }
    override fun loadFTsSuccess(msg: String) {
        if(msg.isEmpty())return
        var chooseBean = ArrayList<ChooseGetBean>()
        try {
            chooseBean = Gson().fromJson<java.util.ArrayList<ChooseGetBean>>(msg,object : TypeToken<java.util.ArrayList<ChooseGetBean>>() {}.type)
            firstBean = chooseBean[0]
        }catch (e: Exception){
            "数据为空".logE()
        }

        ftsList.clear()
        ftsList.addAll(chooseBean)
        ftsAdapter.notifyDataSetChanged()
    }
}
