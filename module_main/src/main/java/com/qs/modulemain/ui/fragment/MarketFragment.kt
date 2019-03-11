package com.qs.modulemain.ui.fragment


import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.qs.modulemain.R
import com.qs.modulemain.bean.AddressBean
import com.qs.modulemain.presenter.AddressManagePresenter
import com.qs.modulemain.ui.activity.my.AddAddressActivity
import com.qs.modulemain.ui.activity.my.ExportAddressActivity
import com.qs.modulemain.ui.activity.my.ImportAddressActivity
import com.qs.modulemain.ui.adapter.AddressAdapter
import com.qs.modulemain.util.DataUtils
import com.smallcat.shenhai.mvpbase.base.SimpleFragment
import com.smallcat.shenhai.mvpbase.extension.getResourceColor
import com.smallcat.shenhai.mvpbase.extension.start
import kotlinx.android.synthetic.main.fragment_market.*
import org.litepal.crud.DataSupport
import org.w3c.dom.Text
import java.util.ArrayList


class MarketFragment : SimpleFragment() {

    private var adapter: AddressAdapter? = null
    private var dataList: ArrayList<AddressBean> = ArrayList()

    override val layoutId: Int
        get() = R.layout.fragment_market

    override fun initData() {
//        tvTitle?.text = getString(R.string.address_manage)
//
//        tvRight?.apply {
//            text = getString(R.string.export)
//            setTextColor(getResourceColor(R.color.color_e4))
//            setOnClickListener{
//                start(ExportAddressActivity::class.java)
//            }
//        }
        mView.findViewById<ImageView>(R.id.iv_back).visibility = View.GONE
        mView.findViewById<TextView>(R.id.tv_title).text = getText(R.string.address_manage)

        tv_add.setOnClickListener {
            val intent = Intent(mContext, AddAddressActivity::class.java)
            startActivity(intent)
        }
        tv_import.setOnClickListener {
            val intent = Intent(mContext, ImportAddressActivity::class.java)
            startActivity(intent)
        }

        val tvRight = mView.findViewById<TextView>(R.id.tv_right)
        tvRight?.apply {
            text = getString(R.string.export)
            setTextColor(mContext.getResourceColor(R.color.color_e4))
            setOnClickListener {
                mContext.start(ExportAddressActivity::class.java)
            }
        }

        adapter = AddressAdapter(dataList)
        adapter?.emptyView = DataUtils.getEmptyView(mContext, getString(R.string.no_address))

        val footView = View(mContext)
        val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 60)
        footView.layoutParams = layoutParams
        adapter!!.addFooterView(footView)
        rv_list.adapter = adapter

    }

    override fun onResume() {
        super.onResume()
        val list: List<AddressBean> = DataSupport.findAll(AddressBean::class.java)
        dataList.clear()
        dataList.addAll(list)
        if (adapter != null)
            adapter!!.notifyDataSetChanged()
    }


}
