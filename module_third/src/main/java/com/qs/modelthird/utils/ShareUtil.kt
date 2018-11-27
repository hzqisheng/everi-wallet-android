package com.qs.modelthird.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import com.qs.modelthird.R
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.model.http.ApiConfig
import com.smallcat.shenhai.mvpbase.utils.LogUtil
import com.tbruyelle.rxpermissions2.RxPermissions
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.umeng.socialize.media.UMWeb

/**
 * Created by hui on 2018/8/30.
 */
object ShareUtil{

    @SuppressLint("CheckResult")
    fun share(activity: Activity, url: String?, content: String?, title: String?, imgUrl: String?) {
        val rxPermissions = RxPermissions(activity)
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe { granted ->
                    if (granted!!) { // Always true pre-M
                        mShare(activity, url, content, title, imgUrl)
                    } else {
                        "分享需要读写权限".toast()
                    }
                }
    }

    private fun mShare(activity: Activity, url: String?, content: String?, title: String?, imgUrl: String?) {
        val image: UMImage
        val thumb: UMImage
        if (imgUrl != null && imgUrl != "") {
            image = UMImage(activity, ApiConfig.BASE_URL + imgUrl)
            thumb = UMImage(activity, ApiConfig.BASE_URL + imgUrl)
        } else {
            image = UMImage(activity, R.mipmap.ic_launcher)
            thumb = UMImage(activity, R.mipmap.ic_launcher)
        }
        image.setThumb(thumb)
        val web = UMWeb(url)
        web.title = title//标题
        web.setThumb(image)  //缩略图
        web.description = content//描述
        ShareAction(activity)
                .withText("分享")
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.SINA)
                .setShareboardclickCallback { snsPlatform, share_media ->
                    ShareAction(activity)
                            .setPlatform(share_media)
                            .withMedia(web)
                            .setCallback(object : UMShareListener {
                                override fun onStart(share_media: SHARE_MEDIA) {

                                }

                                override fun onResult(share_media: SHARE_MEDIA) {
                                    "分享成功".toast()
                                }

                                override fun onError(share_media: SHARE_MEDIA, throwable: Throwable?) {
                                    if (throwable != null) {
                                        LogUtil.e(throwable.message)
                                    }
                                    "分享失败".toast()
                                }

                                override fun onCancel(share_media: SHARE_MEDIA) {
                                    "分享取消".toast()
                                }
                            }).share()
                }.open()
    }
}