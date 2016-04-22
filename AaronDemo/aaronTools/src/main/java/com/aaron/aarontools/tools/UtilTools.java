package com.aaron.aarontools.tools;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.aaron.aarontools.R;

/**
 * Created by toughegg on 16/4/20.
 */
public class UtilTools {

    /**
     * 获取屏幕的宽度
     */
    public static DisplayMetrics getWindowsDisplayMetrics (Context context) {
        DisplayMetrics dm = new DisplayMetrics ();
        ((Activity) context).getWindowManager ().getDefaultDisplay ().getMetrics (dm);
        return dm;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px (Context context, float dpValue) {
        final float scale = context.getResources ().getDisplayMetrics ().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip (Context context, float pxValue) {
        final float scale = context.getResources ().getDisplayMetrics ().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机分辨率转换字体大小
     */
    public static int getFontSize (Context context, float textSize) {
        DisplayMetrics dm = new DisplayMetrics ();
        WindowManager windowManager = (WindowManager) context.getSystemService (Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay ().getMetrics (dm);
        int screenHeight = dm.heightPixels;
        int rate = (int) (textSize * (float) screenHeight / 1280);
        return rate;
    }

    /**
     * 获取View的宽度高度
     *
     * @param view
     * @return
     */
    public static int[] getMeasuredOfView (View view) {
        int[] ints = new int[2];
        view.measure (View.MeasureSpec.makeMeasureSpec (0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec (0, View.MeasureSpec.UNSPECIFIED));
        int width = view.getMeasuredWidth ();
        int height = view.getMeasuredHeight ();
        Log.e ("aaron", "SystemTools>>>>View的宽度和高度：" + width + "---------" + height);
        ints[0] = width;
        ints[1] = height;
        return ints;
    }

    /**
     * 获取国家码
     */
    public static String getCountryZipCode (Context context) {
        String CountryID = "";
        String CountryZipCode = "";
        TelephonyManager manager = (TelephonyManager) context.getSystemService (Context.TELEPHONY_SERVICE);
        // getNetworkCountryIso
        CountryID = manager.getSimCountryIso ().toUpperCase ();
        String[] rl = context.getResources ().getStringArray (R.array.CountryCodes);
        for (int i = 0; i < rl.length; i++) {
            String[] g = rl[i].split (",");
            if (g[1].trim ().equals (CountryID.trim ())) {
                CountryZipCode = g[0];
                break;
            }
        }
        return CountryZipCode;
    }

    /**
     * 创建通知，
     * 参考网址：http://blog.csdn.net/vipzjyno1/article/details/25248021
     *          http://www.android100.org/html/201509/23/183928.html
     *
     * @param context
     * @param title
     * @param hint
     * @param count
     * @param iconResId
     * @param notifyId
     * @param intent
     */
    public static void createNotification (Context context, String title, String hint, int count, int iconResId, int notifyId, PendingIntent intent) {
        // 第一步：实例化通知栏构造器NotificationCompat.Builder：
        NotificationCompat.Builder builder = new NotificationCompat.Builder (context);
        // 第二步：对Builder进行配置：
        builder.setContentTitle (title);// 设置标题
        builder.setContentText (hint);// 设置提示
        builder.setSmallIcon (iconResId);// 设置通知小图标（必须要）
        builder.setNumber (count);// 设置通知的数量
        builder.setDefaults (Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND);// 设置收到通知提示可以是声音、灯光、震动，其中震动DEFAULT_VIBRATE 需要权限：
//        builder.setVibrate (new long[]{0, 300, 1000, 1000});// 实现效果：延迟0ms，然后振动300ms，在延迟500ms，接着在振动700ms。
//        builder.build ().vibrate = new long[]{0, 300, 1000, 1000};// 同上面效果,不能和setDefaults同用

        builder.setOngoing (true);// 设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
        builder.setAutoCancel (true);//设置这个标志当用户单击面板就可以让通知将自动取消(未测试)
        builder.setContentIntent (intent);
        // 第三步：获取状态通知栏管理
        NotificationManager manager = (NotificationManager) context.getSystemService (Context.NOTIFICATION_SERVICE);
        Notification notification = builder.build ();// 得到通知对象
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        // 第四步：通过通知管理对象发送通知
        manager.notify (notifyId, notification);// 发送通知 (如果id相同，即使发送多个通知，也只显示一个)

        /*
        Notification.FLAG_SHOW_LIGHTS              //三色灯提醒，在使用三色灯提醒时候必须加该标志符
        Notification.FLAG_ONGOING_EVENT          //发起正在运行事件（活动中）
        Notification.FLAG_INSISTENT   //让声音、振动无限循环，直到用户响应 （取消或者打开）
        Notification.FLAG_ONLY_ALERT_ONCE  //发起Notification后，铃声和震动均只执行一次
        Notification.FLAG_AUTO_CANCEL      //用户单击通知后自动消失
        Notification.FLAG_NO_CLEAR          //只有全部清除时，Notification才会清除 ，不清楚该通知(QQ的通知无法清除，就是用的这个)
        Notification.FLAG_FOREGROUND_SERVICE    //表示正在运行的服务
        */

        // 删除一个特定的通知ID对应的通知:
//        manager.cancel(notifyId);
        // 删除所有通知:mNotificationManager.cancelAll();
//        注意：1）通知必须设置icon，不然会报错
//        2)最好使用NotificationCompat.Builder构建器来创建通知
    }
}
