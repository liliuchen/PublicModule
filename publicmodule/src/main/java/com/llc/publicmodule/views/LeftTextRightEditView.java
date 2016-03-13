package com.llc.publicmodule.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.llc.publicmodule.R;

/**
 * TODO: document your custom view class.
 */
public class LeftTextRightEditView extends LinearLayout {
    private String mLeftText, mRightHintText; // TODO: use a default from R.string...
    private int mLeftTextColor = Color.BLACK,mRightTextColor= Color.BLACK; // TODO: use a default from R.color...
    private float mLeftTextSize = 14,mRightTextSize=14; // TODO: use a default from R.dimen...
    private Drawable mRightEditTextBackground;

    TextView mTextView;
    EditText mEditText;

    public TextView getmTextView() {
        return mTextView;
    }

    public EditText getmEditText() {
        return mEditText;
    }

    Context context;
    public LeftTextRightEditView(Context context) {
        super(context);
        this.context=context;
        init(null, 0);
    }

    public LeftTextRightEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init(attrs, 0);

    }

    public LeftTextRightEditView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context=context;
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.LeftTextRightEditView, defStyle, 0);

        mLeftText = a.getString(
                R.styleable.LeftTextRightEditView_LTRE_leftText);

        mRightHintText = a.getString(
                R.styleable.LeftTextRightEditView_LTRE_rightHint);
        mLeftTextColor = a.getColor(
                R.styleable.LeftTextRightEditView_LTRE_leftTextColor,
                Color.BLACK);
        mRightTextColor = a.getColor(
                R.styleable.LeftTextRightEditView_LTRE_rightTextColor,
                Color.BLACK);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mLeftTextSize = a.getDimension(
                R.styleable.LeftTextRightEditView_LTRE_leftTextSize,
                14);
        mRightTextSize=a.getDimension(
                R.styleable.LeftTextRightEditView_LTRE_rightTextSize,
                14);

        if (a.hasValue(R.styleable.LeftTextRightEditView_LTRE_rightBackgroundDrawable)) {
            mRightEditTextBackground = a.getDrawable(
                    R.styleable.LeftTextRightEditView_LTRE_rightBackgroundDrawable);
        }
        a.recycle();
        initView();
    }
    private void initView() {
        View.inflate(context, R.layout.left_text_right_edit_view, this);
        mTextView= (TextView) findViewById(R.id.left_text_right_edit_view_textview);
        mEditText= (EditText) findViewById(R.id.left_text_right_edit_view_edittext);
        mTextView.setText(mLeftText);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mLeftTextSize);
        mTextView.setTextColor(mLeftTextColor);
        mEditText.setHint(mRightHintText);
        mEditText.setTextColor(mRightTextColor);
        mEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX,mRightTextSize);
        mEditText.setBackgroundDrawable(mRightEditTextBackground);
    }

}
