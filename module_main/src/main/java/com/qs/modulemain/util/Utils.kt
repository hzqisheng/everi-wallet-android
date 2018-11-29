package com.qs.modulemain.util

import android.content.Context
import android.support.design.widget.TabLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.luck.picture.lib.tools.ScreenUtils.dip2px



fun reflex(tabLayout: TabLayout) {
    //了解源码得知 线的宽度是根据 tabView的宽度来设置的
    tabLayout.post {
        try {
            //拿到tabLayout的mTabStrip属性
            val mTabStrip = tabLayout.getChildAt(0) as LinearLayout

            val dp10 = dip2px(tabLayout.context, 10f)

            for (i in 0 until mTabStrip.childCount) {
                val tabView = mTabStrip.getChildAt(i)

                //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                val mTextViewField = tabView.javaClass.getDeclaredField("TextView")
                mTextViewField.isAccessible = true

                val mTextView = mTextViewField.get(tabView) as TextView

                tabView.setPadding(0, 0, 0, 0)

                //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                var width = mTextView.width
                if (width == 0) {
                    mTextView.measure(0, 0)
                    width = mTextView.measuredWidth
                }

                //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                val params = tabView.layoutParams as LinearLayout.LayoutParams
                params.width = width
                params.leftMargin = dp10
                params.rightMargin = dp10
                tabView.layoutParams = params

                tabView.invalidate()
            }

        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

}


/**
 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
 */
fun dip2px(context: Context, dpValue: Float): Int {
    val scale = context.resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

// 将px值转换为sp值
fun px2sp(context: Context, pxValue: Float): Int {
    val fontScale = context.resources.displayMetrics.scaledDensity
    return (pxValue / fontScale + 0.5f).toInt()
}

// 将px值转换为dip或dp值
fun px2dip(context: Context, pxValue: Float): Int {
    val scale = context.resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}