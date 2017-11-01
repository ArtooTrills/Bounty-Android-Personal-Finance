package com.example.nazmuddinmavliwala.ewallet.base;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by nazmuddinmavliwala on 14/01/16.
 */
public class SwipeControlViewPager extends ViewPager {

    private boolean swipe = false;

    public void disableSwipe() {
        requestDisallowInterceptTouchEvent(true);
        swipe = false;
    }

    public void enableSwipe() {
        requestDisallowInterceptTouchEvent(false);
        swipe = true;
    }

    public SwipeControlViewPager(Context context) {
        super(context);
    }

    public SwipeControlViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return swipe && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return swipe && super.onInterceptHoverEvent(event);

    }
}
