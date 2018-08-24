package com.dd.mylibrary.utils;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import java.util.List;


/**
 * fillAfter 是否停止在结尾
 * reverse 反复
 * restart 从头开始
 * interpolator 变速器
 */
public class AnimateUtils {

    //-----------------------补间动画---------------------------------------

    /**
     * 补间：X方向平移
     * fromXType
     * fromXValue
     * <p>
     * fromYType
     * fromYValue
     * <p>
     * toXType
     * toXValue
     * <p>
     * toYType
     * toYValue
     */
    public static Animation trans_Tween(int x, int y) {
        //相对于父控件
        Animation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, x,
                Animation.RELATIVE_TO_PARENT, 0.5f,
                Animation.RELATIVE_TO_PARENT, y,
                Animation.RELATIVE_TO_PARENT, 0.5f);

        animation.setDuration(1000);
        //直接传动画对象
        animation.setInterpolator(new BounceInterpolator());
        return animation;
    }

    /**
     * Constructor to use when building a RotateAnimation from code
     *
     * @param fromDegrees 初始角度
     * @param toDegrees   终点角度
     * @param pivotXType  x点坐标
     * @param pivotXValue x值
     * @param pivotYType
     * @param pivotYValue
     */
    public static Animation rotate_Tween(float fromDegrees, float toDegrees, int pivotXType, float pivotXValue, int pivotYType, float pivotYValue) {
        //相对于父控件
        Animation animation = new RotateAnimation(fromDegrees, toDegrees, pivotXType, pivotXValue, pivotYType, pivotYValue);
        animation.setInterpolator(new LinearInterpolator());//匀速
        return animation;
    }

    /**
     * 补间：缩放
     */
    public static Animation scale_Tween(float fromX, float toX, float fromY, float toY, int pivotXType, float pivotXValue, int pivotYType, float pivotYValue) {

        //相对于父控件
        Animation animation = new ScaleAnimation(fromX, toX, fromY, toY, pivotXType, pivotXValue, pivotYType, pivotYValue);
        return animation;
    }

    /**
     * 补间：透明度（0 完全透明，1不透明）
     * fromAlpha 初始透明度 0
     * toAlpha  终止透明度 1
     */
    public static Animation alph_Tween(float fromAlpha, float toAlpha) {
        //相对于父控件
        Animation animation = new AlphaAnimation(fromAlpha, toAlpha);
        return animation;
    }

    /**
     * 帧动画
     *
     * @param list
     * @param duation
     * @return
     */
    public static Drawable frameAni(List<Drawable> list, int duation) {
        AnimationDrawable ad = new AnimationDrawable();
        for (int i = 0; i < list.size(); i++) {
            ad.addFrame(list.get(i), duation);
        }
        //是否只显示一次
        ad.setOneShot(false);
        return ad;
    }

    /**
     * 混合补间动画
     */
    public static AnimationSet animatSetCollection(Animation... animations) {

        AnimationSet animationSet = new AnimationSet(false);
        for (int i = 0; i < animations.length; i++) {
            animationSet.addAnimation(animations[i]);
        }
        return animationSet;
    }


    //-----------------------属性动画---------------------------------------

    /**
     * propertyName 属性名称，指定执行动画的类型
     * translationX  水平方向平移
     * translationY  垂直方向平移
     * rotation      围绕中心点旋转
     * rotationX     围绕x轴旋转
     * rotationY     围绕y轴旋转
     * scaleX        宽度缩放
     * scaleY        高度缩放
     * alpha         透明改变
     * x             移动到执行位置的x坐标
     * y             移动到执行位置的y坐标
     * <p>
     * valueType  下方from和to的数值类型 int、float
     * valueFrom  动画起始值
     * valueTo 动画结束值
     */
    public static Animator propertyAnimator(View view, String propertyName, float... value) {
        //控制属性动画旋转的中心点，对view进行设置
//        view.setPivotX(view.getWidth());
        Animator animator = ObjectAnimator.ofFloat(view, propertyName, value);
        return animator;
    }


    public static AnimatorSet propertyAnimatorSet(boolean together, Animator... animators) {
        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.play(animators[0]);
        if (together) {
            animatorSet.playTogether(animators);//一起执行
        } else {
            animatorSet.playSequentially(animators);//执行多个动画,顺序执行
        }

        return animatorSet;
    }


    /**
     * with和play一起执行
     * play在after后执行
     * play在before前执行
     * <p>
     * after--paly\with--before
     *
     * @param animators
     */
    public static void propertyAnimatorSet(Animator... animators) {
        AnimatorSet animatorSet = new AnimatorSet();
        AnimatorSet.Builder after = animatorSet.play(animators[0]).with(animators[1]).after(animators[2]).before(animators[3]);

    }

}
