package com.sanjianke.mysjk.bussiness.mine.activity;

import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.dd.mylibrary.utils.AnimateUtils;
import com.dd.mylibrary.utils.ScreenUtil;
import com.dd.mylibrary.wedget.CircleImageView;
import com.sanjianke.mysjk.R;
import com.sanjianke.mysjk.base.BaseActivity;
import com.sanjianke.mysjk.bussiness.mine.constract.GameContract;
import com.sanjianke.mysjk.bussiness.mine.presenter.GamePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameActivity extends BaseActivity<GamePresenter> implements GameContract.View {

    @BindView(R.id.mImg_center)
    CircleImageView mImgCenter;
    @BindView(R.id.mImg_center1)
    CircleImageView mImgCenter1;
    @BindView(R.id.mImg_center2)
    CircleImageView mImgCenter2;
    @BindView(R.id.mImg_center3)
    CircleImageView mImgCenter3;
    @BindView(R.id.mImg_center4)
    CircleImageView mImgCenter4;
    @BindView(R.id.mImg_center5)
    CircleImageView mImgCenter5;
    private boolean isShow = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_game;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void loadData() {

    }

    @Override
    public void main() {

    }

    @Override
    public void showLoading(String content) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorMsg(String msg, String type) {

    }


    @OnClick({R.id.mImg_center, R.id.mImg_center1, R.id.mImg_center2, R.id.mImg_center3, R.id.mImg_center4, R.id.mImg_center5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mImg_center:
                if (isShow) {
                    showOthers();
                } else {
                    backOther();
                }
                break;
            case R.id.mImg_center1:

                break;
            case R.id.mImg_center2:
                break;
            case R.id.mImg_center3:
                break;
            case R.id.mImg_center4:
                break;
            case R.id.mImg_center5:
                break;
        }
    }

    private void backOther() {
        isShow = true;
        mImgCenter1.clearAnimation();
        int centerX = ScreenUtil.getScreenWidth(this) / 2 - mImgCenter1.getWidth() / 2;
        int centerY = ScreenUtil.getScreenHeight(this) - mImgCenter.getHeight() / 2 - getDaoHangHeight(this);


        AnimateUtils.propertyAnimator(mImgCenter1, "x", centerX - ScreenUtil.dip2px(this, 80), centerX).start();
//        mImgCenter1.
        Animator x2 = AnimateUtils.propertyAnimator(mImgCenter2, "x", centerX - ScreenUtil.dip2px(this, 56), centerX);
        Animator y2 = AnimateUtils.propertyAnimator(mImgCenter2, "y", centerY - ScreenUtil.dip2px(this, 56), centerY);
        AnimateUtils.propertyAnimatorSet(true, x2, y2).start();

        AnimateUtils.propertyAnimator(mImgCenter3, "y", centerY - ScreenUtil.dip2px(this, 80), centerY).start();

        AnimateUtils.propertyAnimatorSet(true,
                AnimateUtils.propertyAnimator(mImgCenter4, "x", centerX + ScreenUtil.dip2px(this, 56), centerX),
                AnimateUtils.propertyAnimator(mImgCenter4, "y", centerY - ScreenUtil.dip2px(this, 56), centerY)
        ).start();

        AnimateUtils.propertyAnimator(mImgCenter5, "x", centerX + ScreenUtil.dip2px(this, 80), centerX).start();
    }

    private void showOthers() {
        isShow = false;
        int centerX = ScreenUtil.getScreenWidth(this) / 2 - mImgCenter1.getWidth() / 2;
        int centerY = ScreenUtil.getScreenHeight(this) - mImgCenter.getHeight() / 2 - getDaoHangHeight(this);
        AnimateUtils.propertyAnimator(mImgCenter1, "x", centerX, centerX - ScreenUtil.dip2px(this, 80)).start();
//        mImgCenter1.
        Animator x2 = AnimateUtils.propertyAnimator(mImgCenter2, "x", centerX, centerX - ScreenUtil.dip2px(this, 56));
        Animator y2 = AnimateUtils.propertyAnimator(mImgCenter2, "y", centerY, centerY - ScreenUtil.dip2px(this, 56));
        AnimateUtils.propertyAnimatorSet(true, x2, y2).start();
        AnimateUtils.propertyAnimator(mImgCenter3, "y", centerY, centerY - ScreenUtil.dip2px(this, 80)).start();
        AnimateUtils.propertyAnimatorSet(true,
                AnimateUtils.propertyAnimator(mImgCenter4, "x", centerX, centerX + ScreenUtil.dip2px(this, 56)),
                AnimateUtils.propertyAnimator(mImgCenter4, "y", centerY, centerY - ScreenUtil.dip2px(this, 56))
        ).start();
        AnimateUtils.propertyAnimator(mImgCenter5, "x", centerX, centerX + ScreenUtil.dip2px(this, 80)).start();
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

}
