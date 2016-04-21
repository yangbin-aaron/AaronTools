package com.aaron.aarondemo.activitys.floatwindon;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aaron.aarondemo.AaronApplication;
import com.aaron.aarondemo.R;

/**
 * Created by toughegg on 16/4/21.
 */
public class FloatWindons2Activity extends Activity {

    private AaronApplication mApplication;

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayout;
    private DesktopLayout2 mDesktopLayout;
    private long startTime;
    // 声明屏幕的宽高
    float x, y;
    int top;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_floatwindons2);
        mApplication = (AaronApplication) getApplication ();
        TextView back = (TextView) findViewById (R.id.back);
        back.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                finish ();
            }
        });

        createWindowManager ();
        createDesktopLayout ();
        Button btnShow = (Button) findViewById (R.id.show_float_windons_btn);
        btnShow.setOnClickListener (new View.OnClickListener () {
            public void onClick (View v) {
                showDesk ();
            }
        });
    }

    /**
     * 创建悬浮窗体
     */
    private void createDesktopLayout () {
        mDesktopLayout = new DesktopLayout2 (this);
        mDesktopLayout.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Toast.makeText (FloatWindons2Activity.this, "再点我", Toast.LENGTH_SHORT).show ();
            }
        });
        mDesktopLayout.setOnTouchListener (new View.OnTouchListener () {
            float mTouchStartX;
            float mTouchStartY;

            @Override
            public boolean onTouch (View v, MotionEvent event) {
                // 获取相对屏幕的坐标，即以屏幕左上角为原点
                x = event.getRawX ();
                y = event.getRawY () - top; // 25是系统状态栏的高度
                Log.i ("startP", "startX" + mTouchStartX + "====startY"
                        + mTouchStartY);
                switch (event.getAction ()) {
                    case MotionEvent.ACTION_DOWN:
                        // 获取相对View的坐标，即以此View左上角为原点
                        mTouchStartX = event.getX ();
                        mTouchStartY = event.getY ();
                        Log.i ("startP", "startX" + mTouchStartX + "====startY"
                                + mTouchStartY);
                        long end = System.currentTimeMillis () - startTime;
                        // 双击的间隔在 300ms以下
                        if (end < 300) {
                            closeDesk ();
                        }
                        startTime = System.currentTimeMillis ();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // 更新浮动窗口位置参数
                        mLayout.x = (int) (x - mTouchStartX);
                        mLayout.y = (int) (y - mTouchStartY);
                        mWindowManager.updateViewLayout (v, mLayout);
                        break;
                    case MotionEvent.ACTION_UP:

                        // 更新浮动窗口位置参数
                        mLayout.x = (int) (x - mTouchStartX);
                        mLayout.y = (int) (y - mTouchStartY);
                        mWindowManager.updateViewLayout (v, mLayout);

                        // 可以在此记录最后一次的位置

                        mTouchStartX = mTouchStartY = 0;
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onWindowFocusChanged (boolean hasFocus) {
        super.onWindowFocusChanged (hasFocus);
        Rect rect = new Rect ();
        // /取得整个视图部分,注意，如果你要设置标题样式，这个必须出现在标题样式之后，否则会出错
        getWindow ().getDecorView ().getWindowVisibleDisplayFrame (rect);
        top = rect.top;//状态栏的高度，所以rect.height,rect.width分别是系统的高度的宽度

        Log.i ("top", "" + top);
    }

    /**
     * 显示DesktopLayout
     */
    private void showDesk () {
        if (mApplication.floatWindonIsShowing){
            Toast.makeText (this,"不能重复addView",Toast.LENGTH_SHORT).show ();
            return;
        }
        mWindowManager.addView (mDesktopLayout, mLayout);
        mApplication.floatWindonIsShowing = true;
    }

    /**
     * 关闭DesktopLayout
     */
    private void closeDesk () {
        mWindowManager.removeView (mDesktopLayout);
        mApplication.floatWindonIsShowing = false;
        finish ();
    }

    /**
     * 设置WindowManager
     */
    private void createWindowManager () {
        // 取得系统窗体
        mWindowManager = (WindowManager) getApplicationContext ().getSystemService (this.WINDOW_SERVICE);

        // 窗体的布局样式
        mLayout = new WindowManager.LayoutParams ();

        // 设置窗体显示类型——TYPE_SYSTEM_ALERT(系统提示)
        mLayout.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;

        // 设置窗体焦点及触摸：
        // FLAG_NOT_FOCUSABLE(不能获得按键输入焦点)
        mLayout.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        // 设置显示的模式
        mLayout.format = PixelFormat.RGBA_8888;

        // 设置对齐的方法
        mLayout.gravity = Gravity.TOP | Gravity.LEFT;

        // 设置窗体宽度和高度
        mLayout.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayout.height = WindowManager.LayoutParams.WRAP_CONTENT;

    }
}
