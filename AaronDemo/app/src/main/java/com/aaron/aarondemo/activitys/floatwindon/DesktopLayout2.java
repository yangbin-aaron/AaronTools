package com.aaron.aarondemo.activitys.floatwindon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.aaron.aarondemo.R;

/**
 * Created by toughegg on 16/4/21.
 */
public class DesktopLayout2 extends LinearLayout {

    public DesktopLayout2 (Context context) {
        super (context);
        setOrientation (LinearLayout.VERTICAL);// 水平排列


        //设置宽高
        this.setLayoutParams (new LayoutParams (LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));

        View view = LayoutInflater.from (context).inflate (
                R.layout.desklayout, null);
        this.addView (view);
    }
}
