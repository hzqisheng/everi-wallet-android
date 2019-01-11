package com.qs.modulemain.ui.activity.my

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.AdapterView
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.qs.modulemain.R
import com.qs.modulemain.arouter.ARouterConfig
import com.qs.modulemain.bean.AddressBean
import com.qs.modulemain.presenter.AddressManagePresenter
import com.qs.modulemain.ui.activity.index.RecordActivity
import com.qs.modulemain.ui.adapter.AddressAdapter
import com.qs.modulemain.view.AddressManageView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.extension.getResourceColor
import com.smallcat.shenhai.mvpbase.extension.start
import kotlinx.android.synthetic.main.activity_address_manage.*
import org.litepal.crud.DataSupport
import java.util.ArrayList

@Route(path = ARouterConfig.MY_ADDRESS)
class AddressManageActivity : BaseActivity<AddressManagePresenter>(),AddressManageView {
    private lateinit var adapter: AddressAdapter
    private lateinit var dataList:ArrayList<AddressBean>
    private var isNeedBack : Boolean = false

    override fun initPresenter() {
        mPresenter = AddressManagePresenter(mContext)
    }

    override val layoutId: Int
        get() = R.layout.activity_address_manage

    override fun initData() {
        tvTitle?.text = getString(R.string.address_manage)

        if(intent.hasExtra("isNeedBack")){
            isNeedBack = true;
        }

        tvRight?.apply {
            text = getString(R.string.export)
            setTextColor(getResourceColor(R.color.color_e4))
            setOnClickListener{
                start(ExportAddressActivity::class.java)
            }
        }

        tv_add.setOnClickListener {
            start(AddAddressActivity::class.java)
        }
        tv_import.setOnClickListener {
            start(ImportAddressActivity::class.java)
        }

        dataList = ArrayList();
        adapter = AddressAdapter(dataList);

        var footView = View(this)
        var layoutParams = ViewGroup.LayoutParams(MATCH_PARENT,60)
        footView.layoutParams = layoutParams;
        adapter.addFooterView(footView)
        rv_list.adapter = adapter

        adapter.onItemChildClickListener = object : AdapterView.OnItemClickListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {

            }

            override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                if(isNeedBack) {
                    var intent = Intent(mContext, RecordActivity::class.java)
                    intent.putExtra("data", dataList[position].address)
                    setResult(1, intent)
                    finish()
                }
            }

            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }

        }

    }

    override fun onResume() {
        super.onResume()
        var list:List<AddressBean> = DataSupport.findAll(AddressBean::class.java)
        dataList.clear()
        dataList.addAll(list)
        adapter.notifyDataSetChanged()
    }


    override fun loadSuccess(data: Any) {

    }
}
