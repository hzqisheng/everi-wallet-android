package com.qs.modulemain.ui.activity.index

import com.qs.modulemain.R
import com.qs.modulemain.presenter.ChangePwdPresenter
import com.qs.modulemain.view.ChangePwdView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.start
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.model.bean.BaseData
import kotlinx.android.synthetic.main.activity_change_pwd.*

class ChangePwdActivity : BaseActivity<ChangePwdPresenter>(), ChangePwdView {
    /** 当前传递过来的钱包 **/
    private lateinit var mWalletBean:BaseData

    override fun initPresenter() {
        mPresenter = ChangePwdPresenter(mContext)
    }

    override val layoutId: Int
        get() = R.layout.activity_change_pwd

    override fun initData() {
        mWalletBean = intent.getSerializableExtra("data") as BaseData
        tvTitle?.text = getString(R.string.change_pwd)
        tv_import.setOnClickListener { start(RetrievePwdActivity::class.java) }
        tv_sure.setOnClickListener {
            if(!mWalletBean.password.equals(et_current.text.toString())){
                getString(R.string.password_error).toast()
                return@setOnClickListener
            }
            if(!et_new_pwd.text.toString().equals(et_new_pwd_confirm.text.toString())){
                getString(R.string.password_not_equals).toast()
                return@setOnClickListener
            }
            mWalletBean.password = et_new_pwd.text.toString()
            if(mWalletBean.update(mWalletBean.id.toLong()) > 0){
                "Success".toast()
                sharedPref.password = mWalletBean.password
                finish()
            }else{
                "Falied".toast()
            }


        }

    }

}
