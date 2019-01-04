package com.qs.modulemain.ui.activity.index

import android.content.Intent
import com.qs.modulemain.R
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.start
import kotlinx.android.synthetic.main.activity_nfts_edit.*

class NFTsEditActivity : SimpleActivity() {

    override val layoutId: Int
        get() = R.layout.activity_nfts_edit

    override fun initData() {
        tvTitle?.text = getString(R.string.create_nfts)
        ivRight?.apply {
            setBackgroundResource(R.drawable.ic_question)
            setOnClickListener{}
        }
        tv_sure.setOnClickListener {
            var intent = Intent()
            intent.putExtra("result",rv_list.text.toString())
            setResult(1,intent)
            finish()
        }
    }

}
