package com.sanjianke.mysjk.bussiness.mine.constract;


import com.sanjianke.mysjk.base.BaseView;
import com.sanjianke.mysjk.bean.MusicBean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/9 0009.
 */

public class MusicContract {

    public interface View extends BaseView {
        void main(List<MusicBean>list);
    }

    public interface Presenter {
        void main();
    }
}
