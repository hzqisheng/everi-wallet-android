package com.qs.modelthird.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;


/**
 *
 * @author twh
 * @date 18/1/9
 */

public class ProgressWebView extends WebView {

    private ProgressView mProgressBar;

    private WebView mWebView;

    public ProgressWebView(Context context) {
        this(context, null);
    }

    public ProgressWebView(Context context, AttributeSet attrs) {
        this(context, attrs,  android.R.attr.webViewStyle);
    }

    public ProgressWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mProgressBar = new ProgressView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mProgressBar.setLayoutParams(layoutParams);
        mProgressBar.setVisibility(View.GONE);
        addView(mProgressBar);
        mWebView = this;
        mWebView.setLayerType(View.LAYER_TYPE_HARDWARE,null);
        initSetting();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initSetting() {

        // 初始化设置
        WebSettings mSettings = this.getSettings();
        mSettings.setJavaScriptEnabled(true);//开启javascript
        mSettings.setJavaScriptCanOpenWindowsAutomatically(true); //没有用户交互的情况下，是否JavaScript可以打开windows
        mSettings.setDomStorageEnabled(true);//开启DOM
        mSettings.setDefaultTextEncodingName("utf-8");//设置字符编码
        //设置web页面
        mSettings.setAppCacheEnabled(true);
        mSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        mSettings.setAllowFileAccess(true);//设置支持文件流
        mSettings.setSupportZoom(true);// 支持缩放
        mSettings.setBuiltInZoomControls(true);// 支持缩放
        mSettings.setUseWideViewPort(true);// 调整到适合webview大小
        mSettings.setLoadWithOverviewMode(true);// 调整到适合webview大小
        mSettings.setDisplayZoomControls(false);
        mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        mSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        //提高网页加载速度，暂时阻塞图片加载，然后网页加载好了，在进行加载图片（true）
        mSettings.setBlockNetworkImage(false);
        mSettings.setAppCacheEnabled(true);//开启缓存机制
        setWebViewClient(new MyWebClient());
        setWebChromeClient(new MyWebChromeClient());
        //调试模式
        WebView.setWebContentsDebuggingEnabled(true);
    }



    /**
     * 自定义WebChromeClient
     */
    public class MyWebChromeClient extends WebChromeClient {
        /**
         * 进度改变的回掉
         *
         * @param view        WebView
         * @param newProgress 新进度
         */
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                mProgressBar.setProgress(100);
                new Handler().postDelayed(runnable, 200);//0.2秒后隐藏进度条
            } else if (mProgressBar.getVisibility() == GONE) {
                mProgressBar.setVisibility(VISIBLE);
            }
            //设置初始进度10，这样会显得效果真一点，总不能从1开始吧
            if (newProgress < 10) {
                newProgress = 10;
            }
            //不断更新进度
            mProgressBar.setProgress(newProgress);
            super.onProgressChanged(view, newProgress);
        }
    }
    public class MyWebClient extends WebViewClient {
        /**
         * 加载过程中 拦截加载的地址url
         *
         * @param view
         * @param url  被拦截的url
         * @return
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url == null) return false;

            try{
                if(!url.startsWith("http://") && !url.startsWith("https://")){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    getContext().startActivity(intent);
                    return true;
                }
            }catch (Exception e){//防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                return true;//没有安装该app时，返回true，表示拦截自定义链接，但不跳转，避免弹出上面的错误页面
            }

            // TODO Auto-generated method stub
            //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
            view.loadUrl(url);
            return true;
        }
        /**
         * 页面加载过程中，加载资源回调的方法
         *
         * @param view
         * @param url
         */
        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }
        /**
         * 页面加载完成回调的方法
         *
         * @param view
         * @param url
         */
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            // 关闭图片加载阻塞
            view.getSettings().setBlockNetworkImage(false);
            /*if (mWebView.canGoBack()){
                mWebView.goBack();
            }*/
        }
        /**
         * 页面开始加载调用的方法
         * @param view
         * @param url
         * @param favicon
         */
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
            super.onReceivedSslError(view, handler, error);
        }

        @Override
        public void onScaleChanged(WebView view, float oldScale, float newScale) {
            super.onScaleChanged(view, oldScale, newScale);
            ProgressWebView.this.requestFocus();
            ProgressWebView.this.requestFocusFromTouch();
        }
    }
    /**
     *刷新界面（此处为加载完成后进度消失）
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mProgressBar.setVisibility(View.GONE);
        }
    };


}
