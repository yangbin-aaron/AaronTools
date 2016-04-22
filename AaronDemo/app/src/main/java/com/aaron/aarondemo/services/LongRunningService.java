package com.aaron.aarondemo.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.aaron.aarondemo.R;
import com.aaron.aarontools.tools.UtilTools;

/**
 * Created by toughegg on 16/4/22.
 */
public class LongRunningService extends Service {


    @Override
    public IBinder onBind (Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startId) {
        Log.e ("aaron","LongRunningService is started");

        AlarmManager manager = (AlarmManager) getSystemService (ALARM_SERVICE);
        //读者可以修改此处的Minutes从而改变提醒间隔时间
        //此处是设置每隔90分钟启动一次
        //这是90分钟的毫秒数
//        int Minutes = 90 * 60 * 1000;
        int Minutes = 10 * 1000;
        //SystemClock.elapsedRealtime()表示1970年1月1日0点至今所经历的时间

        long triggerAtTime = SystemClock.elapsedRealtime () + Minutes;
        //此处设置开启AlarmReceiver这个Service
        Intent i = new Intent (this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast (this, 0, i, 0);
        //ELAPSED_REALTIME_WAKEUP表示让定时任务的出发时间从系统开机算起，并且会唤醒CPU。
        manager.set (AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand (intent, flags, startId);
    }

    @Override
    public void onDestroy () {
        super.onDestroy ();

        Log.e ("aaron","LongRunningService is closed");

        //在Service结束后关闭AlarmManager
        AlarmManager manager = (AlarmManager) getSystemService (ALARM_SERVICE);
        Intent i = new Intent (this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast (this, 0, i, 0);
        manager.cancel (pi);

    }

    public static class AlarmReceiver  extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //设置通知内容并在onReceive()这个函数执行时开启
            UtilTools.createNotification (context,"闹铃","快醒来",0, R.drawable.icon_pan102,1,null);

            //再次开启LongRunningService这个服务，从而可以
            Intent i = new Intent(context, LongRunningService.class);
            context.startService(i);
        }


    }
}
