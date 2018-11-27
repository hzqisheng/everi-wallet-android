package com.smallcat.shenhai.mvpbase.utils

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.smallcat.shenhai.mvpbase.R
import com.youth.banner.loader.ImageLoader

/**
 * Created by hui on 2018/5/11.
 */
class GlideImageLoader : ImageLoader() {

    override fun displayImage(context: Context, path: Any, imageView: ImageView) {
        Glide.with(context).load(path).apply(options).into(imageView)
    }

    companion object {

        private val options = RequestOptions()
                .placeholder(R.color.color_ef)

        fun loadImage(context: Context, path: Any?, imageView: ImageView) {
            Glide.with(context).load(path).apply(options).into(imageView)
        }

        fun loadOneImage(context: Context, path: Any?, simpleTarget: SimpleTarget<Bitmap>) {
            Glide.with(context).asBitmap().load(path).apply(options).into(simpleTarget)
        }
    }

}


