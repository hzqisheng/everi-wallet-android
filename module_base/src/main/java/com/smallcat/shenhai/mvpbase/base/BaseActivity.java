package com.smallcat.shenhai.mvpbase.base;


import com.smallcat.shenhai.mvpbase.extension.StringExtensionKt;

import static com.smallcat.shenhai.mvpbase.utils.BaseUtilKt.isNetworkAvailable;

/**
 *
 * @author hui
 * @date 2018/4/27
 */

public abstract class BaseActivity<T extends IPresenter> extends SimpleActivity implements BaseView {

    protected T mPresenter;

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        initPresenter();
        if (mPresenter != null) {
            //添加LifecycleObserver
            getLifecycle().addObserver(mPresenter);
            mPresenter.attachView(this);
        }
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