package com.qs.modulemain.ui.activity.index

import android.annotation.SuppressLint
import com.google.gson.Gson
import com.qs.modulemain.R
import com.qs.modulemain.bean.ChooseGetBean
import com.qs.modulemain.ui.activity.MainActivity
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.start
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.helper.MessageEvent
import com.smallcat.shenhai.mvpbase.model.helper.RxBus
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import com.smallcat.shenhai.mvpbase.utils.fitSystemAllScroll
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_transaction_record.*

class TransactionRecordActivity : SimpleActivity() {

    private var number = ""

    override val layoutId: Int
        get() = R.layout.activity_transaction_record

    override fun initData() {
        val id = intent.getStringExtra("id")
        tv_sure.setOnClickListener { start(MainActivity::class.java) }

        addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    when (it.type) {
                        RxBusCenter.TRAN_MSG -> getDataSuccess(it.msg)
                        RxBusCenter.SYMBOL_DETAIL -> onFTSDetail(it.msg)
                    }
                })

        mWebView.evaluateJavascript(WebViewApi.getTransactionDetailById(id), null)

    }

    override fun fitSystem() {
        fitSystemAllScroll(this)
    }


    private fun getDataSuccess(data: String) {
        val bean = Gson().fromJson<TransNumber>(data, TransNumber::class.java)
        val s = bean.transaction.actions[0].data.number
        number = s.substring(0, s.indexOf(" "))
        val id = s.substring(s.lastIndexOf("#") + 1)
        mWebView.evaluateJavascript(WebViewApi.getFungibleSymbolDetail(id.toLong()), null)
    }

    @SuppressLint("SetTextI18n")
    private fun onFTSDetail(data: String) {
        val bean: ChooseGetBean = Gson().fromJson(data, ChooseGetBean::class.java)
        tv_pay.text = "-$number ${bean.sym_name}"
    }


    data class TransNumber(
            val transaction: Transaction
    )

    data class Transaction(
            val actions: List<Action>
    )

    data class Action(
            val `data`: Data
    )

    data class Data(
            val number: String
    )

}
