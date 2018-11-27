package com.smallcat.shenhai.mvpbase.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.smallcat.shenhai.mvpbase.extension.StringExtensionKt;

import static com.smallcat.shenhai.mvpbase.utils.BaseUtilKt.isNetworkAvailable;


/**
 *
 * @author hui
 * @date 2018/2/27
 */

public abstract class BaseFragment<T extends IPresenter> extends SimpleFragment implements BaseView {

    protected T mPresenter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initPresenter();
        if (mPresenter != null) {
            //添加LifecycleObserver
            getLifecycle().addObserver(mPresenter);
            mPresenter.attachView(this);
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void showErrorMsg(String msg) {
        if (msg == null){
            StringExtensionKt.toast("系统异常");
            return;
        }
        if (!isNetworkAvailable(mContext)){
            StringExtensionKt.toast("网络连接异常，请检查网络");
            return;
        }
        StringExtensionKt.toast(msg);
    }

    /**
     * 实例化Presenter
     */
    protected abstract void initPresenter();
}
