package com.dd.mylibrary.wedget;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dd.mylibrary.R;
import com.dd.mylibrary.utils.AnimateUtils;
import com.dd.mylibrary.utils.ScreenUtil;


public class MenuButton extends RelativeLayout {


    public boolean isShow = true;
    private ImageView mImgCenter, mImgCenter2, mImgCenter1, mImgCenter3, mImgCenter4, mImgCenter5;
    private Context mContext;
    private int radious=80;
    private int degree = 45;

    public MenuButton(Context context) {
        this(context, null);
    }

    public MenuButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MenuButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.wedget_menu, null);
        addView(view);

        mImgCenter = findViewById(R.id.mImg_center0);
        mImgCenter1 = findViewById(R.id.mImg_center11);
        mImgCenter2 = findViewById(R.id.mImg_center21);
        mImgCenter3 = findViewById(R.id.mImg_center31);
        mImgCenter4 = findViewById(R.id.mImg_center41);
        mImgCenter5 = findViewById(R.id.mImg_center51);


        mImgCenter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShow) {
                    showOthers();
                } else {
                    backOther();
                }
            }
        });
    }


    private void backOther() {
        isShow = true;
        mImgCenter1.clearAnimation();
        int centerX = getWidth() / 2 - mImgCenter1.getWidth() / 2;
        int centerY = getHeight() - mImgCenter.getHeight() / 2;
        float sqrt = (float)(Math.sqrt(2)*(radious/2)) ;
        float v = (float)Math.sin(degree) * radious;
        sqrt=v;
        AnimateUtils.propertyAnimator(mImgCenter1, "x", centerX - ScreenUtil.dip2px(mContext, radious), centerX).start();
//        mImgCenter1.
        Animator x2 = AnimateUtils.propertyAnimator(mImgCenter2, "x", centerX - ScreenUtil.dip2px(mContext, sqrt), centerX);
        Animator y2 = AnimateUtils.propertyAnimator(mImgCenter2, "y", centerY - ScreenUtil.dip2px(mContext, sqrt), centerY);
        AnimateUtils.propertyAnimatorSet(true, x2, y2).start();

        AnimateUtils.propertyAnimator(mImgCenter3, "y", centerY - ScreenUtil.dip2px(mContext, radious), centerY).start();

        AnimateUtils.propertyAnimatorSet(true,
                AnimateUtils.propertyAnimator(mImgCenter4, "x", centerX + ScreenUtil.dip2px(mContext, sqrt), centerX),
                AnimateUtils.propertyAnimator(mImgCenter4, "y", centerY - ScreenUtil.dip2px(mContext, sqrt), centerY)
        ).start();

        AnimateUtils.propertyAnimator(mImgCenter5, "x", centerX + ScreenUtil.dip2px(mContext, radious), centerX).start();
    }

    private void showOthers() {
        float sqrt = (float)(Math.sqrt(2)*(radious/2)) ;

        float v = (float)Math.sin(degree) * radious;
        sqrt=v;
        isShow = false;
        int centerX = getWidth() / 2 - mImgCenter1.getWidth() / 2;
        int centerY = getHeight() - mImgCenter.getHeight() / 2;
        AnimateUtils.propertyAnimator(mImgCenter1, "x", centerX, centerX - ScreenUtil.dip2px(mContext, radious)).start();
//        mImgCenter1.
        Animator x2 = AnimateUtils.propertyAnimator(mImgCenter2, "x", centerX, centerX - ScreenUtil.dip2px(mContext, sqrt));
        Animator y2 = AnimateUtils.propertyAnimator(mImgCenter2, "y", centerY, centerY - ScreenUtil.dip2px(mContext, sqrt));
        AnimateUtils.propertyAnimatorSet(true, x2, y2).start();
        AnimateUtils.propertyAnimator(mImgCenter3, "y", centerY, centerY - ScreenUtil.dip2px(mContext, radious)).start();
        AnimateUtils.propertyAnimatorSet(true,
                AnimateUtils.propertyAnimator(mImgCenter4, "x", centerX, centerX + ScreenUtil.dip2px(mContext, sqrt)),
                AnimateUtils.propertyAnimator(mImgCenter4, "y", centerY, centerY - ScreenUtil.dip2px(mContext, sqrt))
        ).start();
        AnimateUtils.propertyAnimator(mImgCenter5, "x", centerX, centerX + ScreenUtil.dip2px(mContext, radious)).start();
    }

    /**
     * 获取导航栏高度
     *
     * @param context
     * @return
     */
    public static int getDaoHangHeight(Context context) {
        int result = 0;
        int resourceId = 0;
        int rid = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        if (rid != 0) {
            resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            return context.getResources().getDimensionPixelSize(resourceId);
        } else
            return 0;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int withMode = MeasureSpec.getMode(widthMeasureSpec);
        switch (withMode) {
            case MeasureSpec.EXACTLY:

                break;

            case MeasureSpec.AT_MOST://自适应
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(ScreenUtil.getScreenWidth(mContext),MeasureSpec.EXACTLY);
                break;

            case MeasureSpec.UNSPECIFIED:
                break;
        }

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        switch (heightMode) {
            case MeasureSpec.EXACTLY:
            case MeasureSpec.UNSPECIFIED:
                break;

            case MeasureSpec.AT_MOST:
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(ScreenUtil.dip2px(mContext,200),MeasureSpec.EXACTLY);
                break;
        }



        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
