package com.qs.modulemain.ui.activity.index

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.qs.modulemain.R
import com.qs.modulemain.arouter.ARouterCenter
import com.qs.modulemain.bean.ChooseGetBean
import com.qs.modulemain.bean.RecordDetailBean
import com.qs.modulemain.bean.RecordRequestBean
import com.qs.modulemain.presenter.RecordPresenter
import com.qs.modulemain.ui.adapter.RecordItemAdapter
import com.qs.modulemain.view.RecordView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.extension.logE
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.helper.MessageEvent
import com.smallcat.shenhai.mvpbase.model.helper.RxBus
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import com.smallcat.shenhai.mvpbase.utils.Base64Utils
import com.smallcat.shenhai.mvpbase.utils.fitSystemAllScroll
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_record.*
import java.util.ArrayList

class RecordActivity : BaseActivity<RecordPresenter>(), RecordView {

    private lateinit var data: ChooseGetBean
    private var resultBean: ArrayList<RecordDetailBean> = ArrayList()
    private lateinit var mAdapter: RecordItemAdapter

    override fun initPresenter() {
        mPresenter = RecordPresenter(mContext)
    }

    override val layoutId: Int = R.layout.activity_record

    override fun fitSystem() {
        fitSystemAllScroll(this)
    }

    @SuppressLint("SetTextI18n")
    override fun initData() {

        data = intent.getSerializableExtra("data") as ChooseGetBean

        tv_currency.text = data.sym_name + "(" + data.asset.split("S")[1] + ")"
        tv_money.text = data.asset.split(" ")[0] + " " + data.sym_name

        var isHaveIcon = false
        for (meta in data.metas) {
            if (meta.value.isNotEmpty() && meta.value.contains(",")) {
                val decodedByte: Bitmap? = Base64Utils.base64ToBitmap(meta.value) ?: continue
                iv_img.setImageBitmap(decodedByte)
                isHaveIcon = true
            }
        }
        if (!isHaveIcon && (data?.sym_name == "EVT" || data?.sym_name == "PEVT")) {
            iv_img.setImageResource(R.drawable.icon_fukuan_evt)
        } else if (!isHaveIcon) {
            iv_img.setImageResource(0)
        }

        addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    when (it.type) {
                        RxBusCenter.RECORD_TRANSACATION -> onDataResult(it.msg)
                    }
                })
        val request = RecordRequestBean()
        request.domain = ".fungible"
        request.key = data.sym.split("#")[1].toInt();
        request.skip = 0
        request.take = 100
        request.names = arrayOf("transferft").asList()

        mAdapter = RecordItemAdapter(resultBean)
        rv_list.adapter = mAdapter
        mAdapter.setOnItemClickListener { _, _, position ->
            val url = "https://evtscan.io/trx/${resultBean.get(position).trx_id}?theme=light"
            ARouterCenter.goWebViewActivity(url)
        }

        val json = Gson().toJson(request)

        json.logE()

        mWebView.evaluateJavascript(WebViewApi.getFungibleActionsByAddress(data.sym.split("#")[1].toInt(), sharedPref.publicKey, 0, 20)) {}

        //转账
        tv_transfer.setOnClickListener {
            val intent = Intent(this@RecordActivity, ScanPayActivity::class.java)
            intent.putExtra("maxMoney", data.sym.split(" ")[0])
            intent.putExtra("data", data)
            startActivity(intent)
        }

        //收款
        tv_collect.setOnClickListener {
            intent = Intent(this, CollectActivity::class.java)
            val currencyString = tv_currency.text.toString()
            if (currencyString.contains("#")) {
                val fungibleId = currencyString.substring(currencyString.indexOf("#") + 1, currencyString.length - 1)
                Log.e("fungibleId", fungibleId)
                intent.putExtra("fungibleId", fungibleId.toInt())
            }
            startActivity(intent)
        }
    }

    private fun onDataResult(msg: String) {
        if (msg.isEmpty()) return
        val result = Gson().fromJson<java.util.ArrayList<RecordDetailBean>>(msg, object : TypeToken<ArrayList<RecordDetailBean>>() {}.type)
        resultBean.addAll(result)
        mAdapter.notifyDataSetChanged()
    }

}
