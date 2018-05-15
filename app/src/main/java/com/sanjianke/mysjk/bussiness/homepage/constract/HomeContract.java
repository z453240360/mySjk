package com.sanjianke.mysjk.bussiness.homepage.constract;


import com.sanjianke.mysjk.base.BaseView;

/**
 * Created by Administrator on 2018/3/9 0009.
 */

public class HomeContract {

    public interface View extends BaseView {
        void main();
    }

    public interface Presenter {
        void main();
    }
}
