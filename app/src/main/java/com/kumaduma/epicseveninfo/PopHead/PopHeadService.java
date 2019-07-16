package com.kumaduma.epicseveninfo.PopHead;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kumaduma.epicseveninfo.MainPager;
import com.kumaduma.epicseveninfo.R;

import java.util.Arrays;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class PopHeadService extends Service {

    private WindowManager mWindowManager;
    private View mChatHeadView;
    private int screenWidth, screenHeight;
    private PopLayout popLayout;
    private RelativeLayout chatHeadRoot;
    private WindowManager.LayoutParams params;
    private BottomNavigationView navigation;

    public PopHeadService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Inflate the chat head layout we created
        LayoutInflater mainLayoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        getApplicationContext().setTheme(R.style.AppTheme);
        mChatHeadView = mainLayoutInflater.inflate(R.layout.pop_head, null);
        chatHeadRoot = mChatHeadView.findViewById(R.id.chat_head_root);

        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }

        //Add the view to the window.
        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                LAYOUT_FLAG,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        popLayout = mChatHeadView.findViewById(R.id.expanded_container);

        //Specify the chat head position
        params.gravity = Gravity.TOP | Gravity.START;        //Initially view will be added to top-left corner
        params.x = 0;
        params.y = 100;

        //Add the view to the window
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = mWindowManager.getDefaultDisplay();
        Point size = new Point();
        display. getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        mWindowManager.addView(mChatHeadView, params);

        //Set the close button.
        ImageView closeButton = mChatHeadView.findViewById(R.id.close_btn);
        closeButton.setOnClickListener((v)->{
                //close the service and remove the chat head from the window
                stopSelf();
        });

        //Drag and move chat head using user's touch action.
        final PopHead chatHeadImage = mChatHeadView.findViewById(R.id.chat_head_profile_iv);
        chatHeadImage.setOnTouchListener(new View.OnTouchListener() {
            private int lastAction;
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.performClick();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        //remember the initial position.
                        initialX = params.x;
                        initialY = params.y;

                        //get the touch location
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();

                        lastAction = event.getAction();
                        return true;
                    case MotionEvent.ACTION_UP:
                        //As we implemented on touch listener with ACTION_MOVE,
                        //we have to check if the previous action was ACTION_DOWN
                        //to identify if the user clicked the view or not.
                        if (lastAction == MotionEvent.ACTION_DOWN) {
                            //Open the chat conversation click.
                            //Intent intent = new Intent(PopHeadService.this, MainActivity.class);
                            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            //startActivity(intent);
                            if (popLayout.getVisibility() == View.VISIBLE){
                                setPopScreenView(1);
                            } else {
                                setPopScreenView(0);
                            }
                        } else {
                            if (params.x < screenWidth/2)
                                params.x = 0;
                            else
                                params.x = screenWidth-(mChatHeadView.getWidth());
                            mWindowManager.updateViewLayout(mChatHeadView, params);
                        }

                        lastAction = event.getAction();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        //Calculate the X and Y coordinates of the view.
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);

                        //Update the layout with new X & Y coordinate
                        mWindowManager.updateViewLayout(mChatHeadView, params);
                        lastAction = event.getAction();
                        return true;
                }
                return false;
            }
        });

        popLayout.setOnTouchListener((v, event)-> {
                v.performClick();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                }

                return false;
        });

        setPopScreenView(1);


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mChatHeadView != null) mWindowManager.removeView(mChatHeadView);
    }

    public void setPopScreenView(int setting){
        if (setting == 0) {
            popLayout.setVisibility(View.VISIBLE);
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.MATCH_PARENT;
            mWindowManager.updateViewLayout(mChatHeadView, params);
            RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) chatHeadRoot.getLayoutParams();
            relativeParams.setMargins(20, 5, 0, 0);  // left, top, right, bottom
            chatHeadRoot.setLayoutParams(relativeParams);
        } else {
            popLayout.setVisibility(View.GONE);
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            mWindowManager.updateViewLayout(mChatHeadView, params);
            RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) chatHeadRoot.getLayoutParams();
            relativeParams.setMargins(0, 0, 0, 0);  // left, top, right, bottom
            chatHeadRoot.setLayoutParams(relativeParams);
        }
    }
}