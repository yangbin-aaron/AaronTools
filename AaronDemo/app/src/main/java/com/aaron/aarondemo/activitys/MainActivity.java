package com.aaron.aarondemo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.aaron.aarondemo.R;
import com.aaron.aarondemo.activitys.floatwindon.FloatWindons1Activity;
import com.aaron.aarondemo.activitys.floatwindon.FloatWindons2Activity;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private MainActivity activity;
    private Button escape_string_btn,check_nettool_btn,check_systemtool_btn,float_windons1_btn,float_windons2_btn;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        activity = this;
        check_nettool_btn = (Button) findViewById (R.id.check_nettool_btn);
        check_nettool_btn.setOnClickListener (this);
        check_systemtool_btn = (Button) findViewById (R.id.check_systemtool_btn);
        check_systemtool_btn.setOnClickListener (this);
        float_windons1_btn = (Button) findViewById (R.id.float_windons1_btn);
        float_windons1_btn.setOnClickListener (this);
        float_windons2_btn = (Button) findViewById (R.id.float_windons2_btn);
        float_windons2_btn.setOnClickListener (this);
        escape_string_btn = (Button) findViewById (R.id.escape_string_btn);
        escape_string_btn.setOnClickListener (this);
    }

    @Override
    public void onClick (View v) {
        switch (v.getId ()) {
            case R.id.escape_string_btn:// 转义符号
                startActivity (new Intent (activity, EscapeActivity.class));
                break;
            case R.id.check_nettool_btn:// 检测网络Tools
                startActivity (new Intent (activity, CheckNetConActivity.class));
                break;
            case R.id.check_systemtool_btn:// 检测系统Tools
                startActivity (new Intent (activity, CheckSystemToolsActivity.class));
                break;
            case R.id.float_windons1_btn:// 悬浮窗口,服务
                startActivity (new Intent (activity, FloatWindons1Activity.class));
                break;
            case R.id.float_windons2_btn:// 悬浮窗口,更加详细的（后来加的）
                startActivity (new Intent (activity, FloatWindons2Activity.class));
                break;
        }
    }
}
