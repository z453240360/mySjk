package com.dd.mylibrary.wedget.photoview;

import android.graphics.PointF;
import android.graphics.RectF;
import android.widget.ImageView;

/**
 * Created by Administrator on 2018/3/12 0012.
 */

public class Info {

    // 内部图片在整个手机界面的位置
    public RectF mRect = new RectF();

    // 控件在窗口的位置
    public RectF mImgRect = new RectF();

    public RectF mWidgetRect = new RectF();

    RectF mBaseRect = new RectF();

    PointF mScreenCenter = new PointF();

    float mScale;

    public float mDegrees;

    public ImageView.ScaleType mScaleType;

    public Info(RectF rect, RectF img, RectF widget, RectF base, PointF screenCenter, float scale, float degrees, ImageView.ScaleType scaleType) {
        mRect.set(rect);
        mImgRect.set(img);
        mWidgetRect.set(widget);
        mScale = scale;
        mScaleType = scaleType;
        mDegrees = degrees;
        mBaseRect.set(base);
        mScreenCenter.set(screenCenter);
    }
}