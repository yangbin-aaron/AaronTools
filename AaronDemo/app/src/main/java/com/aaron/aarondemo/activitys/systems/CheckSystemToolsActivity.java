package com.aaron.aarondemo.activitys.systems;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aaron.aarondemo.R;
import com.aaron.aarontools.tools.SystemTools;

/**
 * Created by toughegg on 16/4/20.
 */
public class CheckSystemToolsActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_checksystemtools);
        TextView back = (TextView) findViewById (R.id.back);
        back.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                finish ();
            }
        });

        initView ();
    }

    private void initView () {
        Button all_app_list_btn = (Button) findViewById (R.id.all_app_list_btn);
        all_app_list_btn.setOnClickListener (this);
        Button notsystem_app_list_btn = (Button) findViewById (R.id.notsystem_app_list_btn);
        notsystem_app_list_btn.setOnClickListener (this);
        // 手机IP
        TextView ip_tv = (TextView) findViewById (R.id.ip_tv);
        String ipStr = SystemTools.getPhoneIp ();
        ip_tv.setText (ipStr == null ? "null" : ipStr);

        TextView dum_tv = (TextView) findViewById (R.id.dum_tv);
        int dum = SystemTools.getDeviceUsableMemory (this);
        dum_tv.setText (dum + "");
    }

    @Override
    public void onClick (View v) {
        switch (v.getId ()) {
            case R.id.all_app_list_btn:
                Intent intentAll = new Intent (CheckSystemToolsActivity.this, AppListActivity.class);
                intentAll.putExtra ("type", 0);
                startActivity (intentAll);
                break;
            case R.id.notsystem_app_list_btn:
                Intent intentNotsystem = new Intent (CheckSystemToolsActivity.this, AppListActivity.class);
                intentNotsystem.putExtra ("type", 1);
                startActivity (intentNotsystem);
                break;
        }
    }
}
