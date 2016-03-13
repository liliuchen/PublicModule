package com.llc.publicmodule.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * Created by Administrator on 2015/9/28.
 */
public class CProgressBar extends ProgressBar {
    String text = "";
    Paint mPaint;
    Handler mHandler;
    boolean isShowText;

    public CProgressBar(Context context) {
        super(context);
        mHandler = new Handler();
        initView();
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setTextSize(32);
        mPaint.setColor(Color.WHITE);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        text = getProgress() + "%";
    }

    public CProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHandler = new Handler();
        initView();

    }

    public CProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHandler = new Handler();
        initView();
    }

    public CProgressBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mHandler = new Handler();
        initView();
    }

    public synchronized void setProgress_(final int progress) {

        if (getProgress() > progress) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (getProgress() > progress) {
                        int current = getProgress();
                        setProgress(--current);
                        setText(getProgress() + "%");
                        SystemClock.sleep(10);
                    }
                }
            }).start();
        } else if(getProgress() < progress){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (getProgress() < progress) {
                        int current = getProgress();
                        setProgress(++current);
                        setText(getProgress() + "%");
                        SystemClock.sleep(10);
                    }
                }
            }).start();

        }
    }

    private void setText(String text) {
        this.text = text;
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        if (isShowText) {
        Rect rect = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), rect);
        int x = getWidth() * getProgress() / 100 - rect.width() < 0 ? 0 : getWidth() * getProgress() / 100 - rect.width();
        int y = getHeight() / 2 - rect.centerY();
        canvas.drawText(text, x, y, mPaint);
//        }
    }
}
