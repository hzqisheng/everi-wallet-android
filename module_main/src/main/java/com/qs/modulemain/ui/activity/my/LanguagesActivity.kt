package com.qs.modulemain.ui.activity.my

import android.view.View
import com.qs.modulemain.R
import com.qs.modulemain.ui.activity.MainActivity
import com.smallcat.shenhai.mvpbase.App
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.getResourceString
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.start
import com.smallcat.shenhai.mvpbase.utils.LocalManageUtil
import kotlinx.android.synthetic.main.activity_languages.*

class LanguagesActivity : SimpleActivity() {

    override val layoutId: Int
        get() = R.layout.activity_languages

    override fun initData() {
        tvTitle?.text = getResourceString(R.string.languages)
        ll_chinese.setOnClickListener {
            sharedPref.languages = 0
            changeLanguage()
            iv_choose1.visibility = View.GONE
            iv_choose2.visibility = View.VISIBLE
        }
        ll_english.setOnClickListener {
            sharedPref.languages = 1
            changeLanguage()
            iv_choose1.visibility = View.VISIBLE
            iv_choose2.visibility = View.GONE
        }
        if(sharedPref.languages == 0){
            iv_choose2.visibility = View.VISIBLE
        }else{
            iv_choose1.visibility = View.VISIBLE
        }
    }

    private fun changeLanguage(){
        LocalManageUtil.setLocal(mContext)
        App.getInstance().finishAllActivityExcept(this)
        start(MainActivity::class.java)
    }
}
