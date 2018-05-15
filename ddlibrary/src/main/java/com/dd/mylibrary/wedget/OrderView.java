package com.dd.mylibrary.wedget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.dd.mylibrary.utils.ScreenUtil;


/**
 * Created by Administrator on 2018/3/21 0021.
 */

public class OrderView extends View {

    private int with;
    private int height;
    private int swepAngle = 0;
    private Paint totalPaint;
    private Paint currentPaint;
    private Paint centerPaint;
    private int xC;
    private int yC;
    private int radious;
    private int textSize = 40;

    private String totalOrder = "0单";
    private String currentOrder = "0单";
    private String month = "- 月";
    private String total = "0";
    private Paint txtPaint;
    private Context context;

    public OrderView(Context context) {
        this(context, null);
    }

    public OrderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OrderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        with = getWidth();
        height = getHeight();
        totalPaint = new Paint();
        totalPaint.setAntiAlias(true);
        totalPaint.setColor(Color.parseColor("#00a0e9"));
        totalPaint.setStyle(Paint.Style.FILL);

        currentPaint = new Paint();
        currentPaint.setAntiAlias(true);
        currentPaint.setColor(Color.parseColor("#00bae9"));
        currentPaint.setStyle(Paint.Style.FILL);

        centerPaint = new Paint();
        centerPaint.setAntiAlias(true);
        centerPaint.setColor(Color.parseColor("#ffffff"));
        centerPaint.setStyle(Paint.Style.FILL);

        txtPaint = new Paint();
        txtPaint.setAntiAlias(true);
        txtPaint.setColor(Color.parseColor("#666666"));

        textSize = setSize(15);

        xC = with / 2;
        yC = height / 2;
        radious = xC > yC ? yC : xC;


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        with = getWidth();
        height = getHeight();
        xC = with / 2;
        yC = height / 2;
        radious = (int) ((xC > yC ? yC : xC) / 1.5);
        canvas.drawCircle(xC, yC, radious, totalPaint);

        RectF oval = new RectF(xC - radious, yC - radious, xC + radious, yC + radious);
        canvas.drawArc(oval, 270, swepAngle, true, currentPaint);

        canvas.drawCircle(xC, yC, radious / 2, centerPaint);

        drawCenterText(canvas, "订单数", xC, yC);

        //画上方的小圆圈
        drawSmallCircle(canvas, xC + setSize(25), yC - radious - ScreenUtil.dip2px(context, 5), ScreenUtil.dip2px(context, 5), currentPaint);



        //画上方斜线
        drawLine(canvas, xC +setSize(25), yC - radious - setSize(5), xC + setSize(40), yC - radious - ScreenUtil.dip2px(context, 20), currentPaint);
        //画上方直线
        drawLine(canvas, xC + setSize(40), yC - radious - setSize(20), xC + setSize(135), yC - radious - setSize(20), currentPaint);
        //画当前订单数量
        drawRightText(canvas, currentOrder, xC + setSize(135), yC - radious - setSize(23));
        //画月份
        drawRightText(canvas, month, xC + setSize(135), yC - radious-setSize(4));


        //画下方的小圆圈
        drawSmallCircle(canvas, xC, yC + radious + ScreenUtil.dip2px(context, 10), setSize(5), totalPaint);
        //画下方斜线
        drawLine(canvas, xC, yC + radious+setSize(10), xC + setSize(15), yC + radious + setSize(25), totalPaint);
        //画下方直线
        drawLine(canvas, xC + setSize(15), yC + radious + setSize(25), xC + setSize(110), yC + radious + setSize(25), totalPaint);

        //画总单数量
        drawRightText(canvas, totalOrder, xC + setSize(110), yC + radious + setSize(22));
        //
        drawRightText(canvas, "累计", xC + setSize(110), yC + radious + setSize(40));

    }

    private void drawSmallCircle(Canvas canvas, int xfloat, int yfloat, int radius, Paint paint) {
        canvas.drawCircle(xfloat, yfloat, radius, paint);
    }

    private void drawLine(Canvas canvas, int startX, int startY, int endX, int endY, Paint paint) {
        canvas.drawLine(startX, startY, endX, endY, paint);
    }

    private void drawCenterText(Canvas canvas, String text, int centerX, int centerY) {
//        Paint txtPaint = new Paint();

        //在圆的中心绘制文字
        txtPaint.setColor(Color.parseColor("#00a0e9"));
        txtPaint.setTextSize(textSize);

        /*
        根据前3个参数截取字符串，将该字符串需要占据的宽高大小存入参数4对应的Rect对象中
         */
        Rect rect = new Rect();
        txtPaint.getTextBounds(text, 0, text.length(), rect);
        int textW = rect.width();
        int textH = rect.height();

        canvas.drawText(text, centerX - textW / 2, centerY + textH / 2, txtPaint);
    }

    private void drawRightText(Canvas canvas, String text, int centerX, int centerY) {
        txtPaint.setColor(Color.parseColor("#666666"));
        txtPaint.setTextSize(textSize);
        Rect rect = new Rect();
        txtPaint.getTextBounds(text, 0, text.length(), rect);
        int textW = rect.width();
        int textH = rect.height();

        canvas.drawText(text, centerX - textW, centerY, txtPaint);
    }

    private int setSize(int size) {
        return ScreenUtil.dip2px(context, size);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int withMode = MeasureSpec.getMode(widthMeasureSpec);
        switch (withMode) {
            case MeasureSpec.AT_MOST:
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(getxC(), MeasureSpec.EXACTLY);
                break;
        }

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        switch (heightMode) {
            case MeasureSpec.AT_MOST:
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(getyC(), MeasureSpec.EXACTLY);
                break;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    public int getxC() {
        return xC;
    }

    public void setxC(int xC) {
        this.xC = xC;
    }

    public int getyC() {
        return yC;
    }

    public void setyC(int yC) {
        this.yC = yC;
    }

    public int getRadious() {
        return radious;
    }

    public void setRadious(int radious) {
        this.radious = radious;
    }

    public int getSwepAngle() {
        return swepAngle;
    }

    public void setSwepAngle(int swepAngle) {
        this.swepAngle = swepAngle;
    }

    public String getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(String totalOrder) {
        this.totalOrder = totalOrder;
    }

    public String getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(String currentOrder) {
        this.currentOrder = currentOrder;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void refresh() {
        postInvalidate();
    }
}
