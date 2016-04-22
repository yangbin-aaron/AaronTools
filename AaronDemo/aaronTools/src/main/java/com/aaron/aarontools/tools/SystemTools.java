package com.aaron.aarontools.tools;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;

import com.aaron.aarontools.AaronConstants;

import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by toughegg on 16/4/20.
 * Aaron
 * 系统工具类
 */
public class SystemTools {

    /**
     * 获取手机IP地址
     *
     * @return
     */
    public static String getPhoneIp () {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces (); en.hasMoreElements (); ) {
                NetworkInterface intf = en.nextElement ();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses (); enumIpAddr.hasMoreElements (); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement ();
                    if (!inetAddress.isLoopbackAddress () && inetAddress instanceof Inet4Address) {
                        //if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet6Address) {
                        return inetAddress.getHostAddress ().toString ();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace ();
        }
        return null;
    }

    /**
     * 判断当前应用程序是否后台运行
     */
    public static boolean isBackground (Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService (Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses ();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals (context.getPackageName ())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    // 后台运行
                    return true;
                } else {
                    // 前台运行
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 判断手机是否处理睡眠
     */
    public static boolean isSleeping (Context context) {
        KeyguardManager kgMgr = (KeyguardManager) context
                .getSystemService (Context.KEYGUARD_SERVICE);
        boolean isSleeping = kgMgr.inKeyguardRestrictedInputMode ();
        return isSleeping;
    }

    /**
     * 安装apk
     *
     * @param context
     * @param file
     */
    public static void installApk (Context context, File file) {
        Intent intent = new Intent ();
        intent.setAction ("android.intent.action.VIEW");
        intent.addCategory ("android.intent.category.DEFAULT");
        intent.setType ("application/vnd.android.package-archive");
        intent.setData (Uri.fromFile (file));
        intent.setDataAndType (Uri.fromFile (file),
                "application/vnd.android.package-archive");
        intent.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity (intent);
    }

    /**
     * 回到home，后台运行
     */
    public static void goHome (Context context) {
        Intent mHomeIntent = new Intent (Intent.ACTION_MAIN);
        mHomeIntent.addCategory (Intent.CATEGORY_HOME);
        mHomeIntent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        context.startActivity (mHomeIntent);
    }

    /**
     * 获取设备的可用内存大小
     *
     * @param cxt 应用上下文对象context
     * @return 当前内存大小
     */
    public static int getDeviceUsableMemory (Context cxt) {
        ActivityManager am = (ActivityManager) cxt
                .getSystemService (Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo ();
        am.getMemoryInfo (mi);
        // 返回当前系统的可用内存
        return (int) (mi.availMem / (1024 * 1024));
    }

    /**
     * 清理后台进程与服务(释放内存)
     *
     * @param cxt     应用上下文对象context
     * @param handler
     * @return 被清理的数量
     */
    @TargetApi(Build.VERSION_CODES.FROYO)
    public static int clearThreadsAndServices (Context cxt, Handler handler) {
        long i = getDeviceUsableMemory (cxt);
        int count = 0; // 清理掉的进程数
        ActivityManager am = (ActivityManager) cxt.getSystemService (Context.ACTIVITY_SERVICE);
        // 获取正在运行的service列表
        List<ActivityManager.RunningServiceInfo> serviceList = am.getRunningServices (100);
        if (serviceList != null)
            for (ActivityManager.RunningServiceInfo service : serviceList) {
                if (service.pid == android.os.Process.myPid ())
                    continue;
                try {
                    android.os.Process.killProcess (service.pid);
                    count++;
                } catch (Exception e) {
                    e.getStackTrace ();
                    continue;
                }
            }

        // 获取正在运行的进程列表
        List<ActivityManager.RunningAppProcessInfo> processList = am.getRunningAppProcesses ();
        if (processList != null)
            for (ActivityManager.RunningAppProcessInfo process : processList) {
                // 一般数值大于RunningAppProcessInfo.IMPORTANCE_SERVICE的进程都长时间没用或者空进程了
                // 一般数值大于RunningAppProcessInfo.IMPORTANCE_VISIBLE的进程都是非可见进程，也就是在后台运行着
                if (process.importance > ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
                    // pkgList 得到该进程下运行的包名
                    String[] pkgList = process.pkgList;
                    for (String pkgName : pkgList) {
                        try {
                            am.killBackgroundProcesses (pkgName);
                            AndyLoger.debug ("======已经杀死包名：" + pkgName);
                            count++;
                        } catch (Exception e) { // 防止意外发生
                            AndyLoger.debug ("======没有杀死包名：" + pkgName + "   " + e.getMessage ());
                            e.getStackTrace ();
                            continue;
                        }
                    }
                }
            }
        int toatl = (int) (getDeviceUsableMemory (cxt) - i);
        handler.obtainMessage (AaronConstants.HANDLER_WHAT_MEMORY_CAPACITY, toatl, 0).sendToTarget ();
        AndyLoger.debug ("清理了" + (getDeviceUsableMemory (cxt) - i) + "M内存");
        return count;
    }

    //中英文切换
    public static void switchLanguage (Context context, String language) {
        //设置应用语言类型
        Resources resources = context.getResources ();
        Configuration config = resources.getConfiguration ();
        DisplayMetrics dm = resources.getDisplayMetrics ();
        if (language.equals (AaronConstants.LANGUAGE_ENGLISH)) {
            config.locale = Locale.ENGLISH;
        } else if (language.equals (AaronConstants.LANGUAGE_SIMPLIFIED_CHINESE)) {// 简易中文
            config.locale = Locale.SIMPLIFIED_CHINESE;
        } else if (language.equals (AaronConstants.LANGUAGE_TAIWAN_CHINESE)) {// 繁体中文
            config.locale = Locale.TAIWAN;
        }
        resources.updateConfiguration (config, dm);
    }

    /**
     * 获取系统语言
     *
     * @param context
     * @return
     */
    public static String getLanguage (Context context) {
        Locale locale = context.getResources ().getConfiguration ().locale;
        String language = locale.getLanguage ();
        return language;
    }

    /**
     * 时间定时通知器
     */
    public static void sendAlarm (Context mContext, long time, int alarmCount) {
        Log.d ("aaron", "send alarm -->>>>>");
        // 发送闹钟请求
        Intent intent = new Intent ();
        intent.setAction ("ACTION_MY_MESSAGE");
        Bundle bundle = new Bundle ();
        bundle.putInt ("alarmCount", alarmCount);
        intent.putExtras (bundle);
        PendingIntent pendingIntent = PendingIntent.getBroadcast (mContext, alarmCount, intent, 0);
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService (Context.ALARM_SERVICE);
        alarmManager.set (AlarmManager.RTC_WAKEUP, time, pendingIntent);
    }

    /**
     * 根据格式获取当前系统时间
     *
     * @param format 时间格式，为null时 yyyy-MM-dd hh:mm:ss
     * @return
     */
    public static String getDataOfFormat (String format) {
        if (format == null) {
            format = AaronConstants.YYYY_MM_DD_HH_MM_SS;
        }
        SimpleDateFormat df = new SimpleDateFormat (format);
        return df.format (new Date ());
    }

    /**
     * 获取详细的时间，年月日 时分秒 星期
     *
     * @return
     */
    public static HashMap<String, Integer> getTimeOfDefault () {
        Calendar c = Calendar.getInstance ();
        int hour = c.get (Calendar.HOUR_OF_DAY);//获取当前的小时数
        int minute = c.get (Calendar.MINUTE);//获取当前的分钟数
        int second = c.get (Calendar.SECOND);//获取当前的分钟数
        int year = c.get (Calendar.YEAR); //获取当前年份
        int month = c.get (Calendar.MONTH) + 1;//获取当前月份
        int day = c.get (Calendar.DAY_OF_MONTH);//获取当前月份的日期号码
        int week = c.get (Calendar.DAY_OF_WEEK) - 1;// 获取当前星期,星期是从礼拜天开始的(0星期天)

        HashMap<String, Integer> hashMap = new HashMap<> ();
        hashMap.put (AaronConstants.YEAR, year);
        hashMap.put (AaronConstants.MONTH, month);
        hashMap.put (AaronConstants.DAY, day);
        hashMap.put (AaronConstants.HOUR, hour);
        hashMap.put (AaronConstants.MINUTE, minute);
        hashMap.put (AaronConstants.SECOND, second);
        hashMap.put (AaronConstants.WEEK, week);
        return hashMap;
    }

    /**
     * 将字符串转为日期类型
     *
     * @param sdate
     * @return
     */
    public static Date stringToDate (String sdate, SimpleDateFormat dateFormater) {
        try {
            return dateFormater.parse (sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 判断用户的设备时区是否为东八区（中国） 2014年7月31日
     *
     * @return
     */
    public static boolean isInEasternEightZones () {
        boolean defaultVaule = true;
        if (TimeZone.getDefault () == TimeZone.getTimeZone ("GMT+08"))
            defaultVaule = true;
        else
            defaultVaule = false;
        return defaultVaule;
    }

    /**
     * 根据不同时区，转换时间 2014年7月31日
     */
    public static Date transformTime (Date date, TimeZone oldZone,
                                      TimeZone newZone) {
        Date finalDate = null;
        if (date != null) {
            int timeOffset = oldZone.getOffset (date.getTime ())
                    - newZone.getOffset (date.getTime ());
            finalDate = new Date (date.getTime () - timeOffset);
        }
        return finalDate;
    }

    /**
     * 获取所有APP的列表
     *
     * @param context
     * @return
     */
    public static List<PackageInfo> getAppList (Context context) {
        PackageManager pm = context.getPackageManager ();
        // 获取系统已经安装的应用
        List<PackageInfo> packages = pm.getInstalledPackages (0);

        /*
        PackageInfo pi = packages.get (i);
        pi.applicationInfo.loadLabel (pm).toString ()   －－AppName
        pi.applicationInfo.loadIcon (pm)                －－AppIcon   Drawable
        pi.packageName                                  －－包名
        pi.versionName                                  －－版本名
        pi.versionCode                                  －－版本号
        */
        return packages;
    }

    /**
     * 判断是否为系统应用
     *
     * @param packageInfo
     * @return
     */
    public static boolean isSystemApp (PackageInfo packageInfo) {
        if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
            //非系统应用
            return false;
        } else {
            //系统应用　　
            return true;
        }
    }

    /**
     * 获取非系统APP列表
     *
     * @param context
     * @return
     */
    public static List<PackageInfo> getNotSystemAppList (Context context) {
        List<PackageInfo> infos = new ArrayList<> ();
        List<PackageInfo> tempInfos = getAppList (context);
        for (PackageInfo packageInfo : tempInfos) {
            if (!isSystemApp (packageInfo)) {
                infos.add (packageInfo);
            }
        }
        return infos;
    }

    /**
     * 卸载应用
     *
     * @param context
     * @param packageName
     */
    public static void uninstallApp (Context context, String packageName) {
        Uri packageURI = Uri.parse ("package:" + packageName);
        Intent intent = new Intent (Intent.ACTION_DELETE);
        intent.setData (packageURI);
        context.startActivity (intent);
    }

    /**
     * 启动应用
     *
     * @param context
     * @param packageName
     */
    public static void startApp (Context context, String packageName) {
        //"jp.co.johospace.jorte"就是我们获得要启动应用的包名
        Intent intent = context.getPackageManager ().getLaunchIntentForPackage (packageName);
        context.startActivity (intent);
    }
}
