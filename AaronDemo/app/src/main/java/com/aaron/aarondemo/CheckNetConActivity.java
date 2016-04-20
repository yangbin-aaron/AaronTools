package com.aaron.aarondemo;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.aaron.aarontools.tools.NetConnectionTools;

/**
 * Created by toughegg on 16/4/20.
 */
public class CheckNetConActivity extends Activity implements View.OnClickListener {

    private CheckNetConActivity activity;
    private Button net_is_con_btn, net_type_btn,net_wifi_mobile_btn;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_checknetcon);
        activity = this;
        initView ();
    }

    private void initView () {
        net_is_con_btn = (Button) findViewById (R.id.net_is_con_btn);
        net_is_con_btn.setOnClickListener (this);
        net_type_btn = (Button) findViewById (R.id.net_type_btn);
        net_type_btn.setOnClickListener (this);
        net_wifi_mobile_btn = (Button) findViewById (R.id.net_wifi_mobile_btn);
        net_wifi_mobile_btn.setOnClickListener (this);

    }

    @Override
    public void onClick (View v) {
        switch (v.getId ()) {
            case R.id.net_is_con_btn:
                boolean netIsCon = NetConnectionTools.checkNetConnected (activity);
                Toast.makeText (activity, "当前网络的连接状态为：" + (netIsCon ? "已连接" : "未连接"), Toast.LENGTH_SHORT).show ();
                break;
            case R.id.net_type_btn:
                int netType = NetConnectionTools.getNetType (activity);
                String str = "";
                switch (netType) {
                    case ConnectivityManager.TYPE_WIFI:
                        str = "WIFI";
                        break;
                    case ConnectivityManager.TYPE_MOBILE:
                        str = "MOBILE";
                        break;
                }
                Toast.makeText (activity, "当前连接的网络Type为：" + str, Toast.LENGTH_SHORT).show ();
                break;
            case R.id.net_wifi_mobile_btn:
                boolean netWifiMobile= NetConnectionTools.checkNetworkConnection (activity);
                Toast.makeText (activity, "WiFi和移动流量连接状态为：" + (netWifiMobile ? "已连接" : "未连接"), Toast.LENGTH_SHORT).show ();
                break;
        }
    }
}
