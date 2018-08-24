package com.sanjianke.mysjk.bussiness.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.dd.mylibrary.utils.statusbar.StatusBarUtils;
import com.sanjianke.mysjk.R;
import com.sanjianke.mysjk.base.BaseActivity;
import com.sanjianke.mysjk.bussiness.classification.fragment.ClassificationFragment;
import com.sanjianke.mysjk.bussiness.homepage.fragment.HomeFragment;
import com.sanjianke.mysjk.bussiness.main.constract.MainContract;
import com.sanjianke.mysjk.bussiness.main.presenter.MainPresenter;
import com.sanjianke.mysjk.bussiness.mine.fragment.MineFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class HomeActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.group)
    RadioGroup mGroup;

    private FragmentManager manager;
    private Fragment lastFragment;
    private List<Fragment> fragmentsList = new ArrayList<>();
    private HomeFragment homeFragment;
    private ClassificationFragment classificationFragment;
    private MineFragment mineFragment;

    @Override
    public int getLayoutId() {
        isNeedPermison = true;
        return R.layout.activity_home;
    }

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    public void loadData() {
        initStatusBar();
        initFragment();
    }

    @Override
    public void initStatusBar() {
        StatusBarUtils.setTranslucentForImageViewInFragment(this, 0, null);
    }

    private void initFragment() {

        manager = getSupportFragmentManager();

        homeFragment = HomeFragment.getInstance();
        mineFragment = MineFragment.getInstance();
        classificationFragment = ClassificationFragment.getInstance();

        fragmentsList.add(homeFragment);
        fragmentsList.add(classificationFragment);
        fragmentsList.add(mineFragment);

        manager.beginTransaction().add(R.id.container, fragmentsList.get(0)).commit();
        lastFragment = fragmentsList.get(0);
        mGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton select = (RadioButton) findViewById(i);
                int index = Integer.parseInt(select.getTag().toString());
                if (fragmentsList.get(index).isAdded()) {
                    manager.beginTransaction().show(fragmentsList.get(index)).commit();
                } else {
                    manager.beginTransaction().add(R.id.container, fragmentsList.get(index)).commit();
                }
                manager.beginTransaction().hide(lastFragment).commit();
                lastFragment = fragmentsList.get(index);
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
