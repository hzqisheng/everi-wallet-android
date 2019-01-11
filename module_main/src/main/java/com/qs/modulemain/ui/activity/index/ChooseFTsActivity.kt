package com.qs.modulemain.ui.activity.index

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.qs.modulemain.R
import com.qs.modulemain.bean.AddFTSBean
import com.qs.modulemain.bean.ChooseFTSBean
import com.qs.modulemain.bean.ChooseGetBean
import com.qs.modulemain.presenter.ChooseFTsPresenter
import com.qs.modulemain.ui.adapter.ChooseFTSAdapter
import com.qs.modulemain.view.ChooseFTsView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.extension.logE
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.start
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.bean.NFtsBean
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import com.smallcat.shenhai.mvpbase.utils.lastMY_NFTS
import kotlinx.android.synthetic.main.activity_choose_fts.*

class ChooseFTsActivity : BaseActivity<ChooseFTsPresenter>(), ChooseFTsView {

    private lateinit var chooseAdapter: ChooseFTSAdapter
    private lateinit var dataList: ArrayList<ChooseGetBean>
    override fun initPresenter() {
        mPresenter = ChooseFTsPresenter(mContext)
    }

    override val layoutId: Int = R.layout.activity_choose_fts

    override fun initData() {
        tvTitle?.text = getString(R.string.choose_fts)
        tv_sure.setOnClickListener { start(AddFtsActivity::class.java) }
        dataList = ArrayList()
        chooseAdapter = ChooseFTSAdapter(dataList)
        rv_list.adapter = chooseAdapter

        chooseAdapter.setOnItemClickListener { _, _, position ->
            //dataList[position]!!.sym.split("#")[1]
            val intent = Intent(mContext, FtsIssueActivity::class.java)
            val bundle = Bundle()
            bundle.putString("data", dataList[position].sym.split("#")[1])
            bundle.putString("jingdu", dataList[position].sym.split(",")[0])
            intent.putExtras(bundle)
            mContext.startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        mWebView.evaluateJavascript(WebViewApi.getEVTFungiblesList(sharedPref.publicKey), null)
    }

    override fun onDataResult(result: String) {
        val chooseBean = Gson().fromJson<java.util.ArrayList<ChooseGetBean>>(result, object : TypeToken<java.util.ArrayList<ChooseGetBean>>() {}.type)
        if (chooseBean != null) {
            dataList.clear()
            dataList.addAll(chooseBean)
            chooseAdapter.notifyDataSetChanged()
        }
    }

}
