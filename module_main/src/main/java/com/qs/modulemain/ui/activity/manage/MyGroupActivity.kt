package com.qs.modulemain.ui.activity.manage

import android.content.Intent
import android.view.View
import android.widget.AdapterView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.qs.modulemain.R
import com.qs.modulemain.bean.GroupNameBean
import com.qs.modulemain.presenter.MyGroupPresenter
import com.qs.modulemain.ui.adapter.MyGroupAdapter
import com.qs.modulemain.view.MyGroupView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.start
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import kotlinx.android.synthetic.main.activity_my_group.rv_list
import kotlinx.android.synthetic.main.activity_my_group.swipe_refresh
import kotlinx.android.synthetic.main.activity_my_group.tv_add

class MyGroupActivity : BaseActivity<MyGroupPresenter>(), MyGroupView {

    companion object {
        var resultCode = 0x103
    }

    private var mGroupList = ArrayList<GroupNameBean>()

    private lateinit var mGroupAdapter: MyGroupAdapter

    var isAddGroup = false

    override fun initPresenter() {
        mPresenter = MyGroupPresenter(mContext)
    }

    override val layoutId: Int = R.layout.activity_my_group

    override fun initData() {
        tvTitle?.text = getString(R.string.my_groups)
        tv_add.setOnClickListener { start(CreateGroupActivity::class.java) }

        isAddGroup = intent.getBooleanExtra("isAddGroup", false)
        if (isAddGroup) {
            mGroupList.add(GroupNameBean(".OWNER"))
        }
        mGroupAdapter = MyGroupAdapter(mGroupList)
        rv_list.adapter = mGroupAdapter

        swipe_refresh.setOnRefreshListener {
            mWebView.evaluateJavascript(WebViewApi.getManagedGroups(sharedPref.publicKey), null)
        }

        mGroupAdapter.onItemClickListener = object : AdapterView.OnItemClickListener, BaseQuickAdapter.OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                if (isAddGroup) {
                    val intent = Intent()
                    intent.putExtra("groupName", mGroupList[position].name)
                    setResult(resultCode, intent)
                    finish()
                } else {
                    val intent = Intent(this@MyGroupActivity, GroupDetailActivity::class.java)
                    intent.putExtra("groupName", mGroupList[position].name)
                    startActivity(intent)
                }
            }

            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }
        }
    }

    override fun onResume() {
        super.onResume()
        mWebView.evaluateJavascript(WebViewApi.getManagedGroups(sharedPref.publicKey), null)
    }

    override fun loadGroupSuccess(msg: String) {
        swipe_refresh.isRefreshing = false
        val list = Gson().fromJson<List<GroupNameBean>>(msg, object : TypeToken<ArrayList<GroupNameBean>>() {}.type)
        mGroupList.clear()
        if (isAddGroup) {
            mGroupList.add(GroupNameBean(".OWNER"))
        }
        mGroupList.addAll(list)
        mGroupAdapter.setNewData(mGroupList)
    }

}
