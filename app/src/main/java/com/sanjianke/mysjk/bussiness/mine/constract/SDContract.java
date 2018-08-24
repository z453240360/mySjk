package com.sanjianke.mysjk.bussiness.mine.constract;


import com.sanjianke.mysjk.base.BaseView;

/**
 * Created by Administrator on 2018/3/9 0009.
 */

public class SDContract {

    public interface View extends BaseView {
        void main();
    }

    public interface Presenter {
        void main();
    }
}
