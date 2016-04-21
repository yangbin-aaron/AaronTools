package com.aaron.aarondemo;

import android.app.Application;

/**
 * Created by toughegg on 16/4/21.
 */
public class AaronApplication extends Application {

    @Override
    public void onCreate () {
        super.onCreate ();

    }

    // FloatWindonsActivity1     的   service使用
    public int floatWindonX = 100;
    public int floatWindonY = 100;

    public boolean floatWindonIsShowing = false;// 悬浮窗口是否已经显示
}
