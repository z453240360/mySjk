package com.dd.mylibrary.wedget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class FingerView extends View {


    public FingerView(Context context) {
        super(context);
    }

    public FingerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FingerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        switch (event.getAction()){


        }


        return super.dispatchTouchEvent(event);
    }
}
