package com.llc.publicmodule.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.method.KeyListener;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.llc.publicmodule.R;

/**
 * Created by Administrator on 2015/12/2.
 */
public class CleanEditText extends RelativeLayout {
    EditText editText;
    ImageView imageView;
    String hint, text;
    float textSize, rightDrawablePadding;
    Drawable drawable;
    int backgroundDrawable, textColor;

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        editText.setTextColor(textColor);
    }

    public void setHint(String hint) {
        this.hint = hint;
        editText.setHint(hint);
    }

    public String getText() {
        return editText.getText().toString();
    }

    public void setText(String text) {
        this.text = text;
        editText.setText(text);
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
        imageView.setImageDrawable(drawable);
    }

    public CleanEditText(Context context) {
        super(context);
    }

    public CleanEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public CleanEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    public void initView(Context context, AttributeSet attrs) {
        initAttrs(context, attrs);
        View.inflate(context, R.layout.clean_edit_view, this);
        editText = (EditText) findViewById(R.id.clean_edit_view_editview);
        imageView = (ImageView) findViewById(R.id.clean_edit_view_imageview);
        if (backgroundDrawable != 0) editText.setBackgroundResource(backgroundDrawable);
        if (hint != null) editText.setHint(hint);
        if (text != null) editText.setText(text);
        setTextColor(textColor);
        setTextSize(textSize);
        imageView.setImageDrawable(drawable);
        imageView.setRight((int) rightDrawablePadding);
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });
    }

    public void initAttrs(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CleanEditText);
        text = typedArray.getString(R.styleable.CleanEditText_text);
        hint = typedArray.getString(R.styleable.CleanEditText_hint);
        textSize = typedArray.getDimension(R.styleable.CleanEditText_textSize, 16);
        drawable = typedArray.getDrawable(R.styleable.CleanEditText_rightdrawableRight);
        rightDrawablePadding = typedArray.getDimension(R.styleable.CleanEditText_rightDrawablePadding, 10.0f);
        backgroundDrawable = typedArray.getResourceId(R.styleable.CleanEditText_c_background, 0);
        textColor = typedArray.getColor(R.styleable.CleanEditText_textColor, Color.BLACK);
    }

    public void setKeyListener(KeyListener keyListener) {
        editText.setKeyListener(keyListener);
    }

    public EditText getEditText() {
        return editText;
    }
}
