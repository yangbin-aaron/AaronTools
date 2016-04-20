package com.aaron.aarontools.tools;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 软键盘相关
 * Created by Andy on 15/7/23.
 */
public class KeyBoardUtil {

    private KeyBoardUtil () {
        throw new UnsupportedOperationException("KeyBoardUtil cannot be instantiated");
    }

    /**
     * 打卡软键盘
     */
    public static void openKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     */
    public static void closeKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    /**
     * 设置默认弹出键盘
     *
     * @param mEditText
     */
    public static void setKeyboardFocus(final EditText mEditText) {
        (new Handler()).postDelayed(new Runnable() {
            public void run() {
                mEditText.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                        MotionEvent.ACTION_DOWN, 0, 0, 0));
                mEditText.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP,
                        0, 0, 0));
            }
        }, 100);
    }

    /**
     * 隐藏系统键盘
     * <b>警告</b> 必须是确定键盘显示时才能调用
     */
    public static void hideKeyBoard (Activity aty) {
        ((InputMethodManager) aty
                .getSystemService (Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow (
                        aty.getCurrentFocus ().getWindowToken (),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
