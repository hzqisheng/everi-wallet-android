package com.qs.modulemain.ui.fragment


import android.content.Intent
import android.view.View
import com.google.gson.Gson
import com.qs.modulemain.R
import com.qs.modulemain.arouter.ARouterCenter
import com.qs.modulemain.bean.VersionBean
import com.qs.modulemain.presenter.MyPresenter
import com.qs.modulemain.ui.activity.index.CreateWalletIdIndex
import com.qs.modulemain.ui.activity.my.AboutUsActivity
import com.qs.modulemain.ui.activity.my.HelpCenterActivity
import com.qs.modulemain.ui.activity.my.JoinCommunitiesActivity
import com.qs.modulemain.ui.activity.my.SettingActivity
import com.qs.modulemain.util.confirmPassword
import com.qs.modulemain.view.MyView
import com.smallcat.shenhai.mvpbase.App
import com.smallcat.shenhai.mvpbase.base.BaseFragment
import com.smallcat.shenhai.mvpbase.base.FingerSuccessCallback
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.start
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.bean.BaseData
import com.smallcat.shenhai.mvpbase.model.helper.MessageEvent
import com.smallcat.shenhai.mvpbase.model.helper.RxBus
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import com.smallcat.shenhai.mvpbase.model.http.ApiConfig
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_my.*
import org.litepal.crud.DataSupport


class MyFragment : BaseFragment<MyPresenter>(), MyView, View.OnClickListener {

    override fun initPresenter() {
        mPresenter = MyPresenter(mContext)
    }

    override val layoutId: Int
        get() = R.layout.fragment_my

    override fun initData() {
        tv_wallet.setOnClickListener(this)
        tv_add_community.setOnClickListener(this)
        tv_help.setOnClickListener(this)
        tv_setting.setOnClickListener(this)
        tv_share.setOnClickListener(this)
        tv_about_us.setOnClickListener(this)
        tv_login_out.setOnClickListener(this)
        addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    when (it.type) {
                        RxBusCenter.CHECK_VERSION -> checkSuccess(it.msg)
                    }
                })
    }

    /**
     * Called when a view has been clicked.
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_wallet -> ARouterCenter.goWalletActivity(0)
            R.id.tv_add_community -> mContext.start(JoinCommunitiesActivity::class.java)
            R.id.tv_setting -> mContext.start(SettingActivity::class.java)
            R.id.tv_help -> mContext.start(HelpCenterActivity::class.java)
            R.id.tv_share -> mWebView.evaluateJavascript(WebViewApi.getAPPVersion(), null)
            R.id.tv_about_us -> mContext.start(AboutUsActivity::class.java)
            R.id.tv_login_out -> {
                showFingerPrintDialog()
            }
        }
    }


    private fun checkSuccess(msg: String) {
        val bean = Gson().fromJson(msg, VersionBean::class.java)
        Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "MyEVT")
            putExtra(Intent.EXTRA_TEXT, bean.androidUploadUrl /*ApiConfig.SHARE_URL*/)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(Intent.createChooser(this, "分享到"))
        }
    }

    private fun showFingerPrintDialog() {
        confirmPassword(mContext.sharedPref.isFinger, childFragmentManager, object : FingerSuccessCallback() {
            override fun onCheckSuccess() {
                mContext.sharedPref.publicKey = ""
                mContext.sharedPref.privateKey = ""
                mContext.sharedPref.password = ""
                mContext.sharedPref.name = ""
                mContext.sharedPref.mnemoinc = ""
                mContext.sharedPref.isFinger = 0

                DataSupport.deleteAll(BaseData::class.java)
                App.getInstance().finishAllActivity()
                val intent = Intent(mContext, CreateWalletIdIndex::class.java)
                startActivity(intent)
            }
        })
    }
}
