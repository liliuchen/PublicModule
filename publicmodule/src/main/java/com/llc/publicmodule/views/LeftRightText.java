package com.llc.publicmodule.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.llc.publicmodule.R;


/**
 * Created by Administrator on 2015/12/1.
 */
public class LeftRightText extends RelativeLayout {
    TextView tvLeft, tvRight, tvNum,tvDrawableRight;
    String lefttext, righttext, rightnum;
    int leftTextColor, rightTextColor;
    float leftTextSize, rightTextSize,leftDrawablePadding;
    Drawable leftDrawable, rightDrawable;


    public void setRightTextSize(float rightTextSize) {
        this.rightTextSize = rightTextSize;
        tvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightTextSize);
    }

    public void setLeftTextSize(float leftTextSize) {
        this.leftTextSize = leftTextSize;
        tvLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftTextSize);
    }


    public String getLefttext() {
        return tvLeft.getText().toString();
    }

    public void setLefttext(String lefttext) {
        tvLeft.setText(lefttext);
    }

    public String getRighttext() {
        return tvRight.getText().toString();
    }

    public void setRighttext(String righttext) {
        this.righttext = righttext;
        tvRight.setText(righttext);
    }

    public String getRightnum() {
        return tvNum.getText().toString();
    }

    public void setRightnum(String rightnum) {
        this.rightnum = rightnum;
        if (rightnum == null || rightnum.equals("")) {
            tvNum.setVisibility(View.GONE);
        } else {
            tvNum.setVisibility(View.VISIBLE);
        }
        tvNum.setText(rightnum);
    }
    public void setLeftTextColor(int leftTextColor) {
        this.leftTextColor = leftTextColor;
        tvLeft.setTextColor(leftTextColor);
    }
    public void setRightTextColor(int rightTextColor) {
        this.rightTextColor = rightTextColor;
        tvRight.setTextColor(rightTextColor);
    }

    public LeftRightText(Context context) {
        super(context);
        initView(context);
    }


    public LeftRightText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        initView(context);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LeftRightText);
        righttext = typedArray.getString(R.styleable.LeftRightText_right_text);
        lefttext=typedArray.getString(R.styleable.LeftRightText_left_text);
        rightnum = typedArray.getString(R.styleable.LeftRightText_right_msg_num);
        leftTextSize = typedArray.getDimension(R.styleable.LeftRightText_leftTextSize, 16.0f);
        rightTextSize = typedArray.getDimension(R.styleable.LeftRightText_rightTextSize, 12.0f);
        leftTextColor = typedArray.getColor(R.styleable.LeftRightText_leftTextColor, Color.BLACK);
        rightTextColor = typedArray.getColor(R.styleable.LeftRightText_rightTextColor, Color.BLACK);
        leftDrawable = typedArray.getDrawable(R.styleable.LeftRightText_drawableLeft);
        rightDrawable = typedArray.getDrawable(R.styleable.LeftRightText_drawableRight);
        leftDrawablePadding=typedArray.getDimension(R.styleable.LeftRightText_drawablePadding,5.0f);
        typedArray.recycle();
    }

    public LeftRightText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.left_right_text, this);
        tvLeft = (TextView) this.findViewById(R.id.left_right_text_lefttext);
        tvRight = (TextView) this.findViewById(R.id.left_right_text_righttext);
        tvNum = (TextView) this.findViewById(R.id.left_right_text_rightnum);
        tvDrawableRight= (TextView) this.findViewById(R.id.left_right_text_rightDrawable);
        setText();
        setTextSize();
        setLeftTextColor(leftTextColor);
        setRightTextColor(rightTextColor);
        setRightnum(rightnum);
        setDrawable();
    }

    private void setText() {
        tvLeft.setText(lefttext);
        tvRight.setText(righttext);
    }

    private void setDrawable() {
        tvLeft.setCompoundDrawablePadding((int)leftDrawablePadding);
        tvLeft.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null, null, null);
        tvDrawableRight.setCompoundDrawablesWithIntrinsicBounds(null, null, rightDrawable, null);
    }
    private void setTextSize() {
        tvLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftTextSize);
        tvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightTextSize);
    }


}
