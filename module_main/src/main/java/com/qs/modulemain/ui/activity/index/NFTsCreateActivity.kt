package com.qs.modulemain.ui.activity.index

import com.qs.modulemain.R
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.start
import kotlinx.android.synthetic.main.activity_add_fts.*

class NFTsCreateActivity : SimpleActivity() {

    override val layoutId: Int
        get() = R.layout.activity_nfts_create

    override fun initData() {
        tvTitle?.text = getString(R.string.create_nfts)
        ivRight?.apply {
            setBackgroundResource(R.drawable.ic_question)
            setOnClickListener{}
        }
        tv_sure.setOnClickListener { start(NFTsEditActivity::class.java) }

    }

}
