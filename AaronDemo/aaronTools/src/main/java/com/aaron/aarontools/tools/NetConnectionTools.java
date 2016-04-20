package com.aaron.aarontools.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by toughegg on 16/4/20.
 * 网络连接工具类
 */
public class NetConnectionTools {
    /**
     * 获取网络连接类型,wifi还是手机网络
     *
     * @param context
     * @return -1为无网络,1wifi,0流量
     */
    public static int getNetType (Context context) {
        int netType = -1;// 默认为无网络
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService (Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo ();
        if (info != null) {
            netType = info.getType ();
           /* info.getSubtype()取值列表如下：

            * NETWORK_TYPE_CDMA 网络类型为CDMA
            * NETWORK_TYPE_EDGE 网络类型为EDGE
            * NETWORK_TYPE_EVDO_0 网络类型为EVDO0
            * NETWORK_TYPE_EVDO_A 网络类型为EVDOA
            * NETWORK_TYPE_GPRS 网络类型为GPRS
            * NETWORK_TYPE_HSDPA 网络类型为HSDPA
            * NETWORK_TYPE_HSPA 网络类型为HSPA
            * NETWORK_TYPE_HSUPA 网络类型为HSUPA
            * NETWORK_TYPE_UMTS 网络类型为UMTS

            联通的3G为UMTS或HSDPA，移动和联通的2G为GPRS或EDGE，电信的2G为CDMA，电信
                    的3G为EVDO
        */
        }
        return netType;
    }

    /**
     * 判断网络是否连接
     */
    public static boolean checkNetConnected (Context context) {
        return getNetType (context) != -1;
    }

    /**
     * 判断WiFi和移动流量是否已连接
     * @param context
     * @return
     */
    public static boolean checkNetworkConnection(Context context)
    {
        final ConnectivityManager connMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        final android.net.NetworkInfo wifi =connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final android.net.NetworkInfo mobile =connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if(wifi.isAvailable()||mobile.isAvailable())  //getState()方法是查询是否连接了数据网络
            return true;
        else
            return false;
    }
}
