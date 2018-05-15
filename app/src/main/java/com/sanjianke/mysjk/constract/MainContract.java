package com.sanjianke.mysjk.constract;


import com.sanjianke.mysjk.base.BaseView;

/**
 * Created by Administrator on 2018/3/9 0009.
 */

public class MainContract {

    public interface View extends BaseView {
        void main(String s);
    }

    public interface Presenter {
        void main(int state, int page, int pageSize, String msg);
    }
}
