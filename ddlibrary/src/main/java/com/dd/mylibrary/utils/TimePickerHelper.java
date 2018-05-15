package com.dd.mylibrary.utils;

import android.content.Context;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.dd.mylibrary.R;

import java.util.Calendar;

/**
 * Created by Administrator on 2018/3/30 0030.
 */

public class TimePickerHelper {





    public static TimePickerBuilder initTimePicker(Context context, OnTimeSelectListener listener, boolean isShowDay) {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        //startDate.set(2013,1,1);
        Calendar endDate = Calendar.getInstance();
        //endDate.set(2020,1,1);

        //正确设置方式 原因：注意事项有说明
        startDate.set(2018, 0, 1);
        endDate.set(2020, 11, 31);

        TimePickerBuilder pickerBuilder = new TimePickerBuilder(context,listener);
                pickerBuilder.setType(new boolean[]{true, true, isShowDay, false, false, false})
                .setTitleColor(context.getResources().getColor(R.color.basicColor))//标题文字颜色
                .setSubmitColor(context.getResources().getColor(R.color.basicColor))//确定按钮文字颜色
                .setCancelColor(context.getResources().getColor(R.color.basicColor))//取消按钮文字颜色
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false)//是否循环滚动
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
//                .setContentSize(18)//滚轮文字大小
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setTitleText("请选择日期")//标题文字
                .build();

                return pickerBuilder;
    }



}
