package com.dd.mylibrary.wedget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.dd.mylibrary.utils.ScreenUtil;


/**
 * Created by Administrator on 2018/3/22 0022.
 */

public class MyProgressView extends View {

    private Paint paint;
    private int progress = 0;
    private Context mContext;
    public MyProgressView(Context context) {
        this(context,null);
    }

    public MyProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        mContext = context;
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = ScreenUtil.dip2px(mContext,5);
        paint.setColor(Color.parseColor("#f8f7fc"));
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
        paint.setColor(Color.parseColor("#00a0e9"));
        canvas.drawRect(0, 0, (float) (getWidth()*((float)progress/100)), getHeight(), paint);
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        postInvalidate();
    }
}
