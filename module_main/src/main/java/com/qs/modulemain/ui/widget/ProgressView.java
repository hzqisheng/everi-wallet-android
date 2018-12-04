package com.qs.modulemain.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.qs.modulemain.R;


/**
 * Created by ykf on 18/1/9.
 */

public class ProgressView extends View {

    private static final int HEIGHT = 5;

    private int mProgress = 1;
    private Paint mPaint;

    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint(Paint.DITHER_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);// 填充方式为描边
        mPaint.setStrokeWidth(HEIGHT);//设置画笔的宽度
        mPaint.setAntiAlias(true);// 抗锯齿
        mPaint.setDither(true);// 使用抖动效果
        mPaint.setColor(context.getResources().getColor(R.color.color_67));//画笔设置颜色
    }

    public void setProgress(int progress) {
        mProgress = progress;
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, getWidth() * mProgress / 100, HEIGHT, mPaint);//画矩形从（0.0）开始到（progress,height）的区域
    }
}
