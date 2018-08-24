package com.sanjianke.mysjk.bussiness.login;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;
import android.widget.TextView;

import com.sanjianke.mysjk.R;
import com.sanjianke.mysjk.base.BaseActivity;
import com.sanjianke.mysjk.bussiness.login.constract.LoginContract;
import com.sanjianke.mysjk.bussiness.login.presenter.LoginPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.mEd_userName)
    EditText mEdUserName;
    @BindView(R.id.username)
    TextInputLayout username;
    @BindView(R.id.mEd_password)
    EditText mEdPassword;
    @BindView(R.id.repeat_password)
    TextInputLayout repeatPassword;
    @BindView(R.id.mTxt_login)
    TextView mTxtLogin;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    public void loadData() {
        username.setError("");
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
