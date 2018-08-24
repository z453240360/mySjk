package com.sanjianke.mysjk.bussiness.mine.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dd.mylibrary.utils.SdCarcUtils;
import com.dd.mylibrary.utils.ToastUtils;
import com.dd.mylibrary.wedget.toasty.Toasty;
import com.sanjianke.mysjk.R;
import com.sanjianke.mysjk.base.BaseActivity;
import com.sanjianke.mysjk.bussiness.mine.adapter.SdCardAdapter;
import com.sanjianke.mysjk.bussiness.mine.constract.SDContract;
import com.sanjianke.mysjk.bussiness.mine.presenter.SDPresenter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SdCardActivity extends BaseActivity<SDPresenter> implements SDContract.View {


    @BindView(R.id.mTxt_sdSize)
    TextView mTxtSdSize;
    @BindView(R.id.mRv_sd)
    RecyclerView mRvSd;

    private List<File> mDatas = new ArrayList<>();
    private LinearLayoutManager manager;
    private SdCardAdapter adapter;
    private File currentFile;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sd_card;
    }

    @Override
    public void initPresenter() {
        isNeedPermison = true;
        mPresenter.init(this);
    }

    @Override
    public void loadData() {

        initRv();


        if (SdCarcUtils.isExist()) {
            mTxtSdSize.setText(""+SdCarcUtils.getAvaliSize(mContext) + " 可用  /    总容量：" + SdCarcUtils.getTotalSize(mContext));
            currentFile = SdCarcUtils.getSdFile();
            initFile(currentFile);
        }
    }

    private void initRv() {
        manager = new LinearLayoutManager(mContext);
        adapter = new SdCardAdapter(mContext,mDatas);
        mRvSd.setAdapter(adapter);
        mRvSd.setLayoutManager(manager);
        adapter.setOnItemClickListener(new SdCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, View view) {
                currentFile = mDatas.get(pos);
                mDatas.clear();
                if (currentFile.isDirectory()) {
                    initFile(currentFile);
                }
            }
        });
    }

    private void initFile(File file) {
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()){
                mDatas.add(0,files[i]);
            }else {
                mDatas.add(files[i]);
            }
        }

        if (mDatas.size()==0){
            ToastUtils.show(mContext,"当前内容为空");
        }
        adapter.notifyDataSetChanged();
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

    @Override
    public void onBackPressed() {
        if (currentFile.getAbsolutePath().equals(SdCarcUtils.getSdPath())){
            finish();
        }else {
            currentFile = currentFile.getParentFile();
            initFile(currentFile);
        }
    }
}
