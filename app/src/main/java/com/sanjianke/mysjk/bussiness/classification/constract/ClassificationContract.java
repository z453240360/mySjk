package com.sanjianke.mysjk.bussiness.classification.constract;


import com.sanjianke.mysjk.base.BaseView;
import com.sanjianke.mysjk.bean.ClassificationBean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/9 0009.
 */

public class ClassificationContract {

    public interface View extends BaseView {
        void main(List<ClassificationBean> mDates);
    }

    public interface Presenter {
        void main();
    }
}
