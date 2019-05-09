package com.qs.modulemain.ui.activity.manage

import android.content.Intent
import android.util.Log
import android.view.ViewGroup
import com.google.gson.Gson
import com.qs.modulemain.R
import com.qs.modulemain.bean.*
import com.qs.modulemain.presenter.CreateGroupPresenter
import com.qs.modulemain.ui.widget.MyNodeViewFactory
import com.qs.modulemain.view.CreateGroupView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.toast
import kotlinx.android.synthetic.main.activity_create_group.*
import me.texy.treeview.TreeNode
import me.texy.treeview.TreeView
import java.lang.Exception

const val nodeRequestCode = 0x99
const val nodeResultCode = 0x199

class CreateGroupActivity : BaseActivity<CreateGroupPresenter>(), CreateGroupView {

//    private var dialog: Dialog? = null
//    private var mChoosePosition = 0
//    private lateinit var list: ArrayList<ChooseBean>

    companion object {
        var isAddLevel1Node = true
        var currentAddNode: TreeNode? = null
    }

    private lateinit var mRootNode: TreeNode
    private lateinit var mTreeView: TreeView

    override fun initPresenter() {
        mPresenter = CreateGroupPresenter(mContext)
    }

    override val layoutId: Int = R.layout.activity_create_group

    override fun initData() {
        tvTitle?.text = getString(R.string.create_group)
//        list = arrayListOf(ChooseBean(getString(R.string.I_am_in_charge_of_management), true),
//                ChooseBean(getString(R.string.from_adress_book_choose)), ChooseBean(getString(R.string.hand_input_pub_key)))
//        tv_manage.setOnClickListener { showDialog() }
        iv_add_note.setOnClickListener {
            //start(AddNoteActivity::class.java)
            isAddLevel1Node = true
            val intent = Intent(this@CreateGroupActivity, AddNoteActivity::class.java)
            startActivityForResult(intent, nodeRequestCode)
        }
        tv_sure.setOnClickListener {
            //start(CreateGroup2Activity::class.java)
            if (et_name.text.toString().isNullOrEmpty()) {
                getString(R.string.please_input_name).toast()
                return@setOnClickListener
            }

//            val nodes = ArrayList<TreeNode>()
//            mRootNode.getAllTreeNodes(nodes, mRootNode)
//            var isHaveLeafNode = false
//            nodes.forEach {
//                if (it.isLeafNode) {
//                    isHaveLeafNode = true
//                }
//            }
//            Log.e("Lody", "size：${nodes.size}")
//            if (!isHaveLeafNode) {
//                getString(R.string.invalid_authority_settings).toast()
//                mRootNode.children.clear()
//                mTreeView.refreshTreeView()
//                return@setOnClickListener
//            }

            //删除无效非叶节点
            val nodes = ArrayList<TreeNode>()
            val invalidNodes = ArrayList<TreeNode>()
            mRootNode.getAllTreeNodes(nodes, mRootNode)
            mRootNode.getInvalidTreeNodes(nodes, invalidNodes)
            Log.e("Lody", "无效节点size：${invalidNodes.size}")
            var isHaveLeafNode = false
            nodes.forEach {
                if (it.isLeafNode) {
                    isHaveLeafNode = true
                }
            }
            if (!isHaveLeafNode) {
                getString(R.string.invalid_authority_settings).toast()
                mRootNode.children.clear()
                mTreeView.refreshTreeView()
                return@setOnClickListener
            }
            invalidNodes.forEach {
                mRootNode.removeChild(it)
            }
            mTreeView.refreshTreeView()

            val allNodes = ArrayList<Any>()
            getRoot(allNodes, mRootNode)
            Log.e("Lody", Gson().toJson(allNodes))
//            mTreeView.refreshTreeView()
//            if (allNodes.size <= 0) {
//                getString(R.string.invalid_authority_settings).toast()
//                return@setOnClickListener
//            }
            val root = Root(getThreshold(), 0, allNodes)
            val group = Group(et_name.text.toString(), sharedPref.privateKey, root)
            val rootGroup = RootGroup(et_name.text.toString(), group)
            Log.e("Lody", Gson().toJson(rootGroup))
        }
        iv_reduce.setOnClickListener {
            try {
                et_number.setText(((et_number.text.toString().toInt()) - 1).toString())
            } catch (e: Exception) {
                e.printStackTrace()
                et_number.setText("1")
            }
        }
        iv_add.setOnClickListener {
            try {
                et_number.setText(((et_number.text.toString().toInt()) + 1).toString())
            } catch (e: Exception) {
                e.printStackTrace()
                et_number.setText("1")
            }
        }

        mRootNode = TreeNode.root()
//        buildTree()
        val myNodeViewFactory = MyNodeViewFactory(this, mRootNode)
        mTreeView = TreeView(mRootNode, this, myNodeViewFactory)
        myNodeViewFactory.setmTreeView(mTreeView)
        val view = mTreeView.view
        view.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        rl_container.addView(view)

    }

