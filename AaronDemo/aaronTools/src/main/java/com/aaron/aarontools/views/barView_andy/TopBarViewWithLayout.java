package com.aaron.aarontools.views.barView_andy;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aaron.aarontools.R;

/**
 * Created by Andy on 16/4/10.
 * TopBarViewWithLayout
 */
public class TopBarViewWithLayout extends RelativeLayout {

    private LayoutInflater mLayoutInflater;
    private ViewGroup mViewGroup;

    private RelativeLayout leftLayout, rightLayout;
    private ImageView leftImg, rightImg;

    private TextView leftTv;
    private TextView rightTv;
    private TextView tvTitle;


    private onTopBarClickListener listener;


    public interface onTopBarClickListener {
        void onClickLeftButton ();

        void onClickRightButton ();
    }

    public void setOnTopBarClickListener(onTopBarClickListener listener) {
        this.listener = listener;
    }

    public TopBarViewWithLayout(Context context) {
        this(context, null, 0);
    }

    public TopBarViewWithLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopBarViewWithLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewGroup = (ViewGroup) mLayoutInflater.inflate(R.layout.topbar_layout, null);
        leftLayout = (RelativeLayout) mViewGroup.findViewById(R.id.leftLayout);
        leftImg = (ImageView) mViewGroup.findViewById(R.id.leftImage);
        leftTv = (TextView) mViewGroup.findViewById(R.id.leftText);

        rightLayout = (RelativeLayout) mViewGroup.findViewById(R.id.rightLayout);
        rightImg = (ImageView) mViewGroup.findViewById(R.id.rightImage);
        rightTv = (TextView) mViewGroup.findViewById(R.id.rightText);

        tvTitle = (TextView) mViewGroup.findViewById(R.id.title);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.myBar, defStyleAttr, 0);
        leftImg.setBackground(ta.getDrawable(R.styleable.myBar_myLeftImg));
        leftTv.setText(ta.getString(R.styleable.myBar_myLeftTvTitle));
        leftTv.setTextSize(ta.getDimension(R.styleable.myBar_myLeftTvSize, 12));
        leftTv.setTextColor(ta.getColor(R.styleable.myBar_myLeftTvColor, Color.BLACK));

        rightImg.setBackground(ta.getDrawable(R.styleable.myBar_myRightImg));
        rightTv.setText(ta.getString(R.styleable.myBar_myRightTvTitle));
        rightTv.setTextSize(ta.getDimension(R.styleable.myBar_myRightTvSize, 12));
        rightTv.setTextColor(ta.getColor(R.styleable.myBar_myRightTvColor, Color.BLACK));

        tvTitle.setText(ta.getString(R.styleable.myBar_myTvTitle));
        tvTitle.setTextSize(ta.getDimension(R.styleable.myBar_myTvSize, 12));
        tvTitle.setTextColor(ta.getColor(R.styleable.myBar_myTvColor, Color.BLACK));

        this.addView(mViewGroup);

        ta.recycle();


        //设置点击事件
        leftLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClickLeftButton();
                }
            }
        });

        rightLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClickRightButton();
                }
            }
        });


    }


    // 设置左边 btn 颜色
    public void serLeftLayoutColor(int color) {
        leftLayout.setBackgroundColor(color);
    }

    // 设置右边 btn 颜色
    public void setRightLayoutColor(int color) {
        rightLayout.setBackgroundColor(color);
    }

    // 设置左边  图片
    public void setLeftImageRecource(int resid) {
        leftImg.setBackgroundResource(resid);
    }

    // 设置右边  图片
    public void setRightImageRecource(int resid) {
        rightImg.setBackgroundResource(resid);
    }

    //设置左边text by res id
    public void setLeftText(int resid) {
        leftTv.setText(resid);
    }

    // 设置左边 text by string res
    public void setLeftText(String res) {
        leftTv.setText(res);
    }

    //设置右边text by res id
    public void setRightText(int resid) {
        rightTv.setText(resid);
    }

    // 设置右边 text by string res
    public void setRightText(String res) {
        rightTv.setText(res);
    }

    // 设置 leftLayout 显示 与否
    public void setLeftLayoutShow(boolean show) {
        if (show) {
            leftLayout.setVisibility(VISIBLE);
        } else {
            leftLayout.setVisibility(GONE);
        }
    }

    // 设置 rightLayout 显示 与否
    public void setrightLayoutShow(boolean show) {
        if (show) {
            rightLayout.setVisibility(VISIBLE);
        } else {
            rightLayout.setVisibility(GONE);
        }
    }
}
