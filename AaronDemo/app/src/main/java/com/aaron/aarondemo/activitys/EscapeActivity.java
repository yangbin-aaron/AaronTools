package com.aaron.aarondemo.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.aaron.aarondemo.R;

/**
 * Created by toughegg on 16/4/21.
 */
public class EscapeActivity  extends Activity{

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_escapestring);
        TextView back = (TextView) findViewById (R.id.back);
        back.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                finish ();
            }
        });
    }
}
