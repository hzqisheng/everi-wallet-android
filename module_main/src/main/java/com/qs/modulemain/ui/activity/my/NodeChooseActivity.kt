package com.qs.modulemain.ui.activity.my

import com.google.gson.Gson
import com.qs.modulemain.R
import com.qs.modulemain.bean.NodeBean
import com.qs.modulemain.ui.adapter.NodeListAdapter
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.getResourceColor
import com.smallcat.shenhai.mvpbase.extension.logE
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.helper.MessageEvent
import com.smallcat.shenhai.mvpbase.model.helper.RxBus
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import kotlinx.android.synthetic.main.activity_node_choose.*
import java.util.*

class NodeChooseActivity : SimpleActivity() {

    private lateinit var adapter: NodeListAdapter
    private val list = ArrayList<NodeBean>()
    private var mChoosePos = 0

    override val layoutId: Int
        get() = R.layout.activity_node_choose

    override fun initData() {
        tvTitle?.text = getString(R.string.choose_node)
        tvRight?.apply {
            text = getString(R.string.save)
            setTextColor(getResourceColor(R.color.color_e4))
            setOnClickListener {
                val map = HashMap<String, Any>()
                map["host"] = list[mChoosePos].nodeAddress
                map["port"] = 443
                map["protocol"] = "https"
                WebViewApi.changeNetwork(Gson().toJson(map)).logE()
                mWebView.evaluateJavascript(WebViewApi.changeNetwork(Gson().toJson(map)), null)
                mWebView.evaluateJavascript(WebViewApi.EVTInit(), null)
                sharedPref.chooseNode = "https://" + list[mChoosePos].nodeName
                RxBus.post(MessageEvent("https://" + list[mChoosePos].nodeAddress, RxBusCenter.CHANGE_NODE))
                finish()
            }
        }

        getData()
        adapter = NodeListAdapter(list)
        adapter.setOnItemClickListener { adapter, _, position ->
            list[mChoosePos].isChoose = false
            adapter.notifyItemChanged(mChoosePos)
            mChoosePos = position
            list[mChoosePos].isChoose = true
            adapter.notifyItemChanged(mChoosePos)
        }
        rv_list.adapter = adapter
    }

    private fun getData() {
        list.clear()
        list.add(addNode(1, "(HONG KONG) [with history plugin]"))
        list.add(addNode(2, "(SILICONVALLEY)"))
        list.add(addNode(3, "(TOKYO)"))
        list.add(addNode(4, "(FRANKFURT)"))
        list.add(addNode(5, "(SEOUL)"))
        list.add(addNode(6, "(DUBAI)"))
        list.add(addNode(7, "(SINGAPORE) [with history plugin]"))
        list.add(addNode(8, "(FRANKFURT)"))
        list.add(addNode(9, "(KUALA LUMPUR) [with history plugin]"))
        list.add(addNode(10, "(TOKYO)"))
        list.add(addNode(11, "(SILICONVALLEY)"))
        list.add(addNode(12, "(HONG KONG)"))
        list.add(addNode(13, "(VIRGINIA)"))
        list.add(addNode(14, "(SHANGHAI) [with history plugin]"))
        list.add(addNode(15, "(SINGAPORE) [with history plugin]"))
    }

    private fun addNode(s1: Int, s2: String): NodeBean {
        if (sharedPref.chooseNode == "https://mainnet$s1.everitoken.io") {
            mChoosePos = s1 - 1
            return NodeBean("mainnet$s1.everitoken.io", "MainNet$s2", true)
        }
        return NodeBean("mainnet$s1.everitoken.io", "MainNet$s2")
    }
}
