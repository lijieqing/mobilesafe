package com.myapp.newlife.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by lijie on 2016/5/19.
 */
public class NoScrollViewPager extends ViewPager {
    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //决定事件是否中断
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;//不拦截事件, 让嵌套的子viewpager有机会响应触摸事件
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // 重写ViewPager滑动事件, 改为什么都不做
        return true;
    }
}
