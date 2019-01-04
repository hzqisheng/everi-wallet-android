package com.smallcat.shenhai.mvpbase.utils

import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.IOException


/**
 * 作者： MirsFang on 2018/12/15 13:02
 * 邮箱： mirsfang@163.com
 * 类描述：
 */




fun sizeCompres(path: String, rqsW: Int, rqsH: Int): Bitmap {
    // 用option设置返回的bitmap对象的一些属性参数
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true// 设置仅读取Bitmap的宽高而不读取内容
    BitmapFactory.decodeFile(path, options)// 获取到图片的宽高，放在option里边
    val height = options.outHeight//图片的高度放在option里的outHeight属性中
    val width = options.outWidth
    var  inSampleSize:Int
    if (rqsW == 0 || rqsH == 0) {
        options.inSampleSize = 1
    } else if (height > rqsH || width > rqsW) {
        val heightRatio = Math.round(height.toFloat() / rqsH.toFloat())
        val widthRatio = Math.round(width.toFloat() / rqsW.toFloat())
        inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
        options.inSampleSize = inSampleSize
    }
    return BitmapFactory.decodeFile(path, options)// 主要通过option里的inSampleSize对原图片进行按比例压缩
}

/**
 * bitmap转为base64
 *
 * @param bitmap
 * @return
 */
fun bitmapToBase64(bitmap: Bitmap?): String? {

    var result: String? = null
    var baos: ByteArrayOutputStream? = null
    try {
        if (bitmap != null) {
            baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)

            baos.flush()
            baos.close()

            val bitmapBytes = baos.toByteArray()
            result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        try {
            if (baos != null) {
                baos.flush()
                baos.close()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
    return result
}


fun base64ToBitmap(base64Data: String): Bitmap {
    val bytes = Base64.decode(base64Data, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}







