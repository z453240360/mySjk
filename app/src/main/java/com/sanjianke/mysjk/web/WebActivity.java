package com.sanjianke.mysjk.web;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.dd.mylibrary.wedget.dialog.HttpDialog;
import com.sanjianke.mysjk.R;
import com.sanjianke.mysjk.base.BaseActivity;
import com.sanjianke.mysjk.base.BaseView;
import com.sanjianke.mysjk.web.presenters.WebPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebActivity extends BaseActivity<WebPresenter> implements BaseView {


    @BindView(R.id.syswebviewac)
    SysWebView syswebviewac;

    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void loadData() {
        syswebviewac.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        initView();
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

    /**
     * 初始化视图控件
     */
    void initView() {
        syswebviewac.dialog.setOnKeyBackListener(new HttpDialog.onKeyBackListener() {
            @Override
            public void keyBack() {
                finish();
            }
        });
        loadUR();
    }

    @SuppressLint("JavascriptInterface")
    private void loadUR() {
        syswebviewac.getWebView().addJavascriptInterface(this, "sportsPage");
        syswebviewac.loadUrl("https://www.baidu.com/");
    }


}