    private fun getThreshold(): Int {
        return try {
            et_number.text.toString().toInt()
        } catch (e: Exception) {
            e.printStackTrace()
            1
        }
    }

    private fun buildTree() {
        for (i in 0..19) {
            val treeNode = TreeNode("Parent  No.$i")
            treeNode.level = 0
            for (j in 0..9) {
                val treeNode1 = TreeNode("Child No.$j")
                treeNode1.level = 1
                for (k in 0..4) {
                    val treeNode2 = TreeNode("Grand Child No.$k")
                    treeNode2.level = 2
                    treeNode1.addChild(treeNode2)
                }
                treeNode.addChild(treeNode1)
            }
            mRootNode.addChild(treeNode)
        }
    }

    private fun getRoot(nodes: ArrayList<Any>, node: TreeNode) {
        if (node.isRoot) {
            if (node.children.size > 0)
                node.children.forEach { getRoot(nodes, it) }
            else
                return
        } else {
            if (node.isLeafNode) {
                nodes.add(node.value)
                return
            } else {

                //删除没有叶子节点的非叶节点，递归遍历同时进行删除会将深层遍历的元素删除，出现闪退
                //解决办法，先遍历出所有需要删除的数据，再在统一进行删除操作
//                val allNodes = ArrayList<TreeNode>()
//                node.getAllTreeNodes(allNodes, node)
//                var isHaveLeafNode = false
//                allNodes.forEach {
//                    if (it.isLeafNode) {
//                        isHaveLeafNode = true
//                    }
//                }
//                if (!isHaveLeafNode) {
//                    mRootNode.removeChild(node)
//                    return
//                }

                val nonLeafNode = node.value as GroupNonLeafNode
                val subNodes = ArrayList<Any>()
                nodes.add(NonLeafNode(nonLeafNode.threshold, nonLeafNode.weight, subNodes))
                node.children.forEach {
                    getRoot(subNodes, it)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == nodeResultCode) {
            val isLeafNode = data!!.getBooleanExtra("isLeafNode", false)
            var result: Any
            if (isLeafNode) {
                result = data!!.getParcelableExtra<GroupLeafNode>("result")
            } else {
                result = data!!.getParcelableExtra<GroupNonLeafNode>("result")
            }
            val treeNode = TreeNode(result)
            treeNode.isLeafNode = isLeafNode
            if (isAddLevel1Node) {
                treeNode.level = 0
                mRootNode.addChild(treeNode)
            } else {
                currentAddNode?.addChild(treeNode)
                currentAddNode?.isExpanded = true
            }
            mTreeView.refreshTreeView()
        }
    }

//    private fun showDialog(){
//        if (dialog == null) {
//            dialog = Dialog(mContext, R.style.CustomDialog)
//        }
//        val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_group_create, null)
//        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_list)
//        val adapter = ChooseAdapter(list)
//        adapter.setOnItemClickListener { _, _, position ->
//            list[mChoosePosition].isSelected = false
//            mChoosePosition = position
//            list[mChoosePosition].isSelected = true
//            adapter.setNewData(list)
//            Handler().postDelayed({
//                dialog?.dismiss()
//                tv_manage.text = list[position].title
//            }, 300)
//        }
//        recyclerView.adapter = adapter
//        dialog!!.setContentView(view)
//        dialog!!.setCanceledOnTouchOutside(true)
//        dialog!!.setCancelable(true)
//        dialog!!.show()
//    }

//    override fun onDestroy() {
//        if (dialog != null && dialog!!.isShowing) {
//            dialog!!.dismiss()
//        }
//        super.onDestroy()
//    }
}
