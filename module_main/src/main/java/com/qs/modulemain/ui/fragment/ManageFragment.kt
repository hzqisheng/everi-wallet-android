package com.qs.modulemain.ui.fragment


import com.qs.modulemain.R
import com.qs.modulemain.ui.activity.index.BatchTransfersActivity
import com.qs.modulemain.ui.activity.index.NFTsActivity
import com.qs.modulemain.ui.activity.manage.MyGroupActivity
import com.smallcat.shenhai.mvpbase.base.SimpleFragment
import com.smallcat.shenhai.mvpbase.extension.start
import com.smallcat.shenhai.mvpbase.extension.toast
import kotlinx.android.synthetic.main.fragment_manage.*


class ManageFragment : SimpleFragment() {

    override val layoutId: Int
        get() = R.layout.fragment_manage

    override fun initData() {
        tv_batch_transfer.setOnClickListener {
//            mContext.start(BatchTransfersActivity::class.java)
            getString(R.string.no_function).toast()
        }
        ll_my_nfts.setOnClickListener { mContext.start(NFTsActivity::class.java) }
        ll_my_group.setOnClickListener {
//            mContext.start(MyGroupActivity::class.java)
            getString(R.string.no_function).toast()
        }
    }

}
