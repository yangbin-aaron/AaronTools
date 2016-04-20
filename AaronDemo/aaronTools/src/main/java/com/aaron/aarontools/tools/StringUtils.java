package com.aaron.aarontools.tools;

import android.util.Log;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String 处理工具类
 * Created by Andy on 15/7/11.
 */
public class StringUtils {

    /**
     * 判断给定字符串是否空白串 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     */
    public static boolean isEmpty (CharSequence input) {
        if (input == null || "".equals (input))
            return true;

        for (int i = 0; i < input.length (); i++) {
            char c = input.charAt (i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }


    /**
     * 判断是不是一个合法的电子邮件地址
     */
    public static boolean matcherEmail (CharSequence email) {
        Pattern emailer = Pattern.compile ("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        if (isEmpty (email)) {
            return false;
        }
        return emailer.matcher (email).matches ();
    }

    /**
     * 判断是不是一个合法的手机号码
     */
    public static boolean matcherPhone (CharSequence phoneNum) {
        Pattern phone = Pattern.compile ("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        if (isEmpty (phoneNum)) {
            return false;
        }
        return phone.matcher (phoneNum).matches ();
    }

    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int stringToInt (String str, int defValue) {
        try {
            return Integer.parseInt (str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * String转long
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long stringToLong (String obj, long defValue) {
        try {
            return Long.parseLong (obj);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * String转double
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static double stringToDouble (String obj, double defValue) {
        try {
            return Double.parseDouble (obj);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 字符串转布尔
     *
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBool (String b) {
        try {
            return Boolean.parseBoolean (b);
        } catch (Exception e) {
        }
        return false;
    }


    /**
     * 判断一个字符串是不是数字
     */
    public static boolean isNumber (CharSequence str) {
        try {
            Integer.parseInt (str.toString ());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * byte[]数组转换为16进制的字符串。
     *
     * @param data 要转换的字节数组。
     * @return 转换后的结果。
     */
    public static final String byteArrayToHexString (byte[] data) {
        StringBuilder sb = new StringBuilder (data.length * 2);
        for (byte b : data) {
            int v = b & 0xff;
            if (v < 16) {
                sb.append ('0');
            }
            sb.append (Integer.toHexString (v));
        }
        return sb.toString ().toUpperCase (Locale.getDefault ());
    }

    /**
     * 16进制表示的字符串转换为字节数组。
     *
     * @param s 16进制表示的字符串
     * @return byte[] 字节数组
     */
    public static byte[] hexStringToByteArray (String s) {
        int len = s.length ();
        byte[] d = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节
            d[i / 2] = (byte) ((Character.digit (s.charAt (i), 16) << 4) + Character
                    .digit (s.charAt (i + 1), 16));
        }
        return d;
    }

    /**
     * 将float保留n位位小数
     *
     * @param val
     * @param format "#0.00"  2位小数
     * @return
     */
    public static String getStringFromFloat (float val, String format) {
        DecimalFormat df = new DecimalFormat (format);
        return df.format (val);
    }

    /**
     * 获取string中的数字
     *
     * @param str
     * @return
     */
    public static String getDigital (String str) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile (regEx);
        Matcher m = p.matcher (str);
        return m.replaceAll ("").trim ();
    }

    /**
     * 解码
     *
     * @param paramString
     * @return
     */
    public static String toURLEncoded (String paramString) {
        if (paramString == null || paramString.equals ("")) {
            Log.d ("aaron", "toURLEncoded error:" + paramString);
            return "";
        }

        try {
            String str = new String (paramString.getBytes (), "UTF-8");
            str = URLEncoder.encode (str, "UTF-8");
            return str;
        } catch (Exception localException) {
            Log.d ("h", "toURLEncoded error:" + paramString, localException);
        }

        return "";
    }

    // ----------------------------------------------------------------------

    /**
     * 截取英文的首字母
     *
     * @param inputString Get User Info       －－   guf
     */
    public static String getEnShort (String inputString) {
        String result = "";
        inputString = inputString.toLowerCase ();
        String[] tt = inputString.split (" ");
        System.out.println (tt.length);
        for (int i = 0; i < tt.length; i++) {
            if (tt[i].length () > 0) {//加个判断，长度大于0的
                result = result + String.valueOf (tt[i].charAt (0)).toLowerCase ();
            }
        }
        return result;
    }

    /**
     * 将字符串中的中文转化为拼音,并提取首字母
     *
     * @param inputString
     * @return 获取用户信息         －－   hqyhxx
     */
    public static String getPingYinShort (String inputString) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat ();
        format.setCaseType (HanyuPinyinCaseType.LOWERCASE);
        format.setToneType (HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType (HanyuPinyinVCharType.WITH_V);

        char[] input = inputString.trim ().toCharArray ();// 把字符串转化成字符数组
        String shortString = "";
        try {
            for (int i = 0; i < input.length; i++) {
                // \\u4E00是unicode编码，判断是不是中文
                if (Character.toString (input[i]).matches (
                        "[\\u4E00-\\u9FA5]+")) {
                    // 将汉语拼音的全拼存到temp数组
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray (
                            input[i], format);
                    // 取拼音的第一个读音
                    shortString += temp[0].substring (0, 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace ();
        }
        return shortString;
    }

}
