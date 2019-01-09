package com.qs.modulemain.ui.activity.index

import android.content.Intent
import com.google.gson.Gson
import com.qs.modulemain.R
import com.qs.modulemain.bean.ChooseGetBean
import com.qs.modulemain.bean.DomainBean
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.toast
import kotlinx.android.synthetic.main.activity_issue_fts_authority.*
import java.lang.Exception
import java.util.ArrayList

class IssueFtsAuthorityActivity : SimpleActivity() {
    private lateinit var mChooseGetBean: ChooseGetBean
    private  var mIssueBean:ChooseGetBean.IssueBean.AuthorizersBean ?= null
    private  var mManageBean:ChooseGetBean.ManageBean.AuthorizersBeanXX ?= null

    override val layoutId: Int
        get() = R.layout.activity_issue_fts_authority

    override fun initData() {
        tvTitle?.text = getString(R.string.authority_setting)
        mChooseGetBean = ChooseGetBean()
        //发行
        cb_issue.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                if(cb_edit.isChecked){
                    cb_edit.isChecked = false
                }
            }
        }
        cb_edit.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                if(cb_issue.isChecked){
                    cb_issue.isChecked = false
                }
            }
        }

        //管理
        cb_manage.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                if(cb_edit_manage.isChecked){
                    cb_edit_manage.isChecked = false
                }
            }
        }
        cb_edit_manage.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                if(cb_manage.isChecked){
                    cb_manage.isChecked = false
                }
            }
        }

        tv_issue_edit.setOnClickListener {
            val intent = Intent(this@IssueFtsAuthorityActivity,NFTsEditActivity::class.java)
            startActivityForResult(intent,101)
        }

        tv_manage_edit.setOnClickListener {
            val intent = Intent(this@IssueFtsAuthorityActivity,NFTsEditActivity::class.java)
            startActivityForResult(intent,102)
        }



        tv_sure.setOnClickListener {
            handleFtsBean()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 101){
            if(data == null)return
            val resultJson = data.getStringExtra("result")
            if(resultJson.toString().isEmpty()){
                "invalid code !".toast()
                return
            }
            try {
                mIssueBean = Gson().fromJson(resultJson,ChooseGetBean.IssueBean.AuthorizersBean::class.java)
            }catch (e: Exception){
                "invalid code !".toast()
            }

        }

        if(requestCode == 102){
            val resultJson = data!!.getStringExtra("result")
            if(resultJson.toString().isEmpty()){
                "invalid code !".toast()
                return
            }
            try {
                mManageBean = Gson().fromJson(resultJson,ChooseGetBean.ManageBean.AuthorizersBeanXX::class.java)
            }catch (e: Exception){
                "invalid code !".toast()
            }

        }

    }


    fun handleFtsBean(){
        if (cb_issue.isChecked) {
            val issueBean = ChooseGetBean.IssueBean.AuthorizersBean()
            issueBean.ref = "[A] " + sharedPref.publicKey
            issueBean.weight = 1
            mChooseGetBean.addIssueAuthorizersBean(issueBean)
        }else{
            if(mIssueBean != null)
            mChooseGetBean.addIssueAuthorizersBean(mIssueBean)
        }

        if(cb_manage.isChecked) {
            val manageBean = ChooseGetBean.ManageBean.AuthorizersBeanXX()
            manageBean.ref = "[A] " + sharedPref.publicKey
            manageBean.weight = 1
            mChooseGetBean.addManageAuthorizersBean(manageBean)
        }else{
            if(mManageBean != null)
            mChooseGetBean.addManageAuthorizersBean(mManageBean)
        }

        if(mChooseGetBean.issue == null ){
            val issue = ChooseGetBean.IssueBean()
            issue.authorizers = ArrayList()
            mChooseGetBean.issue = issue
            return
        }

        if(mChooseGetBean.manage == null){
            val mange = ChooseGetBean.ManageBean()
            mange.authorizers = ArrayList()
            mChooseGetBean.manage = mange

        }

        val intent = Intent()
        intent.putExtra("result",mChooseGetBean)
        setResult(1,intent)
        finish()
    }

}
