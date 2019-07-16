package com.kumaduma.epicseveninfo.PopHead;

import android.content.Context;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatImageView;
import android.view.MotionEvent;
import android.widget.Toast;

public class PopHead extends AppCompatImageView {

    public PopHead(Context context) {
        super(context);
    }

    public PopHead(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return false;
    }

    // Because we call this from onTouchEvent, this code will be executed for both
    // normal touch events and for when the system calls this using Accessibility
    @Override
    public boolean performClick() {
        super.performClick();
        //doSomething();
        return true;
    }

    private void doSomething() {
    }
}