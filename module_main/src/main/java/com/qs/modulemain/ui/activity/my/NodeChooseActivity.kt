package com.qs.modulemain.ui.activity.my

import android.app.Dialog
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.qs.modulemain.R
import com.qs.modulemain.bean.NodeBean
import com.qs.modulemain.ui.adapter.NodeListAdapter
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.getResourceColor
import com.smallcat.shenhai.mvpbase.extension.logE
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.helper.MessageEvent
import com.smallcat.shenhai.mvpbase.model.helper.RxBus
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_node_choose.*
import kotlinx.android.synthetic.main.activity_pay.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import java.util.regex.Pattern


class NodeChooseActivity : SimpleActivity() {

    private lateinit var adapter: NodeListAdapter
    private val list = ArrayList<NodeBean>()
    private var mChoosePos = 0

    private var addNodeDialog: Dialog? = null

    private var addNodeAddress: String = ""

    override val layoutId: Int
        get() = com.qs.modulemain.R.layout.activity_node_choose

    override fun initData() {
        tvTitle?.text = getString(com.qs.modulemain.R.string.choose_node)
//        tvRight?.apply {
//            text = getString(R.string.save)
//            setTextColor(getResourceColor(R.color.color_e4))
//            setOnClickListener {
//                val map = HashMap<String, Any>()
//                map["host"] = list[mChoosePos].nodeAddress
//                map["port"] = 443
//                map["protocol"] = "https"
//                WebViewApi.changeNetwork(Gson().toJson(map)).logE()
//                mWebView.evaluateJavascript(WebViewApi.changeNetwork(Gson().toJson(map)), null)
//                mWebView.evaluateJavascript(WebViewApi.EVTInit(), null)
//                sharedPref.chooseNode = list[mChoosePos].nodeAddress
//                RxBus.post(MessageEvent("https://" + list[mChoosePos].nodeAddress, RxBusCenter.CHANGE_NODE))
//                finish()
//            }
//        }
        tvRight?.apply {
            text = getString(com.qs.modulemain.R.string.add)
//            setTextColor(getResourceColor(R.color.color_e4))
            setOnClickListener {
                showAddNodeDialog()
            }
        }
        addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    when (it.type) {
                        RxBusCenter.ADD_NODE -> {
                            sharedPref.customNode += sharedPref.customNode + "#" + addNodeAddress
                            list.add(addNode(list.size + 1, addNodeAddress))
                            adapter.notifyDataSetChanged()
                        }
                    }
                })

        getData()
        adapter = NodeListAdapter(list)
        adapter.setOnItemClickListener { adapter, _, position ->
            list[mChoosePos].isChoose = false
            adapter.notifyItemChanged(mChoosePos)
            mChoosePos = position
            list[mChoosePos].isChoose = true
            adapter.notifyItemChanged(mChoosePos)

            val map = HashMap<String, Any>()
            map["host"] = list[mChoosePos].nodeAddress
            map["port"] = 443
            map["protocol"] = "https"
            WebViewApi.changeNetwork(Gson().toJson(map)).logE()
            mWebView.evaluateJavascript(WebViewApi.changeNetwork(Gson().toJson(map)), null)
            mWebView.evaluateJavascript(WebViewApi.EVTInit(), null)
            sharedPref.chooseNode = list[mChoosePos].nodeAddress
            RxBus.post(MessageEvent(/*"https://" +*/ list[mChoosePos].nodeAddress, RxBusCenter.CHANGE_NODE))
            finish()
        }
        rv_list.adapter = adapter
    }

    private fun showAddNodeDialog() {
        if (addNodeDialog == null) {
            addNodeDialog = Dialog(mContext, com.qs.modulemain.R.style.CustomDialog)
        }
        val view = LayoutInflater.from(mContext).inflate(com.qs.modulemain.R.layout.dialog_add_node, null)
        val etNode = view.findViewById<EditText>(com.qs.modulemain.R.id.et_node)
        val tvSure = view.findViewById<TextView>(com.qs.modulemain.R.id.tv_sure)
        val tvCancel = view.findViewById<TextView>(com.qs.modulemain.R.id.tv_cancel)
        tvSure.setOnClickListener {
            val input: String = etNode.text.toString()
            if (input.isNullOrEmpty()) {
                getString(R.string.add_node_hint).toast()
            } else {
                val rule = "^(http://|https://)[a-zA-Z0-9.]+:\\d*$"
                //注册正则表达式
                val pattern = Pattern.compile(rule)
                //匹配字符串，返回描述匹配结果的Matcher实例
                val matcher = pattern.matcher(input)
                //通过调用Matcher的find方法得知是否匹配成功
                if (matcher.find()) {
                    var isAdd = false
                    list.forEach { nodeBean: NodeBean ->
                        if (input.equals(nodeBean.nodeAddress)) {
                            isAdd = true
                            getString(R.string.node_exist).toast()
                        }
                    }
                    if (!isAdd) {
                        val inputSplit = input.split(":")
                        if (inputSplit.size >= 3) {
                            val arg0: String = inputSplit[1].substring(2)
                            val arg1: Int = Integer.parseInt(inputSplit[2])
                            val arg2: String = inputSplit[0]
                            ("arg0>>>" + arg0).logE()
                            ("arg1>>>" + arg1).logE()
                            ("arg2>>>" + arg2).logE()
                            addNodeAddress = input
//                        mWebView.evaluateJavascript(WebViewApi.checkNetwork(arg0, arg1, arg2), null)
                            val map = HashMap<String, Any>()
                            map["host"] = arg0
                            map["port"] = arg1
                            map["protocol"] = arg2
                            WebViewApi.checkNetwork(Gson().toJson(map)).logE()
                            mWebView.evaluateJavascript(WebViewApi.checkNetwork(Gson().toJson(map)), null)
                            //mWebView.evaluateJavascript(WebViewApi.EVTInit(), null)
                        }
                    }
                } else {
                    getString(R.string.add_valid_node_tip).toast()
                }
                addNodeDialog!!.dismiss()
            }
        }
        tvCancel.setOnClickListener { addNodeDialog!!.dismiss() }
        addNodeDialog!!.setContentView(view)
        addNodeDialog!!.setCanceledOnTouchOutside(false)
        addNodeDialog!!.setCancelable(true)
        addNodeDialog!!.show()
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
        list.add(addNode(16, "http://testnet1.everitoken.io:8888/"))
        if (!"".equals(sharedPref.customNode)) {
            val split = sharedPref.customNode.split("#")
            //index为集合下标，s为集合对象
            split.forEachIndexed { index, s ->
                list.add(addNode(17 + index, s))
            }
        }
    }

    private fun addNode(s1: Int, s2: String): NodeBean {
        if (s1 <= 15) {
            if (sharedPref.chooseNode == "https://mainnet$s1.everitoken.io") {
                mChoosePos = s1 - 1
                return NodeBean("https://mainnet$s1.everitoken.io", "MainNet$s2", true)
            }
            return NodeBean("https://mainnet$s1.everitoken.io", "MainNet$s2")
        } else {
            if (sharedPref.chooseNode == s2) {
                mChoosePos = s1 - 1
                if (s1 == 16) {
                    return NodeBean(s2, "TestNet", true)
                } else {
                    return NodeBean(s2, "CustomNet", true)
                }
            }
            if (s1 == 16) {
                return NodeBean(s2, "TestNet")
            } else {
                return NodeBean(s2, "CustomNet")
            }
        }
    }
}
