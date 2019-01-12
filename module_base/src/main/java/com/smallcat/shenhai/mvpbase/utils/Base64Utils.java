package com.smallcat.shenhai.mvpbase.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * 作者： MirsFang on 2018/12/15 19:56
 * 邮箱： mirsfang@163.com
 * 类描述：
 */
public class Base64Utils {

    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data.split(",")[1], Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }

}
