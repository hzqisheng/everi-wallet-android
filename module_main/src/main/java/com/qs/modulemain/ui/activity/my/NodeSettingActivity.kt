package com.qs.modulemain.ui.activity.my

import android.annotation.SuppressLint
import com.qs.modulemain.R
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.start
import com.smallcat.shenhai.mvpbase.model.helper.MessageEvent
import com.smallcat.shenhai.mvpbase.model.helper.RxBus
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_node_setting.*

class NodeSettingActivity : SimpleActivity() {

    override val layoutId: Int
        get() = R.layout.activity_node_setting

    @SuppressLint("SetTextI18n")
    override fun initData() {
        tvTitle?.text = getString(R.string.node_setting)
        tv_node.text = "https://" + sharedPref.chooseNode
        ll_everi.setOnClickListener { start(NodeChooseActivity::class.java) }

        addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    when (it.type) {
                        RxBusCenter.CHANGE_NODE -> tv_node.text = it.msg
                    }
                })
    }

}
