package com.smallcat.shenhai.mvpbase.model.http

import com.smallcat.shenhai.mvpbase.App
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import io.reactivex.Observable
import io.reactivex.functions.Function
import java.util.concurrent.TimeUnit

/**
 * @author hui
 * @date 2018/4/27
 */
object ApiConfig {

    const val BASE_URL = "https://test-jx-user.yuanduidui.cn/"
    const val WEB_URL = "https://test-jx-user.yuanduidui.cn/#/"
    const val LOCAL_WEB_URL = "http://192.168.0.187:8100/#/"
    //服务协议地址
    const val SERVICE_URL = "https://test-jx-back.yuanduidui.cn/upload/protocol.html"

    const val PHONE_JX = "0573-96345"
    const val JPUSH_CODE = 2000
    const val URL_NEWS = "news" // 资讯-新闻
    const val URL_MY_ORDER = "myOrder" // 我的订单
    const val URL_MY_PAY_RECORD = "myPayRecord" // 我的支付记录
    const val URL_MY_ADDRESS = "myAddress" //myAddress 我的地址
    const val URL_SERVER_PUBLISH = "servicePublish" //servicePublish 服务发布
    const val URL_ORDER_DETAIL = "orderDetail" //orderDetail 订单详情

     //错误重连
     class RetryWithDelay(val maxRetries: Int = 3, val delayMillis: Long = 2000L) : Function<Observable<in Throwable>, Observable<*>> {
         var retryCount = 0

      override fun apply(observable: Observable<in Throwable>): Observable<*> = observable
              .flatMap { throwable ->
               if (++retryCount < maxRetries) {
                //重连每次间隔默认2s
                Observable.timer(delayMillis, TimeUnit.MILLISECONDS)
               } else {
                Observable.error(throwable as Throwable)
               }
              }
     }
}