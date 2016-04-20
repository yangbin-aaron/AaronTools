package com.aaron.aarontools.tools;

import android.app.Activity;
import android.content.Context;
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
    public static int getFontSize (Context context, int textSize) {
        DisplayMetrics dm = new DisplayMetrics ();
        WindowManager windowManager = (WindowManager) context.getSystemService (Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay ().getMetrics (dm);
        int screenHeight = dm.heightPixels;
        int rate = (int) (textSize * (float) screenHeight / 1280);
        return rate;
    }

    /**
     * 获取View的宽度高度
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
}
