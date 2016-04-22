package com.aaron.aarondemo.activitys.systems;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aaron.aarondemo.R;
import com.aaron.aarondemo.services.LongRunningService;
import com.aaron.aarontools.AaronConstants;
import com.aaron.aarontools.tools.SystemTools;
import com.aaron.aarontools.tools.UtilTools;

import java.util.HashMap;

/**
 * Created by toughegg on 16/4/20.
 */
public class CheckSystemToolsActivity extends Activity implements View.OnClickListener {


    private final static int IS_BACKGROUND_APP = 0x01;
    private final static int IS_SLEEPING = 0x02;
    private final static int THREAD_HANDLER = 0x03;
    private Handler mHandler = new Handler () {
        @Override
        public void handleMessage (Message msg) {
            super.handleMessage (msg);
            switch (msg.what) {
                case AaronConstants.HANDLER_WHAT_MEMORY_CAPACITY:
                    findViewById (R.id.layout_load).setVisibility (View.GONE);
                    String str = "释放内存 " + msg.arg1 + "M";
                    Toast.makeText (CheckSystemToolsActivity.this, str, Toast.LENGTH_SHORT).show ();
                    break;
                case IS_BACKGROUND_APP:
                    Toast.makeText (CheckSystemToolsActivity.this,
                            SystemTools.isBackground (CheckSystemToolsActivity.this) ? "该应用在后台运行" : "该应用在前台运行"
                            , Toast.LENGTH_SHORT).show ();
                    Log.e ("aaron", SystemTools.isBackground (CheckSystemToolsActivity.this) ? "该应用在后台运行" : "该应用在前台运行");
                    break;
                case IS_SLEEPING:
                    Log.e ("aaron", SystemTools.isSleeping (CheckSystemToolsActivity.this) ? "当前处于睡眠状态" : "当前属于活跃状态");
                    break;
                case THREAD_HANDLER:
                    // 系统时间（1）
                    TextView system_time1_tv = (TextView) findViewById (R.id.system_time1_tv);
                    String system_time = SystemTools.getDataOfFormat ("yyyy-MM-dd HH:mm:ss");
                    system_time1_tv.setText (system_time);
                    // 系统时间（2）
                    HashMap<String, Integer> time_hash = SystemTools.getTimeOfDefault ();
                    TextView system_time2_tv = (TextView) findViewById (R.id.system_time2_tv);
                    system_time2_tv.setText (time_hash.get (AaronConstants.YEAR)
                            + "/" + time_hash.get (AaronConstants.MONTH)
                            + "/" + time_hash.get (AaronConstants.DAY)
                            + " " + time_hash.get (AaronConstants.HOUR)
                            + ":" + time_hash.get (AaronConstants.MINUTE)
                            + ":" + time_hash.get (AaronConstants.SECOND)
                            + " 星期" + (time_hash.get (AaronConstants.WEEK) == 0 ? 7 : time_hash.get (AaronConstants.WEEK)));
                    break;
            }
        }
    };

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
        Button clear_thread_service_btn = (Button) findViewById (R.id.clear_thread_service_btn);
        clear_thread_service_btn.setOnClickListener (this);
        Button is_background_app_btn = (Button) findViewById (R.id.is_background_app_btn);
        is_background_app_btn.setOnClickListener (this);
        Button is_sleeping_btn = (Button) findViewById (R.id.is_sleeping_btn);
        is_sleeping_btn.setOnClickListener (this);
        Button go_home_btn = (Button) findViewById (R.id.go_home_btn);
        go_home_btn.setOnClickListener (this);
        Button switch_language_btn = (Button) findViewById (R.id.switch_language_btn);
        switch_language_btn.setOnClickListener (this);
        Button create_notification_btn = (Button) findViewById (R.id.create_notification_btn);
        create_notification_btn.setOnClickListener (this);
        Button alarm_btn = (Button) findViewById (R.id.alarm_btn);
        alarm_btn.setOnClickListener (this);
        // 手机IP
        TextView ip_tv = (TextView) findViewById (R.id.ip_tv);
        String ipStr = SystemTools.getPhoneIp ();
        ip_tv.setText (ipStr == null ? "null" : ipStr);
        // 内存
        TextView dum_tv = (TextView) findViewById (R.id.dum_tv);
        int dum = SystemTools.getDeviceUsableMemory (this);
        dum_tv.setText (dum + "");
    }

    @Override
    protected void onResume () {
        super.onResume ();
        createThread ();
    }

    @Override
    protected void onPause () {
        super.onPause ();
        thread_swith = false;
    }

    @Override
    public void onClick (View v) {
        switch (v.getId ()) {
            case R.id.all_app_list_btn:// 所有应用列表
                Intent intentAll = new Intent (CheckSystemToolsActivity.this, AppListActivity.class);
                intentAll.putExtra ("type", 0);
                startActivity (intentAll);
                break;
            case R.id.notsystem_app_list_btn:// 非系统应用列表
                Intent intentNotsystem = new Intent (CheckSystemToolsActivity.this, AppListActivity.class);
                intentNotsystem.putExtra ("type", 1);
                startActivity (intentNotsystem);
                break;
            case R.id.clear_thread_service_btn:// 清理线程和服务
                findViewById (R.id.layout_load).setVisibility (View.VISIBLE);
                SystemTools.clearThreadsAndServices (CheckSystemToolsActivity.this, mHandler);
                break;
            case R.id.is_background_app_btn:// 判断当前应用程序是否后台运行
                isBackGroundApp ();
                break;
            case R.id.is_sleeping_btn:// 手机是否处于睡眠状态
                isSleeping ();
                break;
            case R.id.go_home_btn:
                SystemTools.goHome (CheckSystemToolsActivity.this);
                break;
            case R.id.switch_language_btn:
                String language = SystemTools.getLanguage (CheckSystemToolsActivity.this);
                Log.e ("aaron", language);
                if (language.equals (AaronConstants.LANGUAGE_ENGLISH)) {
                    language = AaronConstants.LANGUAGE_SIMPLIFIED_CHINESE;
                } else if (language.equals (AaronConstants.LANGUAGE_SIMPLIFIED_CHINESE)) {
                    language = AaronConstants.LANGUAGE_ENGLISH;
                }
                SystemTools.switchLanguage (CheckSystemToolsActivity.this, language);
                // 重新获取资源
                Button switch_language_btn = (Button) findViewById (R.id.switch_language_btn);
                switch_language_btn.setText (R.string.switch_language);
                break;
            case R.id.create_notification_btn:
                /*
                FLAG_ONE_SHOT   表示返回的PendingIntent仅能执行一次，执行完后自动取消
                FLAG_NO_CREATE     表示如果描述的PendingIntent不存在，并不创建相应的PendingIntent，而是返回NULL
                FLAG_CANCEL_CURRENT      表示相应的PendingIntent已经存在，则取消前者，然后创建新的PendingIntent，这个有利于数据保持为最新的，可以用于即时通信的通信场景
                FLAG_UPDATE_CURRENT     表示更新的PendingIntent
                 */
                Intent intent = new Intent (CheckSystemToolsActivity.this, AppListActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity (this, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                UtilTools.createNotification (CheckSystemToolsActivity.this, "测试标题", "测试提示", 0, R.drawable.icon_pan102, 1, pendingIntent);
                break;
            case R.id.alarm_btn:// 闹铃   http://www.cnblogs.com/linjiqin/archive/2011/02/26/1966065.html
                Button alarm_btn = (Button) findViewById (R.id.alarm_btn);
                Intent intentLRS = new Intent (this, LongRunningService.class);
                if (alarmIsStart) {
                    alarmIsStart = false;
                    stopService (intentLRS);
                    alarm_btn.setText ("定时通知(关)");
                } else {
                    alarmIsStart = true;
                    startService (intentLRS);
                    alarm_btn.setText ("定时通知(开)");
                }
                break;
        }
    }

    private boolean alarmIsStart = false;

    private int index = 0;

    private void isBackGroundApp () {
        new Thread () {
            @Override
            public void run () {
                super.run ();
                while (index < 10) {
                    mHandler.obtainMessage (IS_BACKGROUND_APP).sendToTarget ();
                    index++;
                    try {
                        Thread.sleep (2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace ();
                    }
                }
                index = 0;
            }
        }.start ();
    }

    private void isSleeping () {
        new Thread () {
            @Override
            public void run () {
                super.run ();
                while (index < 20) {
                    mHandler.obtainMessage (IS_SLEEPING).sendToTarget ();
                    index++;
                    try {
                        Thread.sleep (1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace ();
                    }
                }
                index = 0;
            }
        }.start ();
    }


    private boolean thread_swith = false;

    /**
     * 定时器
     */
    private void createThread () {
        thread_swith = true;
        new Thread () {
            @Override
            public void run () {
                super.run ();
                while (thread_swith) {
                    mHandler.obtainMessage (THREAD_HANDLER).sendToTarget ();
                    try {
                        Thread.sleep (1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace ();
                    }
                }
            }
        }.start ();
    }
}
