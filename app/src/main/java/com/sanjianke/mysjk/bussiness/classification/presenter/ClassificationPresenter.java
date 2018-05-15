package com.sanjianke.mysjk.bussiness.classification.presenter;


import com.sanjianke.mysjk.base.BasePresenter;
import com.sanjianke.mysjk.bean.ClassificationBean;
import com.sanjianke.mysjk.bussiness.classification.constract.ClassificationContract;
import com.sanjianke.mysjk.bussiness.classification.fragment.ClassificationFragment;

import java.util.ArrayList;
import java.util.List;

public class ClassificationPresenter extends BasePresenter<ClassificationFragment> implements ClassificationContract.Presenter{

    @Override
    public void main() {

        List<ClassificationBean> mDates = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            ClassificationBean bean = new ClassificationBean();
            bean.setTitle("测试标题"+i);
            bean.setStatues(i%3+"");
            bean.setImgUrl("");
            bean.setMessage("副标题");
            mDates.add(bean);
        }

        mView.main(mDates);
    }
}
