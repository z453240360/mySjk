package com.sanjianke.mysjk.bussiness.homepage.fragment;


import com.dd.mylibrary.http.constant.Constant;
import com.dd.mylibrary.utils.GlideImageLoader;
import com.dd.mylibrary.utils.ToastUtils;
import com.sanjianke.mysjk.R;
import com.sanjianke.mysjk.base.BaseFragment;
import com.sanjianke.mysjk.bussiness.homepage.constract.HomeContract;
import com.sanjianke.mysjk.bussiness.homepage.presenter.HomePresenter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View {


    public static HomeFragment homeFragment;
    public static HomeFragment getInstance() {
        if (homeFragment == null)
            homeFragment = new HomeFragment();
        return homeFragment;
    }

    @BindView(R.id.mBanner)
    Banner mBanner;
    Unbinder unbinder;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void loadData() {
        initBanner();
    }

    private void initBanner() {
        List<String> images = new ArrayList<>();
        images.add(Constant.bannerImage1);
        images.add(Constant.bannerImage2);
        images.add(Constant.bannerImage3);
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(images);
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
//        mBanner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
//        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();

        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
//                Toasty.normal(mContext,""+position,5).show();
                ToastUtils.show(mContext,position+"");
            }
        });
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

}
