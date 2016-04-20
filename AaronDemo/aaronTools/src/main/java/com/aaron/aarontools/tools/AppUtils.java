package com.aaron.aarontools.tools;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.security.MessageDigest;

/**
 * App 版本名称等
 * Created by Andy on 15/7/23.
 */
public class AppUtils {
    private AppUtils () {
        throw new UnsupportedOperationException ("AppUtil cannot instantiated");
    }

    /**
     * 获取应用程序版本信息
     */
    public static PackageInfo getAppPackageInfo (Context context) {
        try {
            PackageManager packageManager = context.getPackageManager ();
            PackageInfo packageInfo = packageManager.getPackageInfo (
                    context.getPackageName (), 0);
            return packageInfo;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace ();
        }
        return null;
    }

    /**
     * 获取app版本名
     */
    public static String getAppVersionName (Context context) {
        PackageManager pm = context.getPackageManager ();
        PackageInfo pi;
        try {
            pi = pm.getPackageInfo (context.getPackageName (), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace ();
        }
        return "";
    }

    /**
     * 获取app版本号
     */
    public static int getAppVersionCode (Context context) {
        PackageManager pm = context.getPackageManager ();
        PackageInfo pi;
        try {
            pi = pm.getPackageInfo (context.getPackageName (), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace ();
        }
        return 0;
    }

    /**
     * 获取应用签名
     *
     * @param context
     * @param pkgName
     */
    public static String getSign (Context context, String pkgName) {
        try {
            PackageInfo pis = context.getPackageManager ().getPackageInfo (
                    pkgName, PackageManager.GET_SIGNATURES);
            return hexdigest (pis.signatures[0].toByteArray ());
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException (SystemTools.class.getName () + "the "
                    + pkgName + "'s application not found");
        }
    }

    /**
     * 将签名字符串转换成需要的32位签名
     */
    private static String hexdigest (byte[] paramArrayOfByte) {
        final char[] hexDigits = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97,
                98, 99, 100, 101, 102};
        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance ("MD5");
            localMessageDigest.update (paramArrayOfByte);
            byte[] arrayOfByte = localMessageDigest.digest ();
            char[] arrayOfChar = new char[32];
            for (int i = 0, j = 0; ; i++, j++) {
                if (i >= 16) {
                    return new String (arrayOfChar);
                }
                int k = arrayOfByte[i];
                arrayOfChar[j] = hexDigits[(0xF & k >>> 4)];
                arrayOfChar[++j] = hexDigits[(k & 0xF)];
            }
        } catch (Exception e) {
        }
        return "";
    }

}
