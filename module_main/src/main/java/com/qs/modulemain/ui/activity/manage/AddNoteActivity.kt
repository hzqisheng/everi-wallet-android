package com.qs.modulemain.ui.activity.manage

import com.qs.modulemain.R
import com.smallcat.shenhai.mvpbase.base.SimpleActivity

class AddNoteActivity : SimpleActivity() {

    override val layoutId: Int
        get() = R.layout.activity_add_note

    override fun initData() {
        tvTitle?.text = getString(R.string.new_note)
    }


}
