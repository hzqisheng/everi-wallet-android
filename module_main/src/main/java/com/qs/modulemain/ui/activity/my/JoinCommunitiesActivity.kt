package com.qs.modulemain.ui.activity.my

import com.qs.modulemain.R
import com.qs.modulemain.arouter.ARouterCenter
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.utils.addClipboard
import kotlinx.android.synthetic.main.activity_join_communities.*

class JoinCommunitiesActivity : SimpleActivity() {

    override val layoutId: Int
        get() = R.layout.activity_join_communities

    override fun initData() {
        tvTitle?.text = getString(R.string.add_community)

        ll_face_book.setOnClickListener { ARouterCenter.goWebViewActivity("https://www.facebook.com/everiToken") }
        ll_twitter.setOnClickListener { ARouterCenter.goWebViewActivity("https://twitter.com/EveriToken") }
        ll_telegram.setOnClickListener { ARouterCenter.goWebViewActivity("https://t.me/everiToken") }
        ll_telegram_chinese.setOnClickListener { ARouterCenter.goWebViewActivity("https://t.me/everiTokenCNofficial") }
        ll_telegram_russian.setOnClickListener { ARouterCenter.goWebViewActivity("https://t.me/everitokenru") }
        ll_wechat.setOnClickListener {
            addClipboard(mContext, "everiToken")
            getString(R.string.copyed_please_search).toast()
        }
    }


}
