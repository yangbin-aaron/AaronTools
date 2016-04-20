package com.aaron.aarontools.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.aaron.aarontools.AaronConstants;

/**
 * Created by Andy on 15/7/13.
 * SharePrefence保存操作
 */
public class SharedPreferencesUtils {

    private static SharedPreferencesUtils sharedPreferencesUtils;

    private static SharedPreferences preference;

    /**
     * 单例模式  实例划
     *
     * @param context
     * @param fileName
     * @return
     */
    public static SharedPreferencesUtils getInstants (Context context, String fileName) {
        if (sharedPreferencesUtils == null) {
            sharedPreferencesUtils = new SharedPreferencesUtils ();
            preference = context.getSharedPreferences (fileName, Context.MODE_PRIVATE);
        }
        return sharedPreferencesUtils;
    }

    /**
     * 单例模式  实例划
     *
     * @param context
     * @return
     */
    public static SharedPreferencesUtils getInstants (Context context) {
        if (sharedPreferencesUtils == null) {
            sharedPreferencesUtils = new SharedPreferencesUtils ();
            preference = context.getSharedPreferences (AaronConstants.SHAREPREFENCES_NAME_DEFAULT, Context.MODE_PRIVATE);
        }
        return sharedPreferencesUtils;
    }

    // -------------------------------int-------------------------------

    /**
     * save int 类型的值
     *
     * @param k
     * @param v
     */
    public void putIntV (String k, int v) {
        Editor editor = preference.edit ();
        editor.putInt (k, v);
        editor.commit ();
    }

    /**
     * get Int 类型数据
     *
     * @param k
     * @return 默认值为0
     */
    public int getIntV (String k) {
        return preference.getInt (k, 0);
    }

    /**
     * get Int 类型数据
     *
     * @param k
     * @return 默认值为自定义
     */
    public int getIntV (String k, int defv) {
        return preference.getInt (k, defv);
    }

    // -------------------------------boolean-------------------------------

    /**
     * save boolean 类型的值
     *
     * @param k
     * @param v
     */
    public void putBooleanV (String k, boolean v) {
        Editor editor = preference.edit ();
        editor.putBoolean (k, v);
        editor.commit ();
    }

    /**
     * get boolean 类型数据
     *
     * @param k
     * @return 默认值为false
     */
    public boolean getBooleanV (String k) {
        return preference.getBoolean (k, false);
    }

    /**
     * get boolean 类型数据
     *
     * @param k
     * @return 默认值为自定义
     */
    public boolean getBooleanV (String k, boolean defBool) {
        return preference.getBoolean (k, defBool);
    }

    // -------------------------------String-------------------------------

    /**
     * save String 类型的值
     *
     * @param k
     * @param v
     */
    public void putStringV (String k, String v) {
        Editor editor = preference.edit ();
        editor.putString (k, v);
        editor.commit ();
    }

    /**
     * get String 类型数据
     *
     * @param k
     * @return 默认值为null
     */
    public String getStringV (String k) {
        return preference.getString (k, null);
    }

    /**
     * get String 类型数据
     *
     * @param k
     * @return 默认值为自定义
     */
    public String getStringV (String k, String defV) {
        return preference.getString (k, defV);
    }

    // -------------------------------float-------------------------------

    /**
     * save float 类型的值
     *
     * @param k
     * @param v
     */
    public void putFloatV (String k, float v) {
        Editor editor = preference.edit ();
        editor.putFloat (k, v);
        editor.commit ();
    }

    /**
     * get Float 类型数据
     *
     * @param k
     * @return 默认值为0
     */
    public float getFloatV (String k) {
        return preference.getFloat (k, 0);
    }

    /**
     * get Float 类型数据
     *
     * @param k
     * @return 默认值为自定义
     */
    public float getFloatV (String k, float defv) {
        return preference.getFloat (k, defv);
    }

    // -------------------------------long-------------------------------

    /**
     * save long 类型的值
     *
     * @param k
     * @param v
     */
    public void putLongV (String k, long v) {
        Editor editor = preference.edit ();
        editor.putLong (k, v);
        editor.commit ();
    }

    /**
     * get Long 类型数据
     *
     * @param k
     * @return 默认值为0
     */
    public long getLongV (String k) {
        return preference.getLong (k, 0);
    }

    /**
     * get Long 类型数据
     *
     * @param k
     * @return 默认值为自定义
     */
    public long getLongV (String k, long defv) {
        return preference.getLong (k, defv);
    }

    // -------------------------------删除-------------------------------

    /**
     * remove一项
     *
     * @param k
     */
    public static void remove (String k) {
        Editor editor = preference.edit ();
        editor.remove (k);
        editor.commit ();
    }

    /**
     * 清空SharePrefences
     */
    public static void clean () {
        Editor editor = preference.edit ();
        editor.clear ();
        editor.commit ();
    }
}
