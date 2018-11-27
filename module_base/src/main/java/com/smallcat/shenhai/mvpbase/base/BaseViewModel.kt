package com.smallcat.shenhai.mvpbase.base

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

/**
 * @author smallCut
 * @date 2018/11/6
 */
abstract class BaseViewModel : ViewModel(){
    var mCurrent: MutableLiveData<String>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
            }
            return field
        }

}