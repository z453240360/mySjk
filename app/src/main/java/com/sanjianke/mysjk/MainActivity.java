package com.sanjianke.mysjk;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.dd.mylibrary.wedget.XTabLayout.XTabLayout;
import com.sanjianke.mysjk.base.BaseActivity;
import com.sanjianke.mysjk.bussiness.classification.fragment.ClassificationFragment;
import com.sanjianke.mysjk.bussiness.homepage.fragment.HomeFragment;
import com.sanjianke.mysjk.bussiness.mine.fragment.MineFragment;
import com.sanjianke.mysjk.constract.MainContract;
import com.sanjianke.mysjk.fragment.FragmentAdapter;
import com.sanjianke.mysjk.presenter.MainPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.xTab)
    XTabLayout xTab;
    List<Fragment> fragments = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void loadData() {
        initTitle();
        initViewPager();
    }

    private void initTitle() {
        mTitle.setTitle(false,"首页");
    }

    private void initViewPager() {
        List<String> titles = new ArrayList<>();
        titles.add("主页");
        titles.add("分类");
        titles.add("我的");

        fragments.add(new HomeFragment());
        fragments.add(new ClassificationFragment());
        fragments.add(new MineFragment());

        FragmentAdapter adatper = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(adatper);
        viewPager.setOffscreenPageLimit(4);
        //将TabLayout和ViewPager关联起来。
        xTab.setupWithViewPager(viewPager);
        //给TabLayout设置适配器
        xTab.setupWithViewPager(viewPager);
    }

    @Override
    public void main(String s) {

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
