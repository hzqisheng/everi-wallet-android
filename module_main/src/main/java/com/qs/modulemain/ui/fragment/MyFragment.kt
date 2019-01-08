package com.qs.modulemain.ui.fragment


import android.app.Dialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.qs.modulemain.R
import com.qs.modulemain.arouter.ARouterCenter
import com.qs.modulemain.presenter.MyPresenter
import com.qs.modulemain.ui.activity.index.CreateWalletIdIndex
import com.qs.modulemain.ui.activity.my.AboutUsActivity
import com.qs.modulemain.ui.activity.my.HelpCenterActivity
import com.qs.modulemain.ui.activity.my.JoinCommunitiesActivity
import com.qs.modulemain.ui.activity.my.SettingActivity
import com.qs.modulemain.view.MyView
import com.smallcat.shenhai.mvpbase.base.BaseFragment
import com.smallcat.shenhai.mvpbase.extension.getResourceString
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.start
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.bean.BaseData
import kotlinx.android.synthetic.main.fragment_my.*
import org.litepal.crud.DataSupport


class MyFragment : BaseFragment<MyPresenter>(), MyView, View.OnClickListener {
    private var pwdDialog:Dialog? = null
    override fun initPresenter() {
        mPresenter = MyPresenter(mContext)
    }

    override val layoutId: Int
        get() = R.layout.fragment_my

    override fun initData() {
        tv_wallet.setOnClickListener(this)
        tv_address.setOnClickListener(this)
        tv_add_community.setOnClickListener(this)
        tv_help.setOnClickListener(this)
        tv_setting.setOnClickListener(this)
        tv_share.setOnClickListener(this)
        tv_about_us.setOnClickListener(this)
        tv_login_out.setOnClickListener (this )
    }

    override fun loadSuccess(data: Any) {
    }

    override fun loginOut() {
    }

    /**
     * Called when a view has been clicked.
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_wallet -> ARouterCenter.goWalletActivity(0)
            R.id.tv_address -> ARouterCenter.goAddressManageActivity()
            R.id.tv_add_community ->  getString(R.string.no_function).toast() // mContext.start(JoinCommunitiesActivity::class.java)
            R.id.tv_setting -> mContext.start(SettingActivity::class.java)
            R.id.tv_help -> mContext.start(HelpCenterActivity::class.java)
            R.id.tv_share ->  getString(R.string.no_function).toast()
            R.id.tv_about_us -> mContext.start(AboutUsActivity::class.java)
            R.id.tv_login_out -> { showSetUpDialog() }
        }
    }

    private fun showSetUpDialog(){
        if (pwdDialog == null) {
            pwdDialog = Dialog(mContext, R.style.CustomDialog)
        }
        val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_set_up_sign, null)
        val etNumber = view.findViewById<EditText>(R.id.et_number)
        val tvSure = view.findViewById<TextView>(R.id.tv_sure)
        val cbCheck = view.findViewById<CheckBox>(R.id.cb_check)
        val tvCancel = view.findViewById<TextView>(R.id.tv_cancel)
        tvSure.setOnClickListener {

            if(!mContext.sharedPref.password.equals(etNumber.text.toString())){
                getString(R.string.password_not_equals)
                return@setOnClickListener
            }

            mContext.sharedPref.publicKey = ""
            mContext.sharedPref.privateKey = ""
            mContext.sharedPref.password = ""
            mContext.sharedPref.name = ""
            mContext.sharedPref.mnemoinc = ""

            DataSupport.deleteAll(BaseData::class.java)

            var intent = Intent(mContext,CreateWalletIdIndex::class.java)
            startActivity(intent)
            pwdDialog!!.dismiss()
        }
        tvCancel.setOnClickListener{ pwdDialog!!.dismiss() }
        pwdDialog!!.setContentView(view)
        pwdDialog!!.setCanceledOnTouchOutside(false)
        pwdDialog!!.setCancelable(true)
        pwdDialog!!.show()
    }

}
