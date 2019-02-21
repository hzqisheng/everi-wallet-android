package com.qs.modulemain.ui.activity.index

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.widget.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.qs.modulemain.R
import com.qs.modulemain.bean.ChooseGetBean
import com.qs.modulemain.presenter.ChooseFTsPresenter
import com.qs.modulemain.ui.adapter.ChooseFTSAdapter
import com.qs.modulemain.ui.adapter.HelpAdapter
import com.qs.modulemain.util.DataUtils
import com.qs.modulemain.view.ChooseFTsView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.start
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import kotlinx.android.synthetic.main.activity_choose_fts.*

class ChooseFTsActivity : BaseActivity<ChooseFTsPresenter>(), ChooseFTsView {

    private lateinit var chooseAdapter: ChooseFTSAdapter
    private lateinit var dataList: ArrayList<ChooseGetBean>

    private var dialog: Dialog? = null

    override fun initPresenter() {
        mPresenter = ChooseFTsPresenter(mContext)
    }

    override val layoutId: Int = R.layout.activity_choose_fts

    override fun initData() {
        tvTitle?.text = getString(R.string.choose_fts)
        tv_sure.setOnClickListener {
            if (sharedPref.helpTipNext) {
                start(AddFtsActivity::class.java)
            } else {
                showTipDialog()
            }
        }
        dataList = ArrayList()
        chooseAdapter = ChooseFTSAdapter(dataList)
        rv_list.adapter = chooseAdapter
        swipe_refresh.setOnRefreshListener {
            mWebView.evaluateJavascript(WebViewApi.getEVTFungiblesList(sharedPref.publicKey), null)
        }
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

    private fun showTipDialog() {
        if (dialog == null) {
            dialog = Dialog(mContext, R.style.CustomDialog)
        }
        val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_help_tip, null)
        val rvTips = view.findViewById<RecyclerView>(R.id.rv_list)
        val adapter = HelpAdapter(DataUtils.addFtsHelpData(mContext))
        rvTips.adapter = adapter
        val btnCommit = view.findViewById<Button>(R.id.btn_commit)
        val cbTipNext = view.findViewById<CheckBox>(R.id.cb_tip_next)
        btnCommit.setOnClickListener {
            dialog!!.dismiss()
            if (cbTipNext.isChecked) {
                sharedPref.helpTipNext = true
            }
            start(AddFtsActivity::class.java)
        }
        cbTipNext.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {

            }
        }
        dialog!!.setContentView(view)
        dialog!!.setCanceledOnTouchOutside(false)
        dialog!!.setCancelable(true)
        dialog!!.show()
    }

    override fun onResume() {
        super.onResume()
        mWebView.evaluateJavascript(WebViewApi.getEVTFungiblesList(sharedPref.publicKey), null)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
        }
    }

    override fun onDataResult(result: String) {
        swipe_refresh.isRefreshing = false
        val chooseBean = Gson().fromJson<java.util.ArrayList<ChooseGetBean>>(result, object : TypeToken<java.util.ArrayList<ChooseGetBean>>() {}.type)
        if (chooseBean != null) {
            dataList.clear()
            dataList.addAll(chooseBean)
            chooseAdapter.notifyDataSetChanged()
        }
    }

}
