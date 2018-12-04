package com.qs.modulemain.ui.activity.manage

import android.app.Dialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import com.qs.modulemain.R
import com.qs.modulemain.presenter.CreateGroupPresenter
import com.qs.modulemain.ui.adapter.ChooseAdapter
import com.qs.modulemain.view.CreateGroupView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.extension.start
import com.smallcat.shenhai.mvpbase.model.bean.ChooseBean
import kotlinx.android.synthetic.main.activity_create_group.*

class CreateGroupActivity : BaseActivity<CreateGroupPresenter>(), CreateGroupView {

    private var dialog:Dialog? = null
    private var mChoosePosition = 0
    private var list = arrayListOf(ChooseBean(getString(R.string.I_am_in_charge_of_management)),
            ChooseBean(getString(R.string.from_adress_book_choose)), ChooseBean(getString(R.string.hand_input_pub_key)))

    override fun initPresenter() {
        mPresenter = CreateGroupPresenter(mContext)
    }

    override val layoutId: Int = R.layout.activity_create_group

    override fun initData() {
        tvTitle?.text = getString(R.string.create_group)
        et_manage.setOnClickListener { showDialog() }
        iv_add_note.setOnClickListener { start(AddNoteActivity::class.java) }
        tv_sure.setOnClickListener { start(CreateGroup2Activity::class.java) }
    }

    private fun showDialog(){
        if (dialog == null) {
            dialog = Dialog(mContext, R.style.CustomDialog)
        }
        val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_group_create, null)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_list)
        val adapter = ChooseAdapter(list)
        mChoosePosition = 0
        adapter.setOnItemClickListener { _, _, position ->
            list[mChoosePosition].isSelected = false
            mChoosePosition = position
            list[mChoosePosition].isSelected = true
            adapter.setNewData(list)
            dialog?.dismiss()
            et_manage.setText(list[position].title)
        }
        recyclerView.adapter = adapter
        dialog!!.setContentView(view)
        dialog!!.setCanceledOnTouchOutside(false)
        dialog!!.setCancelable(true)
        dialog!!.show()
    }

    override fun onDestroy() {
        if (dialog!=null || dialog!!.isShowing){
            dialog!!.dismiss()
        }
        super.onDestroy()
    }
}
