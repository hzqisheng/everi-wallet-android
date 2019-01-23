package com.qs.modulemain.ui.activity.my

import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.qs.modulemain.R
import com.qs.modulemain.bean.HelpCenterBean
import com.smallcat.shenhai.mvpbase.base.SimpleActivity

class HelpCenterActivity : SimpleActivity() {

    private val list = ArrayList<HelpCenterBean>()

    override val layoutId: Int
        get() = R.layout.activity_help_center

    override fun initData() {
        tvTitle?.text = getString(R.string.help_center)
        initList()
        addView()
    }

    private fun initList() {
        list.clear()
        list.add(HelpCenterBean(getString(R.string.help_center_1), getString(R.string.help_center_1_msg)))
        list.add(HelpCenterBean(getString(R.string.help_center_2), getString(R.string.help_center_2_msg)))
        list.add(HelpCenterBean(getString(R.string.help_center_3), getString(R.string.help_center_3_msg)))
        list.add(HelpCenterBean(getString(R.string.help_center_4), getString(R.string.help_center_4_msg)))
        list.add(HelpCenterBean(getString(R.string.help_center_5), getString(R.string.help_center_5_msg)))
        list.add(HelpCenterBean(getString(R.string.help_center_6), getString(R.string.help_center_6_msg)))
        list.add(HelpCenterBean(getString(R.string.help_center_7), getString(R.string.help_center_7_msg)))
        list.add(HelpCenterBean(getString(R.string.help_center_8), getString(R.string.help_center_8_msg)))
        list.add(HelpCenterBean(getString(R.string.help_center_9), getString(R.string.help_center_9_msg)))
        list.add(HelpCenterBean(getString(R.string.help_center_10), getString(R.string.help_center_10_msg)))
        list.add(HelpCenterBean(getString(R.string.help_center_11), getString(R.string.help_center_11_msg)))
        list.add(HelpCenterBean(getString(R.string.help_center_12), getString(R.string.help_center_12_msg)))
        list.add(HelpCenterBean(getString(R.string.help_center_13), getString(R.string.help_center_13_msg)))
    }

    private fun addView() {
        val linearLayout = findViewById<LinearLayout>(R.id.ll_contain)
        for (i in list.indices) {
            val view = LayoutInflater.from(mContext).inflate(R.layout.item_help_center, null)
            val title = view.findViewById<TextView>(R.id.tv_name)
            val tvMsg = view.findViewById<TextView>(R.id.tv_msg)
            view.setOnClickListener {
                if (tvMsg.visibility == View.GONE) {
                    tvMsg.visibility = View.VISIBLE
                } else {
                    tvMsg.visibility = View.GONE
                }
            }
            title.text = list[i].title
            tvMsg.text = list[i].msg
            linearLayout.addView(view)
        }
    }
}
