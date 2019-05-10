package com.qs.modulemain.ui.activity.manage

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.qs.modulemain.R
import com.qs.modulemain.bean.*
import com.qs.modulemain.ui.activity.index.AddMetaActivity
import com.qs.modulemain.ui.adapter.MetaDataAdapter
import com.qs.modulemain.ui.widget.MyNodeViewFactory
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.logE
import com.smallcat.shenhai.mvpbase.extension.start
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.helper.MessageEvent
import com.smallcat.shenhai.mvpbase.model.helper.RxBus
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_create_group.*
import kotlinx.android.synthetic.main.activity_fts_issue_edit.*
import kotlinx.android.synthetic.main.activity_group_detail.*
import kotlinx.android.synthetic.main.activity_group_detail.iv_add_data
import kotlinx.android.synthetic.main.activity_group_detail.rl_container
import kotlinx.android.synthetic.main.activity_group_detail.tv_name
import kotlinx.android.synthetic.main.activity_group_detail.tv_threshold
import me.texy.treeview.TreeNode
import me.texy.treeview.TreeView
import org.json.JSONArray
import org.json.JSONObject


class GroupDetailActivity : SimpleActivity() {

    companion object {
        lateinit var mRootNode: TreeNode
    }

    private var mGroupName = ""

    override val layoutId: Int
        get() = R.layout.activity_group_detail

    private lateinit var mTreeView: TreeView
    var mGroupDetail = GroupDetailBean()

    private var mList = ArrayList<DetailMetas>()

    private lateinit var mAdapter: MetaDataAdapter

    @SuppressLint("SetTextI18n")
    override fun initData() {
        mGroupName = intent.getStringExtra("groupName")
        tvTitle?.text = mGroupName
        tvRight?.apply {
            text = getString(R.string.authority_setting)
            setTextColor(Color.WHITE)
            setOnClickListener {
                val intent = Intent(this@GroupDetailActivity, CreateGroupActivity::class.java)
                intent.putExtra("threshold", mGroupDetail.root.threshold)
                intent.putExtra("name", mGroupDetail.name)
                startActivityForResult(intent, 101)
            }
        }
        iv_add_data.setOnClickListener {
            var intent = Intent(this@GroupDetailActivity, AddMetaActivity::class.java)
            startActivityForResult(intent, 103)
        }

        mAdapter = MetaDataAdapter(mList)
        rv_list.adapter = mAdapter

        addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    when (it.type) {
                        RxBusCenter.GROUP_DETAIL -> {
                            loadGroupDetailSuccess(it.msg)
                        }
                    }
                })

        mWebView.evaluateJavascript(WebViewApi.getGroupDetail(mGroupName), null)
    }

    fun loadGroupDetailSuccess(msg: String) {
        mGroupDetail = Gson().fromJson<GroupDetailBean>(msg, object : TypeToken<GroupDetailBean>() {}.type)
        tv_name.text = mGroupDetail.name
        tv_threshold.text = mGroupDetail.root.threshold.toString()
        //组节点
        mRootNode = TreeNode.root()
        convertToTreeNodes()
        val myNodeViewFactory = MyNodeViewFactory(this, mRootNode, true)
        mTreeView = TreeView(mRootNode, this, myNodeViewFactory)
        myNodeViewFactory.setmTreeView(mTreeView)
        val view = mTreeView.view
        view.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        rl_container.addView(view)
        //元数据
        mList.clear()
        mList.addAll(mGroupDetail.metas)
        mAdapter.setNewData(mList)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101) {
            mTreeView.refreshTreeView()
        }
        if (requestCode == 103) {
            if (resultCode > 0 && data != null) {
                var metaBean = data.getSerializableExtra("result") as ChooseGetBean.MetasBean

            }
        }
    }

    private fun convertToTreeNodes() {
        val nodesString = Gson().toJson(mGroupDetail.root.nodes)
        val jsonArray = JSONArray(nodesString)
        addNode(mRootNode, jsonArray)
    }

    private fun addNode(node: TreeNode, jsonArray: JSONArray) {
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            if (node.isRoot) {
                if (jsonObject.has("nodes")) {
                    val nonLeafNode = GroupNonLeafNode(jsonObject.getInt("threshold"), jsonObject.getInt("weight"))
                    val treeNode = TreeNode(nonLeafNode)
                    treeNode.isLeafNode = false
                    treeNode.isExpanded = true
                    treeNode.level = 0
                    node.addChild(treeNode)
                    addNode(treeNode, JSONArray(jsonObject.getString("nodes")))
                } else {
                    val leafNode = GroupLeafNode(jsonObject.getString("key"), jsonObject.getInt("weight"))
                    val treeNode = TreeNode(leafNode)
                    treeNode.isLeafNode = true
                    treeNode.level = 0
                    node.addChild(treeNode)
                }
            } else {
                if (jsonObject.has("nodes")) {
                    val nonLeafNode = GroupNonLeafNode(jsonObject.getInt("threshold"), jsonObject.getInt("weight"))
                    val treeNode = TreeNode(nonLeafNode)
                    treeNode.isLeafNode = false
                    treeNode.isExpanded = true
                    node.addChild(treeNode)
                    addNode(treeNode, JSONArray(jsonObject.getString("nodes")))
                } else {
                    val leafNode = GroupLeafNode(jsonObject.getString("key"), jsonObject.getInt("weight"))
                    val treeNode = TreeNode(leafNode)
                    treeNode.isLeafNode = true
                    node.addChild(treeNode)
                }
            }
        }
    }

}
