package com.sanjianke.mysjk.web.contracts;


import com.sanjianke.mysjk.base.BaseView;

/**
 * 创建日期：2017/12/12 on 15:25
 * 描述:
 * 作者:李振亚
 */

public class WebContract {
    public interface View extends BaseView {

    }

    public interface Presenter {
        void checkPayResult();
    }
}
