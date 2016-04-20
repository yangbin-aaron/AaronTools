package com.aaron.aarondemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private MainActivity activity;
    private Button checkNetConBtn;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        activity = this;
        checkNetConBtn = (Button) findViewById (R.id.check_nettool_btn);
        checkNetConBtn.setOnClickListener (this);
    }

    @Override
    public void onClick (View v) {
        switch (v.getId ()) {
            case R.id.check_nettool_btn:
                startActivity (new Intent (activity, CheckNetConActivity.class));
                break;
        }
    }
}
