package com.qs.modulemain.ui.activity.index

import com.qs.modulemain.R
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import kotlinx.android.synthetic.main.activity_nfts_detail.*

class NFTsDetailActivity : SimpleActivity() {

    override val layoutId: Int
        get() = R.layout.activity_nfts_detail

    override fun initData() {
        val domainName = intent.getStringExtra("domain")
        val tokenName = intent.getStringExtra("token")
        tvTitle?.text = domainName
        tv_name.text = tokenName
        tv_domain.text = domainName
        tv_public_key.text = sharedPref.publicKey
    }
}
