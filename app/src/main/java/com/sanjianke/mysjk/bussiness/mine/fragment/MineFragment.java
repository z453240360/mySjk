package com.sanjianke.mysjk.bussiness.mine.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sanjianke.mysjk.R;
import com.sanjianke.mysjk.activity.ZxingActivity;
import com.sanjianke.mysjk.base.BaseFragment;
import com.sanjianke.mysjk.bussiness.login.LoginActivity;
import com.sanjianke.mysjk.bussiness.mine.activity.GameActivity;
import com.sanjianke.mysjk.bussiness.mine.activity.MusicActivity;
import com.sanjianke.mysjk.bussiness.mine.activity.SdCardActivity;
import com.sanjianke.mysjk.bussiness.mine.constract.MineContract;
import com.sanjianke.mysjk.bussiness.mine.presenter.MinePresenter;
import com.sanjianke.mysjk.web.WebActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MineFragment extends BaseFragment<MinePresenter> implements MineContract.View {
    public static MineFragment mineFragment;
    Unbinder unbinder;

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


    @OnClick({R.id.mTxt_music1, R.id.mTxt_music2, R.id.mTxt_music3,R.id.mTxt_game,R.id.mTxt_file,R.id.mTxt_web})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mTxt_music1:
                startActivity(MusicActivity.class);
                break;
            case R.id.mTxt_music2:
                startActivity(LoginActivity.class);
                break;
            case R.id.mTxt_music3:
                startActivity(ZxingActivity.class);
                break;
            case R.id.mTxt_game:
                startActivity(GameActivity.class);
                break;

            case R.id.mTxt_file:
                startActivity(SdCardActivity.class);
                break;
            case R.id.mTxt_web:
                startActivity(WebActivity.class);
                break;
        }
    }
}
