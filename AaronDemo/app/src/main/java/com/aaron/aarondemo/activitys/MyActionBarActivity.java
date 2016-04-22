package com.aaron.aarondemo.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.aaron.aarondemo.R;
import com.aaron.aarontools.views.MyActionBar;

/**
 * Created by toughegg on 16/4/22.
 */
public class MyActionBarActivity extends Activity implements MyActionBar.OnActionBarClickListener {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_myactionbar);
        MyActionBar bar = (MyActionBar) findViewById (R.id.bar);
        bar.setBarBackGround (android.R.color.holo_blue_light);
        bar.setBarTitleText (R.string.my_actionbar);
//        bar.setBarTitleText ("my_actionbar");
        bar.setBarLeftImage (R.drawable.back_btn);
        bar.setBarRightText ("右");
        bar.setBarTextBackground (R.drawable.blue_btn_color);
        bar.setOnActionBarClickListener (this);
    }

    @Override
    public void onActionBarLeftClicked () {
        finish ();
    }

    @Override
    public void onActionBarRightClicked () {
        Toast.makeText (this, "右边点击事件", Toast.LENGTH_SHORT).show ();
        Log.e ("aaron", "右边点击事件");
    }
}
