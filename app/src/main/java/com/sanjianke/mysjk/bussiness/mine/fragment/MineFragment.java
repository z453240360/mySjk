package com.sanjianke.mysjk.bussiness.mine.fragment;

import com.sanjianke.mysjk.R;
import com.sanjianke.mysjk.base.BaseFragment;
import com.sanjianke.mysjk.bussiness.homepage.fragment.HomeFragment;
import com.sanjianke.mysjk.bussiness.mine.constract.MineContract;
import com.sanjianke.mysjk.bussiness.mine.presenter.MinePresenter;

public class MineFragment extends BaseFragment<MinePresenter> implements MineContract.View{
    public static MineFragment mineFragment;
    public static MineFragment getInstance() {
        if (mineFragment == null)
            mineFragment = new MineFragment();
        return mineFragment;
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
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
}
