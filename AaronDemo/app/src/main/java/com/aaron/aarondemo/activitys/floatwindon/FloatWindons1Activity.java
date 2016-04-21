package com.aaron.aarondemo.activitys.floatwindon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aaron.aarondemo.R;
import com.aaron.aarondemo.services.FxService;

/**
 * Created by toughegg on 16/4/21.
 */
public class FloatWindons1Activity extends Activity implements View.OnClickListener {

    private Button show_float_windons_btn,hide_float_windons_btn;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_floatwindons1);
        TextView back = (TextView) findViewById (R.id.back);
        back.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                finish ();
            }
        });
        initView();
    }

    private void initView () {
        show_float_windons_btn = (Button) findViewById (R.id.show_float_windons_btn);
        show_float_windons_btn.setOnClickListener (this);
        hide_float_windons_btn = (Button) findViewById (R.id.hide_float_windons_btn);
        hide_float_windons_btn.setOnClickListener (this);
    }

    @Override
    public void onClick (View v) {
        switch (v.getId ()){
            case R.id.show_float_windons_btn:
                Intent intentShow = new Intent(FloatWindons1Activity.this, FxService.class);
                //启动FxService
                startService(intentShow);
                break;
            case R.id.hide_float_windons_btn:
                Intent intentHide = new Intent(FloatWindons1Activity.this, FxService.class);
                //终止FxService
                stopService(intentHide);
                break;
        }
    }
}
