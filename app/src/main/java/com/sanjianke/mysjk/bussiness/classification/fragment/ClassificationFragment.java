package com.sanjianke.mysjk.bussiness.classification.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dd.mylibrary.utils.ScreenUtil;
import com.dd.mylibrary.utils.ToastUtils;
import com.dd.mylibrary.utils.statusbar.StatusBarUtils;
import com.sanjianke.mysjk.R;
import com.sanjianke.mysjk.base.BaseFragment;
import com.sanjianke.mysjk.bean.ClassificationBean;
import com.sanjianke.mysjk.bussiness.classification.adapter.ClassificationAdapter;
import com.sanjianke.mysjk.bussiness.classification.constract.ClassificationContract;
import com.sanjianke.mysjk.bussiness.classification.presenter.ClassificationPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ClassificationFragment extends BaseFragment<ClassificationPresenter> implements ClassificationContract.View {

    public static ClassificationFragment classificationFragment;
    @BindView(R.id.mRv)
    RecyclerView mRv;
    Unbinder unbinder;
    @BindView(R.id.mImg_classification)
    LinearLayout mImgClassification;
    Unbinder unbinder1;
    @BindView(R.id.ll_status)
    LinearLayout llStatus;
    @BindView(R.id.mTxt_titie)
    TextView mTxtTitie;
    Unbinder unbinder2;

    private ClassificationAdapter adapter;
    private List<ClassificationBean> mDates = new ArrayList<>();

    public static ClassificationFragment getInstance() {
        if (classificationFragment == null)
            classificationFragment = new ClassificationFragment();
        return classificationFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_classification;
    }

    @Override
    public void initPresenter() {
        initTitle();
        mPresenter.init(this);
    }

    private void initTitle() {

        int statusBarHeight = StatusBarUtils.getStatusBarHeight(mContext) + ScreenUtil.dip2px(mContext, 44);
        mImgClassification.getLayoutParams().width = ScreenUtil.getScreenWidth(mContext);
        mImgClassification.getLayoutParams().height = statusBarHeight;
        llStatus.getLayoutParams().height = StatusBarUtils.getStatusBarHeight(mContext);
    }


    @Override
    public void loadData() {
        initRv();
        mPresenter.main();
    }

    private void initRv() {
        adapter = new ClassificationAdapter(mContext, mDates);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mRv.setLayoutManager(manager);
        mRv.setAdapter(adapter);

        adapter.setOnItemClickListener(new ClassificationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, View view) {
                ToastUtils.show(mContext, pos + "");
            }
        });
    }

    @Override
    public void main(List<ClassificationBean> mDate) {
        if (mDate == null && mDate.size() == 0) {
            return;
        }
        mDates.addAll(mDate);
        adapter.notifyDataSetChanged();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder2 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder2.unbind();
    }
}
