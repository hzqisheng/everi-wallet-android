package com.qs.modulemain.ui.activity.manage

import android.content.Intent
import com.qs.modulemain.R
import com.qs.modulemain.ui.activity.index.ScanActivity
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.toast
import kotlinx.android.synthetic.main.activity_add_group.*
import kotlinx.android.synthetic.main.activity_add_note.*
import kotlinx.android.synthetic.main.activity_add_note.et_number
import kotlinx.android.synthetic.main.activity_add_note.iv_add
import kotlinx.android.synthetic.main.activity_add_note.iv_reduce
import kotlinx.android.synthetic.main.activity_add_note.tv_name

class AddGroupActivity : SimpleActivity() {

    companion object {
        var resultCode = 0x102
    }

    override val layoutId: Int
        get() = R.layout.activity_add_group

    override fun initData() {
        tvTitle?.text = getString(R.string.add_group)

        tv_name.setOnClickListener {
            val intent = Intent(this@AddGroupActivity, MyGroupActivity::class.java)
            intent.putExtra("isAddGroup", true)
            startActivityForResult(intent, 3)
        }

        iv_reduce.setOnClickListener {
            try {
                if (et_number.text.toString().toInt() <= 1) {
                    return@setOnClickListener
                }
                et_number.setText(((et_number.text.toString().toInt()) - 1).toString())
            } catch (e: Exception) {
                e.printStackTrace()
                et_number.setText("1")
            }
        }
        iv_add.setOnClickListener {
            try {
                et_number.setText(((et_number.text.toString().toInt()) + 1).toString())
            } catch (e: Exception) {
                e.printStackTrace()
                et_number.setText("1")
            }
        }
        tv_sure.setOnClickListener {
            if (tv_name.text.isNullOrEmpty()) {
                getString(R.string.choose_group).toast()
                return@setOnClickListener
            }
            val intent = Intent()
            intent.putExtra("groupName", tv_name.text)
            intent.putExtra("weight", getWeight())
            setResult(resultCode, intent)
            finish()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) return
        if (resultCode == MyGroupActivity.resultCode) {
            val groupName = data!!.getStringExtra("groupName")
            tv_name.text = groupName
        }
    }

    private fun getWeight(): Int {
        return try {
            et_number.text.toString().toInt()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            1
        }
    }

}

